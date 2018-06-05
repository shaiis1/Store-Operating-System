package FileAction.Update;

import java.io.PrintWriter;

import org.json.JSONObject;

public class RemoveFromFileAction extends UpdateFileAction{

	public RemoveFromFileAction(String [] i_FieldsToSearch, String [] i_ValuesToSearch, String i_DBPath) {
		super(i_FieldsToSearch, i_ValuesToSearch, i_DBPath);
	}

	@Override
	protected void DoWhenFound(JSONObject i_Data, PrintWriter i_filePrinter)
	{
		//Do nothing actually removes :)
	}
	
}
