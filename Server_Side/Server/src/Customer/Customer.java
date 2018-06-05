package Customer;
import org.json.*;
import Person.Person;

public abstract class Customer extends Person
{	
	public Customer(String i_Id, String i_FirstName, String i_LastName, String i_PhoneNumber)
	{
		super(i_Id,i_FirstName,i_LastName,i_PhoneNumber);
	}
	
	public Customer(JSONObject i_CustomerData)
	{
		super(i_CustomerData.getString("ID"),i_CustomerData.getString("firstName"),i_CustomerData.getString("lastName"),i_CustomerData.getString("phoneNumber"));
	}
}
