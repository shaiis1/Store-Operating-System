package UI.Frames.Statistics;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;
import org.json.JSONException;
import Customer.CreateCustomerException;
import Customer.VipCustomer;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.Frames.AbstractFrames.tableFrame;
import Workers.CreateWorkerException;

@SuppressWarnings("serial")
public class AllVipCustomersFrame extends tableFrame{
	
	public AllVipCustomersFrame(){
		super();
	}

	@Override
	protected Vector<Vector<Object>> createTableData()  throws UnknownHostException, IOException, FromServerException,
					JSONException, CreateCustomerException, CreateWorkerException{
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		Vector<VipCustomer> dataFromServer = ServerFunctionExecuter.getInstance().GetVIPCustomers();
		for(VipCustomer Row : dataFromServer){
			data.add(new Vector<Object>(){{
	   	  		this.add(Row.getFirstName());
	   	  		this.add(Row.getLastName());
	   	  		this.add(Row.getID());
	   	  		this.add(Row.getPhoneNumber());
			}});//end of data adding
		}//end of for
		
		return data;
	}

	@Override
	protected Vector<String> getColumnsNames(){
		 Vector<String> columnNames = new Vector<String>();
		 columnNames.add("First name");
		 columnNames.add("Last name");
		 columnNames.add("Id");
		 columnNames.add("Phone");
		 return columnNames;
	}

	@Override
	protected String getFrameTitle(){
		return "Vip Customers";
	}
}//end of AllVipCustomersFrame
