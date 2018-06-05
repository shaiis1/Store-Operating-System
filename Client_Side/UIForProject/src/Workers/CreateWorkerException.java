package Workers;

@SuppressWarnings("serial")
public class CreateWorkerException extends Exception{
	
	private String m_Message;
	
	public CreateWorkerException (String i_Message){
		m_Message = i_Message;
	}
	
	@Override
	public String getMessage(){
		return "Adding Worker Error " + m_Message;
	}
}//end of CreateWorkerException