package NetworkMessage;

public class SuccessMessage extends ResponseMessage
{

	public SuccessMessage(String i_Data)
	{
		super(eDataStatus.Success, i_Data);
	}
	
}
