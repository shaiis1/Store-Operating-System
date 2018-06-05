package FileAction.GetData;

import org.json.JSONException;
import org.json.JSONObject;

import FileAction.Exceptions.IncompatibleException;

public class GetFilterdDataByFields extends GetFilterdData
{
	protected String [] m_FieldsToFilterBy;
	protected String [] m_ValuseToFilterBy;
	
	
	public GetFilterdDataByFields(String i_Path,String [] i_FieldsToFilterBy,String [] i_ValuseToFilterBy) throws IncompatibleException {
		super(i_Path);
		
		if(i_FieldsToFilterBy.length !=  i_ValuseToFilterBy.length)
			throw new IncompatibleException("every field must have a value");
		m_FieldsToFilterBy = i_FieldsToFilterBy;
		m_ValuseToFilterBy = i_ValuseToFilterBy;
	}


	@Override
	protected boolean theCondition(JSONObject i_CurrentJSONObject) throws JSONException
	{
		boolean result = true;
		
		for(int i=0;i<m_FieldsToFilterBy.length;i++)
		{
			if(!i_CurrentJSONObject.getString(m_FieldsToFilterBy[i]).equals(m_ValuseToFilterBy[i]))
			{
				result = false;
				break;
			}
		}
		
		return result;
	}
	
}
