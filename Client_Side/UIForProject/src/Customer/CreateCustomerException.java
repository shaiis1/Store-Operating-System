package Customer;

@SuppressWarnings("serial")
public class CreateCustomerException extends Exception{
	
	String m_Message;
	
	public CreateCustomerException(String i_Message){
		m_Message = i_Message;
	}
	
	@Override
	public String getMessage(){
		return "Adding Customer Error:" + m_Message;
	}
}//end of CreateCustomerException
