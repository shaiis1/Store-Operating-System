package FileAction.GetData;

import org.json.JSONArray;

public class GetAllStringData extends FileActionGetLines
{

	public GetAllStringData(String i_Path) {
		super(i_Path);
	}

	@Override
	protected void doOnEveryLine(String i_Line, JSONArray i_Array)
	{
		i_Array.put(i_Line);
	}

}
