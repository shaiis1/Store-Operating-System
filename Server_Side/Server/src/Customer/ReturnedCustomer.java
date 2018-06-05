package Customer;

import org.json.JSONObject;

public class ReturnedCustomer extends Customer
{

	public ReturnedCustomer(String i_Id, String i_FirstName, String i_LastName, String i_PhoneNumber)
	{
		super(i_Id, i_FirstName, i_LastName, i_PhoneNumber);
	}
	
	public ReturnedCustomer(JSONObject i_CustomerData)
	{
		super(i_CustomerData);
	}
}
