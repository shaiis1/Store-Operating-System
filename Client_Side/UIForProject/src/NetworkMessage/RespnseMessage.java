package NetworkMessage;

import org.json.JSONObject;

public abstract class RespnseMessage
{
	private eDataStatus m_DataStatus;
	private JSONObject m_Data;
	
	public RespnseMessage(eDataStatus i_DataStatus,JSONObject i_Data)
	{
		m_DataStatus = i_DataStatus;
		m_Data = i_Data;
	}
	
	public RespnseMessage(eDataStatus i_DataStatus,String i_Message)
	{
		m_DataStatus = i_DataStatus;
		m_Data = new JSONObject();
		m_Data.put("message", i_Message);
	}
	
	public RespnseMessage(JSONObject i_JSONData)
	{
		m_DataStatus = i_JSONData.getEnum(eDataStatus.class, "dataStatus");
		m_Data = i_JSONData.getJSONObject("data");
	}
	
	public eDataStatus getDataStatus()
	{
		return m_DataStatus;
	}
	
	public JSONObject getData()
	{
		return m_Data;
	}
	
	public JSONObject GetJSONObject()
	{
		return new JSONObject(JSONObject.wrap(this).toString());
	}
}
