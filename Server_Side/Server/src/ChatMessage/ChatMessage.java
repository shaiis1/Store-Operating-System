package ChatMessage;

import org.json.JSONAble;
import org.json.JSONException;
import org.json.JSONObject;

import Workers.CreateWorkerException;
import Workers.Worker;
import Workers.WorkerFactory;

public class ChatMessage extends JSONAble
{
	private Worker m_Sender;
	private Worker m_Reciver;
	private String m_Message;
	
	public ChatMessage(Worker i_Sender,Worker i_Reciver,String i_Message)
	{
		m_Sender = i_Sender;
		m_Reciver = i_Reciver;
		m_Message = i_Message;
	}
	public ChatMessage(JSONObject i_JSONChatMessage) throws JSONException, CreateWorkerException
	{
		m_Sender = WorkerFactory.CreateWorker(i_JSONChatMessage.getJSONObject("sender"));
		m_Reciver = WorkerFactory.CreateWorker(i_JSONChatMessage.getJSONObject("reciver"));
		m_Message = i_JSONChatMessage.getString("message");
	}
	
	public Worker GetSender()
	{
		return m_Sender;
	}
	
	public JSONObject getSender()
	{
		return m_Sender.GetJSONObject();
	}

	public Worker GetReciver()
	{
		return m_Reciver;
	}	
	
	public JSONObject getReciver()
	{
		return m_Reciver.GetJSONObject();
	}
	
	public String getMessage()
	{
		return m_Message;
	}
}
