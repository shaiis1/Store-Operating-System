package FileAction.Update;

import java.io.PrintWriter;

import org.json.JSONObject;

public class IncreaceFileIndexerAction extends UpdateFileAction
{

	public IncreaceFileIndexerAction(String i_DBPath) {
		super(null, null, i_DBPath);
	}

	@Override
	protected void DoWhenFound(JSONObject i_Data, PrintWriter i_filePrinter){
		i_filePrinter.println(i_Data);
		i_filePrinter.flush();
		
	}

}
