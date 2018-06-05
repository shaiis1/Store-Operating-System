package FileAction;

import java.io.*;
import org.json.JSONObject;

public class AddToFileAction
{
	private String m_Path;
	
	public AddToFileAction(String i_Path)
	{
		m_Path = i_Path;
	}
	public void Execute(JSONObject i_Data) throws IOException
	{
		FileWriter fw = null;
		BufferedWriter bw  = null;
		PrintWriter pw = null;
		
		try
		{
		    fw = new FileWriter(m_Path, true);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			
			pw.println(i_Data.toString());
		}
		finally
		{
			if( pw != null )
			{
				pw.close();
			}
			if( bw != null )
			{
			  	bw.close();
			}
			if( fw != null )
			{
			    fw.close();
			}
		}
	}

}
