package UI.Frames.AddRemove;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.SendingActionListener;
import UI.Decorators.springUtilities;
import UI.InputCheck.inputCheck;
import UI.InputCheck.inputException;
import Workers.Cashier;
import Workers.Seller;
import Workers.ShiftManager;
import Workers.Worker;
import Workers.eBranches;
import Workers.eWorkers;

@SuppressWarnings("serial")
public class addNewWorkerFrame extends addNewCustomerFrame{
	
	private eWorkers jobTitle;
	
	private JTextField bankNumberTextField;
	private JTextField passwordTextField;
	private JComboBox<eWorkers> jobComboBox;
	
	private JLabel bankNumberLabel;
	private JLabel jobLabel;
	private JLabel passWordLabel;
	
	private String getBankNumber;
	private String getPassWord;
	
	private Worker newWorker;
	private String newWorkerNumber;

	public addNewWorkerFrame(){
		super();
		this.pack();
	}
	
	@Override
	protected String getFrameTitle(){
		return "Add new worker";
	}

	@Override
	protected JPanel createMainPanel(){
		JPanel myMainPanel = super.createMainPanel();
		JPanel centerPanel = new JPanel(new SpringLayout());
		bankNumberTextField = new JTextField(8);
		bankNumberLabel = new JLabel("Bank number -");
		jobComboBox = new JComboBox<eWorkers>(eWorkers.values());
		jobLabel = new JLabel("Job type -");
		passWordLabel = new JLabel("Password -");
		passwordTextField = new JTextField(10);
		
		centerPanel.add(bankNumberLabel);
		centerPanel.add(bankNumberTextField);
		centerPanel.add(jobLabel);
		centerPanel.add(jobComboBox);
		centerPanel.add(passWordLabel);
		centerPanel.add(passwordTextField);
		
		springUtilities.makeCompactGrid(centerPanel, 3, 2, 6, 6, 10, 10);
		myMainPanel.add(centerPanel);
		return myMainPanel;
	}

	@Override
	protected SendingActionListener OnSendButtonClicked(){
		return new SendingActionListener(){
		      public void sendAction(ActionEvent e) throws UnknownHostException, IOException, FromServerException, inputException{
		    	  eBranches branch;
		    	  
		    	  getFirstName = firstNameTextField.getText();
		    	  getLastName = lastNameTextField.getText();
		    	  inputCheck.checkName(getFirstName + getLastName);
		    	  
		    	  getPhoneNumber = phoneNumberTextField.getText();
		    	  inputCheck.checkPhone(getPhoneNumber);
		    	  
		    	  getId = idTextField.getText();
		    	  inputCheck.checkId(getId, "Id");
		    	  
		    	  getBankNumber = bankNumberTextField.getText();
		    	  inputCheck.checkId(getBankNumber, "Bank number");
		    	  
		    	  jobTitle = (eWorkers) jobComboBox.getSelectedItem();
		    	  
		    	  getPassWord = passwordTextField.getText().toString();
		    	  inputCheck.checkPass(getPassWord);
		    	  
		    	  branch = ServerFunctionExecuter.getInstance().GetLoggedInUser().getBranch();
		    	  
		    	  if(jobTitle.equals(eWorkers.Cashier)){
		    		  newWorker = new Cashier(getId, getFirstName,getLastName,getPhoneNumber,branch,"",getBankNumber);
		    	  }
		    	  else if(jobTitle.equals(eWorkers.Seller)){
		    		  newWorker = new Seller(getId, getFirstName,getLastName,getPhoneNumber,branch,"",getBankNumber);
		    	  }
		    	  else if(jobTitle.equals(eWorkers.ShiftManager)){
		    		  newWorker = new ShiftManager(getId, getFirstName,getLastName,getPhoneNumber,branch,"",getBankNumber);
		    	  }
		    	  
		    	  newWorkerNumber = ServerFunctionExecuter.getInstance().AddWorker(newWorker, getPassWord);
		      }};
	}
	
	@Override
	protected String getOnSuccessMessage(){
		return String.format("%s added Successfuly!\nworker number #%s!", newWorker.toString(),newWorkerNumber);
	}
}//end of addNewWorkerFrame
