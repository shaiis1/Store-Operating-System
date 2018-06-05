package UI.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import org.json.JSONException;

import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.Popups;
import Workers.Cashier;
import Workers.CreateWorkerException;
import Workers.Seller;
import Workers.ShiftManager;
import Workers.Worker;

@SuppressWarnings("serial")
public class panelsManager extends JPanel{
	private LoginPanel loginPanel;
	private WorkerPanel workerPanel;
	private int panelWidth;
	private int panelHeight;
	Worker user;
	
	public panelsManager(int frameWidth,int frameHeight){
		
		panelWidth = frameWidth;
		panelHeight = frameHeight;
		loginPanel = new LoginPanel(panelWidth,panelHeight);
		loginPanel.setVisible(true);
		this.add(loginPanel);
		addLoginEvent();
	}
	
	public void CommitLogIn(){
		if(user instanceof Cashier){
			workerPanel = new CashierPanel(panelWidth,panelHeight);
		}
		else if(user instanceof Seller){
			workerPanel = new SellerPanel(panelWidth,panelHeight);
		}
		else if(user instanceof ShiftManager){
			workerPanel = new ManagerPanel(panelWidth,panelHeight);
		}
		this.add(workerPanel);
		workerPanel.setVisible(true);
		workerPanel.StartChat();
		loginPanel.setVisible(false);
		addLogOutEvent();
	}
	
	private void addLoginEvent(){	
		loginPanel.getLogInButton().addActionListener(new ActionListener(){
		      @SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e){
		    	  try{
		    		  user = ServerFunctionExecuter.getInstance().SignIn(loginPanel.getUserName().getText(),
		    				  loginPanel.getPassword().getText());
		    		  CommitLogIn();
		    	  }
		    	  catch (FromServerException fse){
		    		  fse.ShowPopup();
		    	  }
		    	  catch (JSONException | CreateWorkerException ex){
					  Popups.ShowError("couldnt read data from net!\n"+ex.getMessage());
				  }
		    	  catch(IOException ioex){
		    		  Popups.ServerConnectionError(ioex.getMessage());
		    	  }		
		      }
		});	
	}
	
	private void addLogOutEvent(){
		workerPanel.getLogOutButton().addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e){
		    	  try{
		    		  ServerFunctionExecuter.getInstance().SignOut();
		    	  }
		    	  catch (IOException | FromServerException ignor){} //ignore
		    	  workerPanel.setVisible(false);
		          loginPanel.setVisible(true);
		      }
		});	
	}
} //end of panelsManager