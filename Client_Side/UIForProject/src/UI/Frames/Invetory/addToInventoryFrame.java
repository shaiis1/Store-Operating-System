package UI.Frames.Invetory;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Items.Item;
import Items.eItemsName;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.SendingActionListener;
import UI.Frames.AbstractFrames.sendingFrame;

@SuppressWarnings("serial")
public class addToInventoryFrame extends sendingFrame{
	
	private JComboBox<eItemsName> itemsNamesComboBox;
	private JTextField amountToIncTextField;

	public addToInventoryFrame(){
		super();
		this.pack();
	}
	
	@Override
	protected JPanel createMainPanel(){
		JPanel myMainPanel = new JPanel();
		
		itemsNamesComboBox = new JComboBox<eItemsName>(eItemsName.values());
		amountToIncTextField = new JTextField(6);		
		
		myMainPanel.add(itemsNamesComboBox);
		myMainPanel.add(amountToIncTextField);
		
		return myMainPanel;
	}
	
	@Override
	protected String getSendButtonName(){
		return "Order";
	}
	
	@Override
	protected SendingActionListener OnSendButtonClicked(){
		
		return new SendingActionListener(){
			
			@Override
			public void sendAction(ActionEvent e) throws UnknownHostException, IOException, FromServerException{
				ServerFunctionExecuter.getInstance().IncItemAmount(new Item((eItemsName) itemsNamesComboBox.getSelectedItem()),
						Integer.parseInt(amountToIncTextField.getText()));
			}
		};
	}

	@Override
	protected String getFrameTitle(){
		return "Order items";
	}

	@Override
	protected String getOnSuccessMessage(){
		return "Added successfuly!";
	}
}//end of AddToInventoryFrame
