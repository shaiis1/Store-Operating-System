package UI.Frames.All;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;
import org.json.JSONException;
import Customer.CreateCustomerException;
import Person.Person;
import ServerFunctions.FromServerException;
import ServerFunctions.ServerFunctionExecuter;
import UI.Frames.AbstractFrames.tableFrame;
import Workers.Cashier;
import Workers.CreateWorkerException;
import Workers.Seller;
import Workers.ShiftManager;
import Workers.Worker;

@SuppressWarnings("serial")
public class AllEmployeesFrame extends tableFrame{
	
	public AllEmployeesFrame(){
		super();
	}

	@Override
	protected Vector<Vector<Object>> createTableData() throws UnknownHostException, IOException,
					FromServerException, JSONException, CreateCustomerException, CreateWorkerException{
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		Vector<Person> dataFromServer = ServerFunctionExecuter.getInstance().GetAllWorkers(); 
		
		for(Person person : dataFromServer){
			data.add(new Vector<Object>(){{
   	  			this.add(((Worker)person).getWorkerNumber());
   	  			this.add(person.getFirstName());
   	  			this.add(person.getLastName());
   	  			this.add(person.getPhoneNumber());
   	  			this.add(((Worker)person).getBankAccountNumber());
   	  			this.add(person.getID());
   	  			if(person instanceof Cashier){
   	  				this.add("Cashier");
	  			}
	  			else if(person instanceof Seller){
	  				this.add("Seller");
	  			}
	  			else if(person instanceof ShiftManager){
	  				this.add("Shift Manager");
	  			}
	   	  	}});
		}//end of for
		return data;
	}//end of createTableData() method

	@Override
	protected Vector<String> getColumnsNames(){

		Vector<String> columnNames = new Vector<String>();
		
		columnNames.add("Worker #");
		columnNames.add("First name");
		columnNames.add("Last name");
		columnNames.add("Phone");
		columnNames.add("Bank account");
		columnNames.add("Id");
		columnNames.add("Type");
		
		return columnNames;
	}

	@Override
	protected String getFrameTitle(){
		return "All Employees";
	}
}//end of ALLEmployeesFrame
