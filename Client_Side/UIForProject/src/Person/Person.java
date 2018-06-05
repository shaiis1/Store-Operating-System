package Person;

import org.json.JSONAble;
import org.json.JSONObject;

public abstract class Person extends JSONAble{
	
	protected String m_ID;
	protected String m_FirstName;
	protected String m_LastName;
	protected String m_PhoneNumber;
	
	public Person(String i_ID, String i_FirstName, String i_LastName, String i_PhoneNumber){
		m_ID = i_ID;
		m_FirstName = i_FirstName;
		m_LastName = i_LastName;
		m_PhoneNumber = i_PhoneNumber;
	}
	
	public String getID(){
		return m_ID;
	}

	public String getFirstName(){
		return m_FirstName;
	}
	
	public void setFirstName(String i_FirstName){
		m_FirstName = i_FirstName;
	}
	
	public String getLastName(){
		return m_LastName;
	}
	
	public void setLasttName(String i_LastName){
		m_LastName = i_LastName;
	}
	
	public String getPhoneNumber(){
		return m_PhoneNumber;
	}
	
	@Override
	public int hashCode(){
		return m_ID.hashCode();
	}
	
	@Override
	public boolean equals(Object other){
		if(other != null){
			int i1 = m_ID.hashCode();
			int i2 = other.hashCode();
			return i1 == i2;
		}
		return false;
	}
	
	@Override
	public JSONObject GetJSONObject(){
		JSONObject typedJSONObject = super.GetJSONObject();
		
		typedJSONObject.put("type", this.getClass().getSimpleName());
		
		return typedJSONObject;
	}
	
	@Override
	public String toString(){
		return String.format("%s %s (%s)", m_FirstName, m_LastName, m_ID);
	}
}//end of Person