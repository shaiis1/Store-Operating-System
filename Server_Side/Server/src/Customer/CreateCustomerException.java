package Customer;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class CreateCustomerException extends Exception
{
	String m_Message;
	
	public CreateCustomerException(String i_Message)
	{
		m_Message = i_Message;
	}
	
	public CreateCustomerException(JSONObject i_JSONCustoer)
	{
		m_Message = "Couldnt Create Customer From JSON:\n" + i_JSONCustoer.toString();
	}
	
	@Override
	public String getMessage()
	{
		return m_Message;
	}
}
