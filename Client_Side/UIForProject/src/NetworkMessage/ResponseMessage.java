package NetworkMessage;

import org.json.JSONAble;
import org.json.JSONObject;

public abstract class ResponseMessage extends JSONAble
{
	private eDataStatus m_DataStatus;
	private String m_Data;
	
	public ResponseMessage(eDataStatus i_DataStatus,JSONObject i_Data)
	{
		m_DataStatus = i_DataStatus;
		m_Data = i_Data.toString();
	}
	
	public ResponseMessage(eDataStatus i_DataStatus,String i_Message)
	{
		m_DataStatus = i_DataStatus;
		m_Data = i_Message;
	}
	
	public ResponseMessage(JSONObject i_JSONData)
	{
		m_DataStatus = i_JSONData.getEnum(eDataStatus.class, "dataStatus");
		m_Data = i_JSONData.getString("data");
	}
	
	public eDataStatus getDataStatus()
	{
		return m_DataStatus;
	}
	
	public String getData()
	{
		return m_Data;
	}
}
