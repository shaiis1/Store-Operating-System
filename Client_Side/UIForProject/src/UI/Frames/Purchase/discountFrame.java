package UI.Frames.Purchase;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Purchase.PurchaseTrack;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.SendingActionListener;
import UI.Frames.AbstractFrames.sendingFrame;
import UI.InputCheck.inputCheck;
import UI.InputCheck.inputException;

@SuppressWarnings("serial")
public class discountFrame extends sendingFrame{
	
	private JLabel customerTypeLabel;
	private JComboBox<PurchaseTrack> customerType;
	private JLabel discountLabel;
	private JTextField discountTextField;
	private String selectedCustomer;
	private double newDiscount;

	public discountFrame(){
		super();
		this.pack();
	}

	@Override
	protected SendingActionListener OnSendButtonClicked(){
		return new SendingActionListener(){
		      public void sendAction(ActionEvent e) throws UnknownHostException, IOException, FromServerException, inputException{	
		    	  selectedCustomer = ((PurchaseTrack)customerType.getSelectedItem()).getCustomerType();
		    	  inputCheck.checkDiscount(discountTextField.getText());
		    	  newDiscount = Double.parseDouble(discountTextField.getText());
		    	  ServerFunctionExecuter.getInstance().ChangePurchaseTrack(selectedCustomer, newDiscount * 0.01);
		      }};
	}

	@Override
	protected String getFrameTitle(){
		return "Change discount";
	}

	@Override
	protected JPanel createMainPanel() throws UnknownHostException, IOException, FromServerException{
		JPanel myMainPanel = new JPanel();
		
		customerTypeLabel = new JLabel("Customer type -");
		customerType = new JComboBox<PurchaseTrack>(ServerFunctionExecuter.getInstance().GetAllPurchaseTrack());
		discountLabel = new JLabel("Discount % -");
		discountTextField = new JTextField(3);

		myMainPanel.add(customerTypeLabel);
		myMainPanel.add(customerType);
		myMainPanel.add(discountLabel);
		myMainPanel.add(discountTextField);
		
		return myMainPanel;
	}

	@Override
	protected String getSendButtonName(){
		return "Change";
	}

	@Override
	protected String getOnSuccessMessage(){
		return String.format("%s discount changed to %.2f", selectedCustomer, newDiscount);
	}
}//end of discountFrame