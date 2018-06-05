package FileAction.GetData;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetAllJSONData extends FileActionGetLines
{

	public GetAllJSONData(String i_Path)
	{
		super(i_Path);
	}

	@Override
	protected void doOnEveryLine(String i_Line, JSONArray i_Array)
	{
		i_Array.put((new JSONObject(i_Line)));
	}
	
}
