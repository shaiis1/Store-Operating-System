package Customer;

import org.json.JSONObject;

public class NewCustomer extends Customer{

	public NewCustomer(String i_Id, String i_FirstName, String i_LastName, String i_PhoneNumber){
		super(i_Id, i_FirstName, i_LastName, i_PhoneNumber);
	}
	
	public NewCustomer(JSONObject i_CustomerData){
		super(i_CustomerData);
	}
}//end of NewCustomer