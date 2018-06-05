package UI.Frames.Purchase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import org.json.JSONException;
import Customer.CreateCustomerException;
import Customer.Customer;
import Items.ItemAmount;
import Purchase.Purchase;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.Popups;
import UI.SendingActionListener;
import UI.Decorators.superButton;
import UI.Frames.AbstractFrames.sendingFrame;
import UI.InputCheck.inputCheck;
import UI.InputCheck.inputException;
import Workers.Cashier;

@SuppressWarnings("serial")
public class purchaseFrame extends sendingFrame{

	private superButton addTocartButton;
	private superButton getCustomerButton;
	private JComboBox<ItemAmount> itemNameComboBox;
	private JLabel amountLabel;
	private JLabel customerLabel;
	private JLabel totalBillLable;
	private JTextField amountField;
	private JTextField customerIdField;
	private JPanel cartPanel;
	private JScrollPane cartScrollPane;
	private Customer currentCustomer;
	private Purchase currentPurchase;

	
	public purchaseFrame(){
		super();
		setVisible();
		addEvents();
	}
	
	@Override
	protected JPanel createMainPanel() throws UnknownHostException, IOException, FromServerException{
		JPanel myMainPanel = new JPanel();
			addTocartButton = new superButton("Add to cart", Color.orange);
			itemNameComboBox = new JComboBox<ItemAmount>(ServerFunctionExecuter.getInstance().
					GetItemsAmount(ServerFunctionExecuter.getInstance().GetLoggedInUser().getBranch()));
			itemNameComboBox.setPreferredSize(new Dimension((int)(getFrameWidth() * 0.53), (int)(getFrameHeight() * 0.08)));
			amountLabel = new JLabel("Amount: ");
			amountField = new JTextField(4);
			totalBillLable = new JLabel("00.00$");
			
			cartPanel = new JPanel();
			cartScrollPane = new JScrollPane(cartPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			cartScrollPane.setPreferredSize(new Dimension ((int)(getFrameWidth() * 0.8), (int)(getFrameHeight() * 0.46)));
			cartPanel.setPreferredSize(new Dimension (cartScrollPane.getPreferredSize().width, 
					(int)(cartScrollPane.getPreferredSize().height * 1.5)));
			
			customerLabel = new JLabel("Customer id");
			customerIdField = new JTextField(4);
			getCustomerButton = new superButton("Find customer", Color.orange);
			
			myMainPanel.add(customerLabel);
			myMainPanel.add(customerIdField);
			myMainPanel.add(getCustomerButton);
			myMainPanel.add(itemNameComboBox);
			myMainPanel.add(amountLabel);
			myMainPanel.add(amountField);
			myMainPanel.add(totalBillLable);
			myMainPanel.add(cartScrollPane);
			myMainPanel.add(addTocartButton);
		return myMainPanel;
	}
	
	private void setVisible(){
		Boolean foundCustomer;
		if(currentCustomer != null)
			foundCustomer = true;
		else
			foundCustomer = false;

		itemNameComboBox.setVisible(foundCustomer);
		amountLabel.setVisible(foundCustomer);
		amountField.setVisible(foundCustomer);
		addTocartButton.setVisible(foundCustomer);
		sendButton.setVisible(foundCustomer);
		cartScrollPane.setVisible(foundCustomer);
		totalBillLable.setVisible(foundCustomer);
		customerLabel.setVisible(!foundCustomer);
		customerIdField.setVisible(!foundCustomer);
		getCustomerButton.setVisible(!foundCustomer);
	}
	
	private void addEvents(){
		OnGetCustomerButtonClicked();
		OnAddToCartButtonClicked();
	}
	
	private void OnGetCustomerButtonClicked(){
		getCustomerButton.addActionListener(new SendingActionListener(){
			@Override
			public void sendAction(ActionEvent e)throws UnknownHostException, IOException, FromServerException,
							inputException, JSONException, CreateCustomerException{
				inputCheck.checkId(customerIdField.getText(), "ID");
				currentCustomer = ServerFunctionExecuter.getInstance().GetCustomerById(customerIdField.getText());
				currentPurchase = new Purchase(currentCustomer, 
						(Cashier)ServerFunctionExecuter.getInstance().GetLoggedInUser(), new Date());
				setVisible();
			}
		});
	}
	
	private void OnAddToCartButtonClicked(){
		addTocartButton.addActionListener(new SendingActionListener(){
			@Override
			public void sendAction(ActionEvent e)throws UnknownHostException, IOException, FromServerException,
							inputException, JSONException, CreateCustomerException{
				ItemAmount currentItemAmount;
				ItemAmount selectedItemAmount;
				if(itemNameComboBox.getItemCount() > 0){
					inputCheck.checkAmount(amountField.getText());
					
					selectedItemAmount = ((ItemAmount)itemNameComboBox.getSelectedItem());
					currentItemAmount = new ItemAmount(ServerFunctionExecuter.getInstance().GetLoggedInUser().getBranch(),
							selectedItemAmount.GetItem() , Integer.parseInt(amountField.getText()));
					
					inputCheck.checkEnoughAmount(currentItemAmount.getAmount(), selectedItemAmount.getAmount());
					
					currentPurchase.AddAmount(currentItemAmount);
					itemNameComboBox.removeItem(itemNameComboBox.getSelectedItem());
					ServerFunctionExecuter.getInstance().SetTotalBill(currentPurchase);
					totalBillLable.setText(currentPurchase.getTotalBill() + "$");
					JLabel currentLabel = new JLabel(currentItemAmount.getItemName() + ":" + currentItemAmount.getAmount());
					
					cartPanel.add(currentLabel);
					
					superButton labelRemoveButton = new superButton("Remove", Color.RED);
					labelRemoveButton.setPreferredSize(new Dimension 
							(cartScrollPane.getPreferredSize().width - currentLabel.getPreferredSize().width - 20, 
									currentLabel.getPreferredSize().height));
					
					labelRemoveButton.addActionListener(new SendingActionListener(){
		
							@Override
							public void sendAction(ActionEvent e)throws UnknownHostException, IOException, FromServerException,
										inputException, JSONException, CreateCustomerException{
								currentPurchase.RemoveFromPurchase(currentItemAmount);
								ServerFunctionExecuter.getInstance().SetTotalBill(currentPurchase);
								totalBillLable.setText(String.format("%.2f$", currentPurchase.getTotalBill()));
								cartPanel.remove(currentLabel);
								cartPanel.remove(labelRemoveButton);
								cartPanel.revalidate();
								cartPanel.repaint();
								itemNameComboBox.addItem(selectedItemAmount);
							}
						});
						cartPanel.add(labelRemoveButton);
						amountField.setText("");
						cartPanel.revalidate();
						cartPanel.repaint();
				}
				else{
					Popups.ShowWarning("No items to choose!");
				}
			}
		});
	}
	
	@Override
	protected String getSendButtonName(){
		return "New purchase";
	}

	@Override
	protected SendingActionListener OnSendButtonClicked(){
		return new SendingActionListener(){
		      public void sendAction(ActionEvent e) throws UnknownHostException, IOException, FromServerException, inputException{
		    	  ServerFunctionExecuter.getInstance().CommitPurchace(currentPurchase);
		      }
		 };
	}

	@Override
	protected String getFrameTitle(){
		return "Add new purchase";
	}

	@Override
	protected int getFrameWidth(){
		return (int)(Toolkit.getDefaultToolkit().getScreenSize().width * 0.22);
	}
	
	@Override
	protected int getFrameHeight(){
		return (int)(Toolkit.getDefaultToolkit().getScreenSize().height * 0.33);
	}

	@Override
	protected String getOnSuccessMessage(){
		return currentPurchase.PrintReceipt();
	}
}//end of purchaseFrame