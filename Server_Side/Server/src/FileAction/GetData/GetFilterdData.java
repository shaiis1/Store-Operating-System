package FileAction.GetData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class GetFilterdData extends FileActionGetLines
{

	public GetFilterdData(String i_Path)
	{
		super(i_Path);
	}

	@Override
	protected void doOnEveryLine(String i_Line, JSONArray i_Array) throws JSONException
	{
		JSONObject currentJSONObject = new JSONObject(i_Line);
		if(theCondition(currentJSONObject))
		{
			i_Array.put(currentJSONObject);
		}
	}

	protected abstract boolean theCondition(JSONObject i_CurrentJSONObject) throws JSONException;
}
