package NetworkMessage;

import org.json.JSONObject;

public class ResponseMessageFactory
{
	
	public static ResponseMessage createResponseMessage(JSONObject i_Data)
	{
		ResponseMessage toReturn;
		if(i_Data.getEnum(eDataStatus.class, "dataStatus").equals(eDataStatus.Success))
		{
			try
			{
				toReturn = new SuccessMessage(i_Data.getString("data"));
			}
			catch(Exception ex)
			{
				toReturn = new SuccessMessage("");
			}
		}
		else
		{
			toReturn = new ErrorMessage(i_Data.getString("data"),i_Data.getEnum(eErrorType.class, "errorType"));
		}
		
		return toReturn;
	}
}
