package Person;

@SuppressWarnings("serial")
public class NotFoundException extends Exception{
	
	private String m_Message;
	
	public NotFoundException(String i_Message){
		m_Message = i_Message;
	}
	
	@Override
	public String getMessage(){
		return "Not Found!: " + m_Message;
	}
}//end of NotFoundException