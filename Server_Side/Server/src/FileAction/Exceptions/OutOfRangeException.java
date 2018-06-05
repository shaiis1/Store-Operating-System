package FileAction.Exceptions;

@SuppressWarnings("serial")
public class OutOfRangeException extends Exception
{
	String m_Message;
	public OutOfRangeException(String i_Message)
	{
		m_Message = i_Message;	
	}
	
	@Override
	public String getMessage()
	{
		return "Out Of Range! : "+m_Message;
	}
}
