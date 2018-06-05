package Server;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.ValidationException;

import org.json.*;

import ChatMessage.ChatMessage;
import Items.ItemAmount;
import NetworkMessage.ErrorMessage;
import NetworkMessage.RequestMessage;
import NetworkMessage.ResponseMessage;
import NetworkMessage.SuccessMessage;
import NetworkMessage.eErrorType;
import NetworkMessage.eFunctions;
import Purchase.Purchase;
import Purchase.PurchaseTrack;
import Workers.*;
import Customer.*;
import FileAction.Exceptions.AllReadyExistsException;
import FileAction.Exceptions.DeleteFileException;
import FileAction.Exceptions.IncompatibleException;
import FileAction.Exceptions.NotFoundException;
import FileAction.Exceptions.OutOfRangeException;
import FileAction.Exceptions.RenameFileException;


public class Server
{	
	private int m_ServerSocketNumber = 10000;
	private final Object m_ConnectedUsersKey = new Object();
	private final FilesManager m_FileManager;
	private final ServerSocket m_ServerSocket;
	private final DateFormat m_LogDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private final Map<Worker,SocketData> m_ConnectedUsers;
	
	public Server() throws IOException
	{
		m_FileManager = FilesManager.CreateOrGetInstance();
		m_ServerSocket = new ServerSocket(m_ServerSocketNumber);
		m_ConnectedUsers = new LinkedHashMap<Worker, SocketData>();
	}
	
	@Override
	public void finalize()
	{
		if(!m_ServerSocket.isClosed())
		{
			try
			{
				m_ServerSocket.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}	
		}
	}
		
	public void StartServer() throws IOException
	{
		while(true)
		{
			final Socket socket = m_ServerSocket.accept();
			
			new Thread(new Runnable()
			{
				private Worker m_LoggedInUser = null;
				private SocketData m_ToUserSocketData = null;
				@SuppressWarnings("deprecation")
				@Override
				public void run()
				{
					RequestMessage messageFromClient = null;
					ResponseMessage messageToClient;
					
					try
					{
						SocketData currentSocket = new SocketData(socket);
						System.out.println(m_ServerSocketNumber);
						currentSocket.GetToNetStream().println(++m_ServerSocketNumber);
						if(Boolean.parseBoolean(currentSocket.GetFromNetStream().readLine()))
						{
							try {
								TimeUnit.SECONDS.sleep(1); // waiting for the client to open ServerSocket
							} catch (InterruptedException ignor) {}
							m_ToUserSocketData = new SocketData(new Socket("localhost",m_ServerSocketNumber));
						}
						
						String s1 = currentSocket.GetFromNetStream().readLine();
						messageFromClient = new RequestMessage(new JSONObject(s1));
						while(!messageFromClient.getFunction().equals(eFunctions.CloseConnection))
						{
							messageToClient = ExecuteFunction(messageFromClient);
							currentSocket.GetToNetStream().println(messageToClient.GetJSONObject().toString());
							messageFromClient = new RequestMessage(new JSONObject(currentSocket.GetFromNetStream().readLine()));
						}
						
						if(m_ConnectedUsers.containsKey(m_LoggedInUser))
						{
							signOut(m_LoggedInUser);
						}
						socket.close();
					}
					catch (IOException ignor){}				
				}
				
				private ResponseMessage ExecuteFunction(RequestMessage i_ClientRequest)
				{
					eFunctions function = i_ClientRequest.getFunction();
					JSONObject data = i_ClientRequest.getData();
					JSONObject dataToReturn = null;
					ResponseMessage messageToReturn = null;
					
					try
					{
						if(m_LoggedInUser==null && function != eFunctions.SignIn)
						{
							throw new Exception("You Must Sign in Before doing any operation");
						}
						
						switch(function)
						{
							case SignIn			:	signIn(data);
													dataToReturn = m_LoggedInUser.GetJSONObject();
													break;
							case SignOut		:	signOut(m_LoggedInUser);
													m_LoggedInUser = null;
													break;
							case AddCustomer	:	addCustomer(data);
													break;
							case AddWorker		:	dataToReturn = addWorker(data);
													break;
							case RemoveWorker 	: 	removeWorker(data);
													break;
							case RemoveCustomer :	removeCustomer(data);
													break;
							case IncItemAmount	:   incItemAmount(data);
													break;
							case GetVIPCustomers:   dataToReturn = getVipCustomers();
													break;
							case GetAllCustomers:   dataToReturn = getAllCustomers();
													break;
							case GetAllWorkers	:	dataToReturn = getAllWorkers();
													break;
							case GetAllExistItems:	dataToReturn = getAllExistItems();
													break;
							case GetItemsAmount	:	dataToReturn = getItemsAmount(m_LoggedInUser.getBranch());
													break;					
							case GetAllPurchaseTrack:dataToReturn = GetAllPurchaseTrack();
													break;
							case ChangePurchaseTrack:ChangePurcaseTrack(data);
													break;
							case GetPurchaseTotalPrice: dataToReturn = getPurcaseTotalPrice(data);
													break;
							case CommitPurchase	:	dataToReturn = commitPurchase(data);
													break;
							case GetTotaySalesReport:dataToReturn = getTodaySalesReport(data);
													break;
							case GetAllConnectedWorkes:dataToReturn = getAllConnectedUsers();
													break;
							case SendMessage	:	sendMessage(data);
													break;
							case GetCustomerByID:	dataToReturn = getCustomerById(data);
													break;
							case GetAllLogs		:	dataToReturn = getAllLogs();
													break;
							default				:	messageToReturn = new ErrorMessage("No Such Function",eErrorType.Client);
						}
					}
					catch(Exception ex)
					{
						messageToReturn = GetMessageErrorByException(ex);
					}
					finally
					{
						if(messageToReturn == null)
						{					
							messageToReturn = new SuccessMessage(dataToReturn == null ? null : dataToReturn.toString());
						}
					}
					
					return messageToReturn;
				}

				private ResponseMessage GetMessageErrorByException(Exception ex)
				{
					eErrorType whosFault = eErrorType.UnKnown;
					
					if(	ex instanceof FileNotFoundException ||
						ex instanceof IOException ||
						ex instanceof DeleteFileException ||
						ex instanceof RenameFileException)
					{
						whosFault = eErrorType.Server;
					}
					else if(	ex instanceof IncompatibleException ||
								ex instanceof IllegalArgumentException ||
								ex instanceof JSONException ||
								ex instanceof NumberFormatException ||
								ex instanceof OutOfRangeException ||
								ex instanceof AllReadyExistsException ||
								ex instanceof CreateCustomerException ||
								ex instanceof CreateWorkerException ||
								ex instanceof NotFoundException ||
								ex instanceof ParseException ||
								ex instanceof ValidationException||
								ex instanceof AllReadyConnectedException)
					{
						whosFault = eErrorType.Client;
					}
					
					return new ErrorMessage(ex.getMessage(),whosFault);
				}

				
				//------------------------Users Functions------------------------//
				private void signOut(Worker i_Worker)
				{
					if(i_Worker!=null)
					{
						synchronized(m_ConnectedUsersKey)
						{
							m_ConnectedUsers.remove(i_Worker);
						}
						notifyRemoveUser(i_Worker);
					}
				}
				
				private void signIn(JSONObject data) throws JSONException, FileNotFoundException, ValidationException, IncompatibleException, IOException, NotFoundException, CreateWorkerException, AllReadyConnectedException 
				{	
					Worker worker =  m_FileManager.SignIn(data.getString("workerNumber"),  data.getString("password"));
					if(m_ConnectedUsers.keySet().contains(worker))
					{
						throw new AllReadyConnectedException(worker);
					}
					m_LoggedInUser = worker;
					notifyNewUser(m_LoggedInUser);
					m_ConnectedUsers.put(m_LoggedInUser, m_ToUserSocketData);
				}
				
				//------------------------Chat Functions------------------------//
				private void notifyNewUser(Worker i_Worker)
				{
					RequestMessage requestMessage = new RequestMessage(eFunctions.NewConnectedUser,i_Worker.GetJSONObject());
					notifyAllConnectedUsers(requestMessage);
				}				

				private void notifyRemoveUser(Worker i_Worker)
				{
					RequestMessage requestMessage = new RequestMessage(eFunctions.RemoveConnectedUser,i_Worker.GetJSONObject());
					notifyAllConnectedUsers(requestMessage);
				}	
				
				private void notifyAllConnectedUsers(RequestMessage i_RequestMessage)
				{
					synchronized(m_ConnectedUsersKey)
					{
						for(SocketData socket : m_ConnectedUsers.values())
						{
							socket.GetToNetStream().println(i_RequestMessage.GetJSONObject().toString());
						}
					}
				}
				
				private JSONObject getAllConnectedUsers()
				{
					JSONArray connectedUsers = new JSONArray();
					
					synchronized(m_ConnectedUsersKey)
					{
						for(Worker worker : m_ConnectedUsers.keySet())
						{
							if(worker.getID().compareTo(m_LoggedInUser.getID()) != 0)
							{
								connectedUsers.put(worker.GetJSONObject());
							}
						}
					}
					
					return encapsulateToJSONObject(connectedUsers);
				}
				
				private void sendMessage(JSONObject data) throws JSONException, CreateWorkerException, NotFoundException
				{
					ChatMessage chatMessage = new ChatMessage(data);
					Worker reciver = chatMessage.GetReciver();
					SocketData toSendSocket;
					
					if(m_ConnectedUsers.containsKey(chatMessage.GetReciver()))
					{
						toSendSocket = m_ConnectedUsers.get(reciver);
						if(toSendSocket.GetSocket().isConnected())
						{
							
							toSendSocket.GetToNetStream().println((new RequestMessage(eFunctions.SendMessage,data)).GetJSONObject().toString());
						}
						else
						{
							signOut(chatMessage.GetReciver());
							throw new NotFoundException("worker",reciver.toString());
						}
					}
					else
					{
						throw new NotFoundException("worker",reciver.toString());
					}
				}

				
				//------------------------Adding Functions------------------------//
				private JSONObject addWorker(JSONObject i_Worker) throws CreateWorkerException, NumberFormatException, JSONException, IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException, AllReadyExistsException
				{
					JSONObject dataToReturn = new JSONObject();
					User newWorker = new User(i_Worker);
					String workerNumber = m_FileManager.AddWorker(newWorker);
					m_FileManager.SaveLog(getStringLog(String.format("New Worker Added %s", newWorker.toString())));
					dataToReturn.put("workerNumber", workerNumber);
					return dataToReturn;
				}
				
				private void addCustomer(JSONObject i_Customer) throws CreateCustomerException, NumberFormatException, FileNotFoundException, JSONException, IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException, AllReadyExistsException, IncompatibleException
				{
					Customer customer = CustomerFactory.CreateCustomer(i_Customer);
					if(customer instanceof NewCustomer)
					{
						m_FileManager.AddCustomer(customer);
						m_FileManager.SaveLog(getStringLog(String.format("New Custoer Added %s", customer.toString())));
					}
					else
					{
						throw new IncompatibleException("Must be new customer");
					}
				}

				//------------------------Removing Functions------------------------//
				private void removeWorker(JSONObject i_Worker) throws CreateWorkerException, NumberFormatException, JSONException, FileNotFoundException, IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
				{
					Worker worker = WorkerFactory.CreateWorker(i_Worker);
					m_FileManager.RemoveWorker(worker);
				}				
				
				private void removeCustomer(JSONObject i_Customer) throws CreateCustomerException, NumberFormatException, JSONException, FileNotFoundException, IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
				{
					Customer customer = CustomerFactory.CreateCustomer(i_Customer);
					m_FileManager.RemoveCustomer(customer);
				}
				
				//------------------------Items Functions------------------------//
				private JSONObject getAllExistItems() throws JSONException, IOException
				{
					return encapsulateToJSONObject(m_FileManager.GetAllExistItems());
				}
				
				private JSONObject getItemsAmount(eBranches i_Branch) throws FileNotFoundException, JSONException, IncompatibleException, IOException
				{
					return encapsulateToJSONObject(m_FileManager.GetItemsAmount(i_Branch));
				}
				
				private void incItemAmount(JSONObject i_Data) throws NumberFormatException, JSONException, FileNotFoundException, IllegalArgumentException, IOException, DeleteFileException, RenameFileException, OutOfRangeException, NotFoundException
				{
					ItemAmount itemAmount = new ItemAmount(i_Data);
					m_FileManager.IncItemAmount(itemAmount);
				}
				
				//------------------------Purchase Functions------------------------//
				private JSONObject commitPurchase(JSONObject data)throws NumberFormatException, FileNotFoundException,JSONException, IllegalArgumentException,IncompatibleException, IOException, OutOfRangeException,NotFoundException,DeleteFileException, RenameFileException, CreateCustomerException, ParseException
				{
					Purchase purchase = new Purchase(data);	
					Purchase purchaseToReturn =  m_FileManager.CommitPurchaseAndUpdateCustomer(purchase);
					m_FileManager.SaveLog(getStringLog(purchaseToReturn.toString()));
					return purchaseToReturn.GetJSONObject();
				}
				
				private JSONObject getPurcaseTotalPrice(JSONObject data) throws JSONException, IOException,IncompatibleException, NotFoundException, CreateCustomerException, ParseException
				{
					Purchase purchase = new Purchase(data);
					double price = m_FileManager.GetTotalBill(purchase);
					JSONObject toReturn = new JSONObject();
					toReturn.put("price", price);
					return toReturn;
				}
				
				private void ChangePurcaseTrack(JSONObject i_Data) throws NumberFormatException, JSONException, FileNotFoundException, IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
				{
					PurchaseTrack purchaseTrackToChange = new PurchaseTrack(i_Data);
					m_FileManager.ChangePurchaseTrack(purchaseTrackToChange);
				}

				private JSONObject GetAllPurchaseTrack() throws JSONException, IOException
				{
					return encapsulateToJSONObject(m_FileManager.getAllPurchaseTrack());
				}

				//------------------------Reports Functions------------------------//
				private JSONObject getVipCustomers() throws FileNotFoundException, JSONException, IncompatibleException, IOException
				{
					return encapsulateToJSONObject(m_FileManager.GetVipCustomers());
				}
				
				private JSONObject getAllCustomers() throws FileNotFoundException, JSONException, IncompatibleException, IOException
				{
					return encapsulateToJSONObject(m_FileManager.GetAllCustomers());
				}
				
				private JSONObject getTodaySalesReport(JSONObject data) throws FileNotFoundException, JSONException, IncompatibleException, IOException
				{
					//Data contains Field to filter the data by(Category,ItemName)
					//The values of the Field
					
					String field = data.getString("field");
					eBranches branchToGetReportFrom;
					JSONArray arrValuesJSON;
					String [] values = null;
					
					if(!field.equals("branch"))
					{
						//Getting the valuse to filter by
						branchToGetReportFrom = m_LoggedInUser.getBranch();
						arrValuesJSON = data.getJSONArray("values");
						values = new String [arrValuesJSON.length()];
						
						for(int i=0;i<values.length;i++)
						{
							values[i] = arrValuesJSON.getString(i);
						}
					}
					else
					{
						branchToGetReportFrom = data.getJSONArray("values").getEnum(eBranches.class, 0);
					}
					
					return encapsulateToJSONObject(m_FileManager.GetTodaysSalesReport(branchToGetReportFrom,field,values));
				}
				
				private JSONObject getAllLogs() throws JSONException, IOException
				{
					return encapsulateToJSONObject(m_FileManager.GetAllLogs());
				}

				private JSONObject getCustomerById(JSONObject data) throws JSONException, FileNotFoundException, IncompatibleException, IOException, NotFoundException
				{
					String customerID = data.getString("ID");
					return m_FileManager.getCustomerByID(customerID);
				}

				private JSONObject getAllWorkers() throws JSONException, IOException, IncompatibleException
				{
					return encapsulateToJSONObject(m_FileManager.GetAllWorkers(m_LoggedInUser.getBranch()));
				}
				
				//Private Functions
				private JSONObject encapsulateToJSONObject(JSONArray arr)
				{
					JSONObject newJSONObject =  new JSONObject();
					
					newJSONObject.put("JSONArray", arr);
					
					return newJSONObject;
				}
				
				private String getStringLog(String i_Message)
				{
					StringBuilder logBuilder = new StringBuilder();
					logBuilder.append(String.format("| at %s |" , m_LogDateFormat.format(Calendar.getInstance().getTime())));
					logBuilder.append(String.format("by user %s %s (number:%s)| ",m_LoggedInUser.getFirstName(),m_LoggedInUser.getLastName(),m_LoggedInUser.getWorkerNumber()));
					logBuilder.append(i_Message);
					return logBuilder.toString();
				}

			}).start();
		}
	}
}