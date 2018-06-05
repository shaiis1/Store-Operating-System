package UI.Frames.Invetory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;
import org.json.JSONException;
import Customer.CreateCustomerException;
import Items.ItemAmount;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.Frames.AbstractFrames.tableFrame;

@SuppressWarnings("serial")
public class inventoryFrame extends tableFrame{
	
	public inventoryFrame(){
		super();
	}

	@Override
	protected Vector<Vector<Object>> createTableData() throws UnknownHostException, IOException,
				FromServerException, JSONException, CreateCustomerException{
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		Vector<ItemAmount> dataFromServer = ServerFunctionExecuter.getInstance().
				GetItemsAmount(ServerFunctionExecuter.getInstance().GetLoggedInUser().getBranch());
		
		for(ItemAmount Row : dataFromServer){
			data.add(new Vector<Object>(){{
   	  			this.add(Row.getItemName());
   	  			this.add(Row.getAmount());
   	  			this.add(Row.getItemCategory());
   	  			this.add(Row.getBranch());
   	  		}});
		}
		return data;
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
		return "Inventory";
	}
}//end of inventoryFrame