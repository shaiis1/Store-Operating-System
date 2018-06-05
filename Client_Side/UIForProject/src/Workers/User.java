package Workers;

import org.json.JSONException;
import org.json.JSONObject;

public class User extends Worker{
	
	String m_Password;
	String m_ActualType;
	
	public User(Worker i_Worker, String i_Password){
		super(i_Worker);
		m_Password = i_Password;
		m_ActualType = i_Worker.GetJSONObject().getString("type");
	}
	
	public User(JSONObject i_UserData) throws JSONException, CreateWorkerException{
		this(WorkerFactory.CreateWorker(i_UserData), i_UserData.getString("password"));
	}
	
	public String getPassword(){
		return m_Password;
	}
	
	@Override
	public JSONObject GetJSONObject(){
		JSONObject toReturn = super.GetJSONObject();
		toReturn.remove("type");
		toReturn.put("type", m_ActualType);
		
		return toReturn;
	}
}//end of User