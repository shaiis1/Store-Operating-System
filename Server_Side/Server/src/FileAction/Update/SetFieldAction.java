package FileAction.Update;

import java.io.PrintWriter;

import org.json.JSONObject;

public class SetFieldAction extends UpdateFileAction
{
	private String m_FieldToSet;
	private String m_ValueToSet;
	
	public SetFieldAction(String [] i_FieldsToSearch, String [] i_ValuesToSearch, String i_DBPath,String i_FieldToSet,String i_ValueToSet)
	{
		super(i_FieldsToSearch, i_ValuesToSearch, i_DBPath);
		m_FieldToSet = i_FieldToSet;
		m_ValueToSet = i_ValueToSet;
	}

	@Override
	protected void DoWhenFound(JSONObject i_Data, PrintWriter i_filePrinter)
	{
		i_Data.remove(m_FieldToSet);
		i_Data.put(m_FieldToSet,m_ValueToSet);
		i_filePrinter.println(i_Data.toString());
		i_filePrinter.flush();
	}
	

}
