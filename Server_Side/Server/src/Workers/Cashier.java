package Workers;

import org.json.JSONObject;

public class Cashier extends Worker
{

	public Cashier(String i_ID,String i_FirstName,String i_LastName,String i_PhoneNumber,eBranches i_Branch,String i_WorkerNumber,String i_BankAccountNumber)
	{
		super(i_ID, i_FirstName, i_LastName, i_PhoneNumber, i_Branch, i_WorkerNumber,i_BankAccountNumber);
	}
	
	public Cashier(JSONObject i_WorkerData)
	{
		super(i_WorkerData);
	}
}
