package NetworkMessage;

import org.json.JSONAble;
import org.json.JSONObject;

public class RequestMessage extends JSONAble
{
	private eFunctions m_Function;
	private JSONObject m_Data;
	
	public RequestMessage(eFunctions i_Function,JSONObject i_Data)
	{
		m_Function = i_Function;
		m_Data = i_Data;
	}
	
	public RequestMessage(JSONObject i_JSONData)
	{
		m_Function = i_JSONData.getEnum(eFunctions.class, "function");
		
		if(i_JSONData.has("data"))
		{
			m_Data = i_JSONData.getJSONObject("data");
		}
		else
		{
			m_Data = null;
		}
		

	}
	
	public eFunctions getFunction()
	{
		return m_Function;
	}
	
	public JSONObject getData()
	{
		return m_Data;
	}
}
