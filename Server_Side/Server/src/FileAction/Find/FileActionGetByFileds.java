package FileAction.Find;

import org.json.JSONException;
import org.json.JSONObject;

import FileAction.Exceptions.IncompatibleException;
import FileAction.Exceptions.NotFoundException;

public class FileActionGetByFileds extends FileActionFind
{
	private String [] m_FieldsToSearch;
	private String [] m_ValuesToSearch;
	private String m_StringToReturn;

	public FileActionGetByFileds(String i_Path,String [] i_FieldsToSearch,String [] i_ValuesToSearch) throws IncompatibleException
	{
		super(i_Path);
		if(i_FieldsToSearch.length != i_ValuesToSearch.length)
			throw new IncompatibleException("every field must have a value");
		m_FieldsToSearch = i_FieldsToSearch;
		m_ValuesToSearch = i_ValuesToSearch;
	}

	@Override
	protected boolean ConditionForSearch(String i_Line) throws JSONException
	{
		boolean result = true;
		JSONObject currentJSON = new JSONObject(i_Line);
		
		for (int i=0;i<m_FieldsToSearch.length;i++)
		{
			if(currentJSON.getString(m_FieldsToSearch[i]).compareTo(m_ValuesToSearch[i]) != 0)
			{
				result = false;
				break;
			}
		}
		
		return result;
	}

	@Override
	protected void WhenNotFound() throws NotFoundException
	{
		throw new NotFoundException(m_FieldsToSearch,m_ValuesToSearch);
	}

	@Override
	protected String StringToReturn()
	{
		return m_StringToReturn;
	}

	@Override
	protected boolean DoWhenFound(String i_Line)
	{
		m_StringToReturn = i_Line;
		return false;
	}
	

}
