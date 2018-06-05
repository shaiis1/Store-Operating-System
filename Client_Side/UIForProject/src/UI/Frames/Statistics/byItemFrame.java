package UI.Frames.Statistics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import javax.swing.JCheckBox;
import java.util.Map.Entry;
import Items.ItemAmount;
import Items.eItemsName;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.Popups;
import UI.Frames.AbstractFrames.checkBoxTabledFrame;

@SuppressWarnings("serial")
public class byItemFrame extends checkBoxTabledFrame{
	
	public byItemFrame(){
		super();
	}

	@Override
	protected Vector<String> getColumnsNames(){

		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Item Name");
		columnNames.add("Amount");
		columnNames.add("Category");
		columnNames.add("Branch");
		return columnNames;
	}

	@Override
	protected String getFrameTitle(){
		return "By item report";
	}

	@Override
	protected ActionListener setCheckboxActionListener(JCheckBox checkBox){
		
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				int i;
				if(checkBox.isSelected()){
					Map<eItemsName, ItemAmount> dataFromServer;
					try{
						dataFromServer = ServerFunctionExecuter.getInstance().GetTodaySalesReporyByItemsName(new String [] {checkBox.getText()});
						Vector<Object> current_data;
						for(Entry<eItemsName, ItemAmount> Row : dataFromServer.entrySet()){
							current_data = new Vector<Object>();
							current_data.add(Row.getKey().name());
							current_data.add(Row.getValue().getAmount());
							current_data.add(Row.getValue().getItemCategory().toString());
							current_data.add(Row.getValue().getBranch().toString());
							data.add(current_data);
						}
					}
					catch (FromServerException fsex){
						fsex.ShowPopup();
					}
					catch(IOException ioex){
						Popups.ServerConnectionError(ioex.getMessage());
					}
				}
				else{
					boolean found = false;
					for(i = 0; i < data.size(); i++){
						if(data.get(i).get(0).toString().compareTo(checkBox.getText()) == 0){
							found = true;
							break;
						}
					}
					if(found){
						data.remove(i);
					}
				}
				UpdateTableData();
			}
		};
	}

	@Override
	protected String[] getCheckboxValues(){
		String[] names = new String[eItemsName.values().length];
		for(int i = 0; i < names.length; i++){
			names[i] = eItemsName.values()[i].name();
		}
		return names;
	}
} //end of byItemFrame

