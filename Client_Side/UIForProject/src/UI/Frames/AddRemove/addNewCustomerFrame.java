package UI.Frames.AddRemove;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import Customer.Customer;
import Customer.NewCustomer;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.SendingActionListener;
import UI.Decorators.springUtilities;
import UI.Decorators.superButton;
import UI.Frames.AbstractFrames.sendingFrame;
import UI.InputCheck.inputCheck;
import UI.InputCheck.inputException;

@SuppressWarnings("serial")
public class addNewCustomerFrame extends sendingFrame{
	
	protected JTextField firstNameTextField;
	protected JTextField lastNameTextField;
	protected JTextField phoneNumberTextField;
	protected JTextField idTextField;
	
	protected JLabel firstNameLabel;
	protected JLabel lastNameLabel;
	protected JLabel phoneNumberLabel;
	protected JLabel idLabel;
	
	protected String getFirstName;
	protected String getLastName;
	protected String getPhoneNumber;
	protected String getId;
	
	private Customer newCustomer;
	
	public addNewCustomerFrame(){
		super();
		this.pack();
	}

	@Override
	protected String getFrameTitle(){
		return "Add new customer";
	}

	@Override
	protected JPanel createMainPanel(){
		JPanel myMainPanel = new JPanel();
		JPanel upPanel = new JPanel(new SpringLayout());
		firstNameTextField = new JTextField(10);
		lastNameTextField = new JTextField(10);
		phoneNumberTextField = new JTextField(8);
		idTextField = new JTextField(10);
		sendButton = new superButton("send", Color.orange);
		
		firstNameLabel = new JLabel("First name -");
		lastNameLabel = new JLabel("Last name -");
		phoneNumberLabel = new JLabel("Phone number -");
		idLabel = new JLabel("Id number -");

		upPanel.add(firstNameLabel);
		upPanel.add(firstNameTextField);
		upPanel.add(lastNameLabel);
		upPanel.add(lastNameTextField);
		upPanel.add(phoneNumberLabel);
		upPanel.add(phoneNumberTextField);
		upPanel.add(idLabel);
		upPanel.add(idTextField);
		springUtilities.makeCompactGrid(upPanel, 4, 2, 6, 6, 10, 10);
		myMainPanel.add(upPanel);
		return myMainPanel;
	}

	@Override
	protected SendingActionListener OnSendButtonClicked(){
		return new SendingActionListener(){
			
		      public void sendAction(ActionEvent e) throws UnknownHostException, IOException, FromServerException, inputException{
		    	  getFirstName = firstNameTextField.getText();
		    	  getLastName = lastNameTextField.getText();
		    	  inputCheck.checkName(getFirstName + getLastName);
		    	  
		    	  getPhoneNumber = phoneNumberTextField.getText();
		    	  inputCheck.checkPhone(getPhoneNumber);
		    	  
		    	  getId = idTextField.getText();
		    	  inputCheck.checkId(getId, "Id");
		    	  
		    	  newCustomer = new NewCustomer(getId, getFirstName,getLastName,getPhoneNumber);
		    	  ServerFunctionExecuter.getInstance().AddCustomer(newCustomer); 
		      }
		 };
	}
	@Override
	protected String getSendButtonName(){
		return "Add";
	}

	@Override
	protected String getOnSuccessMessage(){
		return String.format("%s added Successfuly!", newCustomer.toString());
	}
}
