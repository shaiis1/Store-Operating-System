package UI.Frames.All;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;
import org.json.JSONException;

import Workers.CreateWorkerException;
import Customer.CreateCustomerException;
import Customer.NewCustomer;
import Customer.ReturnedCustomer;
import Customer.VipCustomer;
import Person.Person;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.Frames.AbstractFrames.tableFrame;;

@SuppressWarnings("serial")
public class AllCustomersFrame extends tableFrame{
	
	public AllCustomersFrame(){
		super();
	}

	@Override
	protected Vector<Vector<Object>> createTableData() throws UnknownHostException, IOException,
				FromServerException, JSONException, CreateCustomerException, CreateWorkerException{
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		Vector<Person> dataFromServer = ServerFunctionExecuter.getInstance().GetAllCustomers();

		for(Person Row : dataFromServer){
			data.add(new Vector<Object>(){{
   	  			this.add(Row.getFirstName());
   	  			this.add(Row.getLastName());
   	  			this.add(Row.getID());
   	  			this.add(Row.getPhoneNumber());
   	  			if(Row instanceof NewCustomer){
   	  				this.add("New");
   	  			}
	   			else if(Row instanceof ReturnedCustomer){
	   				this.add("Returned");
	   			}
	   			else if(Row instanceof VipCustomer){
	   				this.add("VIP");
	   			}
   	  		}});
		}
		
		return data;
	}

	@Override
	protected Vector<String> getColumnsNames(){
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("First name");
		columnNames.add("Last name");
		columnNames.add("Id");
		columnNames.add("Phone");
		columnNames.add("Type");
		return columnNames;
	}

	@Override
	protected String getFrameTitle() {
		return "All Customers";
	}
}//end of AllCustomersFrame
