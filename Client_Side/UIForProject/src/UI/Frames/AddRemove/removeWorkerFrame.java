package UI.Frames.AddRemove;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.json.JSONException;
import Customer.CreateCustomerException;
import Person.Person;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.Popups;
import UI.SendingActionListener;
import UI.Frames.AbstractFrames.sendingFrame;
import Workers.CreateWorkerException;
import Workers.Worker;

@SuppressWarnings("serial")
public class removeWorkerFrame extends sendingFrame{

	private JLabel workerNameLabel;
	private JComboBox<Person> workerName;
	private Worker workerToRemove;
	
	public removeWorkerFrame(){
		super();
		this.pack();
	}

	@Override
	protected String getFrameTitle(){
		return "Remove worker";
	}

	@Override
	protected JPanel createMainPanel() throws JSONException, UnknownHostException, IOException,
				FromServerException, CreateCustomerException, CreateWorkerException{
		JPanel myMainPanel = new JPanel();
		workerNameLabel = new JLabel("Choose worker -");
		workerName = new JComboBox<Person>(ServerFunctionExecuter.getInstance().GetAllWorkers());
		myMainPanel.add(workerNameLabel);
		myMainPanel.add(workerName);
		
		return myMainPanel;
	}

	@Override
	protected String getSendButtonName(){
		return "Remove!";
	}

	@Override
	protected SendingActionListener OnSendButtonClicked(){
		
		return new SendingActionListener(){
			
		      public void sendAction(ActionEvent e){
		    	  	
		    	  workerToRemove = (Worker) workerName.getSelectedItem();
		    	  try{
		    		  ServerFunctionExecuter.getInstance().RemoveWorker(workerToRemove);
		    	  }
		    	  catch (FromServerException fsex){
		    		  fsex.ShowPopup();
		    	  }
		    	  catch (IOException ioex){
		    		  Popups.ServerConnectionError(ioex.getMessage());
		    	  }  
		      }};
	}

	@Override
	protected String getOnSuccessMessage(){
		return String.format("%s removed successfuly!",workerToRemove.toString());
	}
} //end of removeWorkerFrame class
