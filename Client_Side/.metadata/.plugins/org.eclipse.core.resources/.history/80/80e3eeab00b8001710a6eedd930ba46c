package UI.Frames.Statistics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import org.json.JSONException;
import Customer.CreateCustomerException;
import Customer.Customer;
import java.util.Map.Entry;
import javax.swing.JCheckBox;
import Items.ItemAmount;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.Popups;
import UI.Frames.AbstractFrames.checkBoxTabledFrame;
import Workers.eBranches;

@SuppressWarnings("serial")
public class byBranchFrame extends checkBoxTabledFrame{
	
	public byBranchFrame(){
		super();
	}

	@Override
	protected Vector<String> getColumnsNames(){
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Customer");
		columnNames.add("Item Name");
		columnNames.add("Category");
		columnNames.add("Amount");
		columnNames.add("Branch");
		return columnNames;
	}

	@Override
	protected String getFrameTitle(){
		return "Report by branch";
	}

	@Override
	protected ActionListener setCheckboxActionListener(JCheckBox checkBox){
		return new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				int i;
				if(checkBox.isSelected()){
					try{
						Map<Customer,Vector<ItemAmount>> dataFromServer = 
								ServerFunctionExecuter.getInstance().GetTodaySalesReporyByBranch(new String [] {checkBox.getText()});
						
						for(Entry<Customer, Vector<ItemAmount>> personPurcases : dataFromServer.entrySet()){
							for(ItemAmount itemAmount : personPurcases.getValue()){
								data.add(new Vector<Object>(){{
									this.add(personPurcases.getKey().toString());
					   	  			this.add(itemAmount.getItemName());
					   	  			this.add(itemAmount.getItemCategory());
					   	  			this.add(itemAmount.getAmount());
					   	  			this.add(itemAmount.getBranch());
								}});
							}
						}
					}
					catch (FromServerException fsex){
						fsex.ShowPopup();
					} 
					catch (JSONException | CreateCustomerException dataex) {
						Popups.ShowError("wrong data from server" + dataex.getMessage());
					}
					catch(IOException ioex){
						Popups.ServerConnectionError(ioex.getMessage());
					}
				}
				else{
					for(i = 0; i < data.size(); i++){
						if(data.get(i).get(4).toString().compareTo(checkBox.getText()) == 0){
							data.remove(i);
							i--;
						}
					}
						
				}
				UpdateTableData();
			}
		};
	}

	@Override
	protected String[] getCheckboxValues(){
		String[] names = new String[eBranches.values().length];
		for(int i = 0; i < names.length; i++){
			names[i] = eBranches.values()[i].name();
		}
		return names;
	}
}//end of byBranchFrame
