package NetworkMessage;

public class ErrorMessage extends ResponseMessage
{
	private eErrorType m_ErrorType;
	
	public ErrorMessage(String i_Data,eErrorType i_ErrorType)
	{
		super(eDataStatus.Error, i_Data);
		m_ErrorType = i_ErrorType;
	}
	
	public eErrorType getErrorType()
	{
		return m_ErrorType;
	}
}
