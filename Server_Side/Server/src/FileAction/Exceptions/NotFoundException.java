package FileAction.Exceptions;

@SuppressWarnings("serial")
public class NotFoundException extends Exception
{
	private String m_Message;
	
	public NotFoundException(String [] i_Fields, String [] i_Values)
	{
		StringBuilder builder = new StringBuilder();	
		builder.append("Couldnt Finde The Values :\n");
		
		for(int i=0;i<i_Values.length;i++)
		{
			builder.append(i_Fields[i]);
			builder.append(" : ");
			builder.append(i_Values[i]);
			builder.append(" , ");
		}
		
		m_Message = builder.toString();
		
	}
	
	public NotFoundException(String i_Field,String i_Value)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("Couldnt Finde The Value :\n");
		builder.append(i_Field);
		builder.append(" : ");
		builder.append(i_Value);
		builder.append(" , ");
		
		m_Message = builder.toString();
	}
	
	@Override
	public String getMessage()
	{
		return m_Message;
	}
}
