package FileAction.Exceptions;

@SuppressWarnings("serial")
public class AllReadyExistsException extends Exception
{
	private String m_Message;
	
	public AllReadyExistsException(String i_Field,String i_Value)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(i_Field);
		builder.append(" : ");
		builder.append(i_Value);
		builder.append(" all ready exists!");
		
		m_Message = builder.toString();
	}
	
	@Override
	public String getMessage()
	{
		return m_Message;
	}
}
