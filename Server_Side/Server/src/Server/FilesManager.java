package Server;

import java.io.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.ValidationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Customer.*;
import FileAction.FileActions;
import FileAction.Exceptions.AllReadyExistsException;
import FileAction.Exceptions.DeleteFileException;
import FileAction.Exceptions.IncompatibleException;
import FileAction.Exceptions.NotFoundException;
import FileAction.Exceptions.OutOfRangeException;
import FileAction.Exceptions.RenameFileException;
import Workers.*;
import Items.Item;
import Items.ItemAmount;
import Person.Person;
import Purchase.Purchase;
import Purchase.PurchaseTrack;


public class FilesManager
{
	private static FilesManager m_Instance = null;
	
	//Files Paths.
	private final String db_workers_path = "DB_Workes.txt";
	private final String db_customers_path = "DB_Customers.txt";
	private final String db_items_path = "DB_Items.txt";
	private final String db_itemsAmount_path = "DB_ItemsAmount.txt";
	private final String db_purchasesrecordes_path = "DB_PurchasesRecordes.txt";
	private final String db_purchasetrack_path = "DB_PurchaseTracks.txt";
	private final String server_logs_path = "Server_Logs.txt";
	
	//Files Actions.
	private final FileActions m_Workers_DB = new FileActions(db_workers_path);
	private final FileActions m_Customers_DB = new FileActions(db_customers_path);
	private final FileActions m_ExistItems_DB = new FileActions(db_items_path);
	private final FileActions m_ItemsAmount_DB = new FileActions(db_itemsAmount_path);
	private final FileActions m_PurchaseRecordes_DB  = new FileActions(db_purchasesrecordes_path);
	private final FileActions m_PurchaseTracks_DB = new FileActions(db_purchasetrack_path);
	private final FileActions m_Server_Logs = new FileActions(server_logs_path);
	
	////------------------------SingelTone------------------------//
	private FilesManager()
	{
	}
	public static FilesManager CreateOrGetInstance()
	{
		if(m_Instance==null)
			m_Instance = new FilesManager();
		
		return m_Instance;
	}
	
	
	public Worker SignIn(String i_WorkerNumber, String i_Password) throws JSONException, FileNotFoundException, IncompatibleException, IOException, NotFoundException, CreateWorkerException, ValidationException 
	{
		JSONObject workerJSON;
		Worker user = null;
		String password = null;
		
		synchronized(m_Workers_DB)
		{
			workerJSON = m_Workers_DB.GetJSONObjectByFields(new String [] {"workerNumber"},new String [] {i_WorkerNumber});
		}
		
		password = workerJSON.getString("password");
		
		if(password.compareTo(i_Password)==0)
		{
			user = WorkerFactory.CreateWorker(workerJSON);
		}
		else
		{
			throw new ValidationException("Worker number or Password are incorrect!");
		}
		
		return user;
	}

	//------------------------Adding Functions------------------------//
	public String AddWorker(User i_User) throws NumberFormatException, FileNotFoundException, JSONException,IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException,OutOfRangeException, AllReadyExistsException
	{
		String workerNumber = "";
		
		synchronized(m_Workers_DB)
		{
			workerNumber = String.valueOf(m_Workers_DB.GetCurrentIndexer() + 1);
			i_User.setWorkerNumber(workerNumber);
			addPerson(m_Workers_DB,i_User);
		}
		
		return workerNumber;
	}
	public void AddCustomer(Customer i_Customer) throws NumberFormatException, FileNotFoundException, JSONException,IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException,OutOfRangeException, AllReadyExistsException
	{
		synchronized(m_Customers_DB)
		{
			addPerson(m_Customers_DB,i_Customer);
		}
	}
	private void addPerson(FileActions i_FileAction,Person i_Person) throws NumberFormatException, FileNotFoundException, JSONException, IllegalArgumentException,IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException, AllReadyExistsException
	{
		if(!i_FileAction.IsExistsByFields(new String [] {"ID"},new String [] {i_Person.getID()}))
		{	
			i_FileAction.AddToFile(i_Person);
		}
		else
		{
			throw new AllReadyExistsException("ID",i_Person.getID());
		}
		
	}
	
	//------------------------Removing Functions------------------------//
	public void RemoveWorker(Worker i_Worker) throws NumberFormatException, JSONException, FileNotFoundException, IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
	{
		synchronized(m_Workers_DB)
		{
			removePerson(m_Workers_DB,i_Worker);
		}
	}
	public void RemoveCustomer(Customer i_Customer) throws NumberFormatException, JSONException, FileNotFoundException, IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
	{
		synchronized(m_Customers_DB)
		{
			removePerson(m_Customers_DB,i_Customer);
		}
	}
	private void removePerson(FileActions i_FileActions,Person i_Person) throws NumberFormatException, JSONException, FileNotFoundException, IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
	{
		i_FileActions.RemoveFromFileByFields(new String [] {"ID"}, new String [] {i_Person.getID()});
	}

	//------------------------Items Functions------------------------//
	public JSONArray GetAllExistItems() throws JSONException, IOException
	{
		synchronized(m_ExistItems_DB)
		{
			return m_ExistItems_DB.GetFileAllJSONData();
		}
	}
	public JSONArray GetItemsAmount(eBranches i_Branch) throws FileNotFoundException, JSONException, IncompatibleException, IOException
	{
		synchronized(m_ItemsAmount_DB)
		{
			return m_ItemsAmount_DB.GetFileDataByFields(new String [] {"branch"},new String [] {i_Branch.name()});
		}
	}
	public void IncItemAmount(ItemAmount i_ItemAmount) throws NumberFormatException, JSONException, FileNotFoundException, IllegalArgumentException, IOException, DeleteFileException, RenameFileException, OutOfRangeException, NotFoundException
	{
		synchronized(m_ItemsAmount_DB)
		{
			try
			{
				m_ItemsAmount_DB.IncreaseByFields(new String [] {"branch","itemName"}, new String [] {i_ItemAmount.getBranch().name(),i_ItemAmount.getItemName().name()}, i_ItemAmount.getAmount());
			}
			catch (NotFoundException e)
			{
				m_ItemsAmount_DB.AddToFile(i_ItemAmount);
			}			
		}
	}

	//------------------------Purchase Functions------------------------//
	public Purchase CommitPurchaseAndUpdateCustomer(Purchase i_Purchase) throws FileNotFoundException, JSONException,IncompatibleException, IOException, OutOfRangeException, NumberFormatException, IllegalArgumentException, NotFoundException,DeleteFileException, RenameFileException
	{
		JSONObject reportJSON;
		
		
		if(!checkEnoughAmount(i_Purchase.GetCashier().getBranch(),i_Purchase.GetItemsAmount()))
		{
			throw new OutOfRangeException("The Items requairs too much amount!");
		}
			
		for(Map.Entry<Integer, ItemAmount> pair: i_Purchase.GetItemsAmount().entrySet())
		{
			//Decrecing the amount
			synchronized(m_ItemsAmount_DB)
			{
				m_ItemsAmount_DB.DecreaseByFields(new String [] {"branch","itemName"},new String [] {i_Purchase.getBranch().name(), pair.getValue().getItemName().name()}, pair.getValue().getAmount());
			}
			
			
			//adding a purchase recorde
			reportJSON = pair.getValue().GetJSONObject();
			reportJSON.put("date",i_Purchase.getDate());
			reportJSON.put("buyer", i_Purchase.getBuyer());
			
			synchronized(m_PurchaseRecordes_DB)
			{
				m_PurchaseRecordes_DB.AddToFile(reportJSON.toString());
			}
		}
		
		i_Purchase.setTotalBill(GetTotalBill(i_Purchase));
		
		upgradeCustomerType(i_Purchase.GetBuyer());
		
		return i_Purchase;
	}
	public double GetTotalBill(Purchase i_Purchase) throws JSONException, IOException, IncompatibleException, NotFoundException
	{
		double sum = 0;
		Map <Integer,Double> itemsPrices = getItemPrices();
		double discount = getCustomerDiscount(i_Purchase.GetBuyer());
		
		for(Map.Entry<Integer, ItemAmount> pair : i_Purchase.GetItemsAmount().entrySet())
		{
			ItemAmount itemAmount = pair.getValue();
			sum += itemsPrices.get(itemAmount.GetItem().hashCode()) * itemAmount.getAmount();
		}
		
		return sum - (sum *discount);//Returning the sum with the discount	
	}
	private Map <Integer,Double> getItemPrices() throws JSONException, IOException
	{
		JSONArray JSONExistItems = null;
		Map <Integer,Double> itemsPrices = new LinkedHashMap<Integer,Double>();
		
		synchronized (m_ExistItems_DB)//to know all updated prices
		{
			JSONExistItems = m_ExistItems_DB.GetFileAllJSONData();
		}
		
		for(int i=0;i<JSONExistItems.length();i++)//setting all prices in map by the item id
		{
			itemsPrices.put(new Item(JSONExistItems.getJSONObject(i)).hashCode(), JSONExistItems.getJSONObject(i).getDouble("price"));
		}
		
		return itemsPrices;
	}
	private double getCustomerDiscount(Customer i_Customer) throws JSONException, FileNotFoundException, IncompatibleException, IOException, NotFoundException
	{
		String customerType = getCustomerStringType(i_Customer);
		double discount;
		
		synchronized(m_PurchaseTracks_DB)//to know the discount of the customer type
		{
			discount = m_PurchaseTracks_DB.GetJSONObjectByFields(new String [] {"customerType"}, new String [] {customerType}).getDouble("discount");
		}
		
		return discount;
	}
	private String getCustomerStringType(Customer i_Customer)
	{
		String customerType;
		
		if(i_Customer instanceof NewCustomer)
		{
			customerType = NewCustomer.class.getSimpleName();
		}
		else if(i_Customer instanceof ReturnedCustomer)
		{
			customerType = ReturnedCustomer.class.getSimpleName();
		}
		else
		{
			customerType =  VipCustomer.class.getSimpleName();
		}
		
		return customerType;
	}
	private void upgradeCustomerType(Customer i_Customer) throws NumberFormatException, JSONException, FileNotFoundException, IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
	{
		String newType = null;
		
		//checking customer type
		if(i_Customer instanceof NewCustomer)
		{
			newType = ReturnedCustomer.class.getSimpleName();
		}
		else if(i_Customer instanceof ReturnedCustomer)
		{
			newType = VipCustomer.class.getSimpleName();
		}
		
		if(newType != null)//in case it is new or returned
		{
			synchronized(m_Customers_DB)
			{//upgrading in DB
				m_Customers_DB.SetField(new String [] {"ID"},new String [] {i_Customer.getID()}, "type", newType);
			}
		}
	}
	private boolean checkEnoughAmount(eBranches i_Branch,Map<Integer,ItemAmount> i_Items) throws FileNotFoundException, JSONException, IncompatibleException, IOException
	{
		boolean result = true;
		JSONArray branchItems = null;
		ItemAmount currentBranchItemAmount;
		
		synchronized(m_ItemsAmount_DB)
		{
			branchItems = m_ItemsAmount_DB.GetFileDataByFields(new String [] {"branch"},new String [] {i_Branch.name()});
		}
		
		for(int i=0;i<branchItems.length();i++)
		{
			currentBranchItemAmount = new ItemAmount(branchItems.getJSONObject(i));

			if(i_Items.containsKey(currentBranchItemAmount.hashCode()) &&
			   i_Items.get(currentBranchItemAmount.hashCode()).getAmount() > branchItems.getJSONObject(i).getInt("amount"))
			{
				result =  false;
				break;
			}
		}
		
		return result;
	}

	//------------------------Reports Functions------------------------//
	public JSONArray GetVipCustomers() throws FileNotFoundException, JSONException, IncompatibleException, IOException
	{
		synchronized(m_Customers_DB)
		{
			return m_Customers_DB.GetFileDataByFields(new String [] {"type"},new String [] {"VipCustomer"});
		}
	}	
	public JSONArray GetAllCustomers() throws FileNotFoundException, JSONException, IncompatibleException, IOException
	{
		synchronized(m_Customers_DB)
		{
			return m_Customers_DB.GetFileAllJSONData();
		}
	}	
	public JSONArray GetAllWorkers(eBranches i_Branch) throws JSONException, IOException, IncompatibleException
	{
		synchronized(m_Workers_DB)
		{
			return m_Workers_DB.GetFileDataByFields(new String[] {"branch"}, new String [] {i_Branch.name()});
		}
	}
	
	public JSONArray GetTodaysSalesReport(eBranches i_Branch,String i_Field,String [] i_Values) throws FileNotFoundException, JSONException,IncompatibleException, IOException
	{
		JSONArray data;
		Date today = new Date();
		String [] fildesToFilterBy = new String [] {"branch","date"};
		String [] valuesToFilterBy = new String [] {i_Branch.name(),Purchase.s_DateFormat.format(today)};
		
		if(i_Values == null)
		{
			data = m_PurchaseRecordes_DB.GetFileDataByFields(fildesToFilterBy, valuesToFilterBy);
		}
		else
		{
			data = m_PurchaseRecordes_DB.GetFileDataByFieldsAndFiledAndAnyValue(fildesToFilterBy,valuesToFilterBy,i_Field, i_Values);
		}
		
		return data;
	}	

	//------------------------LogsFunction Functions------------------------//
	public void SaveLog(String i_Log) throws NumberFormatException, JSONException,IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException,OutOfRangeException
	{
		synchronized(m_Server_Logs)
		{
			m_Server_Logs.AddToFile(i_Log);
		}
	}
	public JSONArray getAllPurchaseTrack() throws JSONException, IOException
	{
		synchronized(m_PurchaseTracks_DB)
		{
			return m_PurchaseTracks_DB.GetFileAllJSONData();
		}
	}
	public void ChangePurchaseTrack(PurchaseTrack i_PurchaseTrackToChange) throws NumberFormatException, JSONException, FileNotFoundException, IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
	{
		synchronized(m_PurchaseTracks_DB)
		{
			m_PurchaseTracks_DB.SetField(new String [] {"customerType"}, new String [] {i_PurchaseTrackToChange.getCustomerType()},"discount", String.valueOf(i_PurchaseTrackToChange.getDiscount()));
		}
	}
	public JSONObject getCustomerByID(String customerID) throws JSONException, FileNotFoundException, IncompatibleException, IOException, NotFoundException
	{
		return m_Customers_DB.GetJSONObjectByFields(new String [] {"ID"},new String [] {customerID});
	}
	
	public JSONArray GetAllLogs() throws JSONException, IOException
	{
		return m_Server_Logs.GetFileAllStringData();
	}
}
