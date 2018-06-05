package Workers;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class CreateWorkerException extends Exception
{
	private String m_Message;
	
	public CreateWorkerException (String i_Message)
	{
		m_Message = i_Message;
	}
	
	public CreateWorkerException(JSONObject i_JSONWorker)
	{
		m_Message = "Couldnt Create Worker From JSON:\n" + i_JSONWorker.toString();
	}
	
	
	@Override
	public String getMessage()
	{
		return m_Message;
	}
}
