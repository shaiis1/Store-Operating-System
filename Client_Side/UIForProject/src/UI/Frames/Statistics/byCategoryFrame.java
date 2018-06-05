package UI.Frames.Statistics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import javax.swing.JCheckBox;
import Items.ItemAmount;
import Items.eItemCategory;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.Popups;
import UI.Frames.AbstractFrames.checkBoxTabledFrame;

@SuppressWarnings("serial")
public class byCategoryFrame extends checkBoxTabledFrame{
	
	public byCategoryFrame(){
		super();
	}
	
	@Override
	protected Vector<String> getColumnsNames(){
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Category");
		columnNames.add("Amount");
		columnNames.add("Branch");
		return columnNames;
	}

	@Override
	protected String getFrameTitle(){
		return "Report by category";
	}

	@Override
	protected ActionListener setCheckboxActionListener(JCheckBox checkBox){
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				int i;
				if(checkBox.isSelected()){
					try{
						Map<eItemCategory, ItemAmount> dataFromServer = 
								ServerFunctionExecuter.getInstance().GetTodaysSalesReportByCategorys
										(new String [] {checkBox.getText()});
						
				  		for(Entry<eItemCategory,ItemAmount> Row : dataFromServer.entrySet()){
				  			data.add(new Vector<Object>(){{
				  				this.add(Row.getValue().getItemCategory());
				  				this.add(Row.getValue().getAmount());
							   	this.add(Row.getValue().getBranch().toString());
							   	}});
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
							found=true;
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
		String[] names = new String[eItemCategory.values().length];
		for(int i = 0; i < names.length; i++){
			names[i] = eItemCategory.values()[i].name();
		}
		return names;
	}
}//end of byCategoryFrame
