package FileAction.GetData;

import org.json.JSONException;
import org.json.JSONObject;

import FileAction.Exceptions.IncompatibleException;

public class DataFilterByAnyValue extends GetFilterdDataByFields
{
	private String m_FieldForAnyValue;
	private String [] m_AnyValues;
	
	public DataFilterByAnyValue(String i_Path, String[] i_FieldsToFilterBy, String[] i_ValuseToFilterBy,String i_FieldForAnyValue,String [] i_AnyValues) throws IncompatibleException
	{
		super(i_Path, i_FieldsToFilterBy, i_ValuseToFilterBy);
		m_FieldForAnyValue  = i_FieldForAnyValue;
		m_AnyValues = i_AnyValues;
		
	}
	
	
	@Override
	protected boolean theCondition(JSONObject i_CurrentJSONObject) throws JSONException
	{
		boolean result = false;
		
		if(super.theCondition(i_CurrentJSONObject))
		{
			for(int i = 0; i <m_AnyValues.length;i++)
			{
				if(i_CurrentJSONObject.getString(m_FieldForAnyValue).equals(m_AnyValues[i]))
				{
					result = true;
					break;
				}
			}
		}
		
		return result;
	}
	

}
