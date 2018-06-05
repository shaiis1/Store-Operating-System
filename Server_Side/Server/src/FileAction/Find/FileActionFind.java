package FileAction.Find;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.json.JSONException;

import FileAction.Exceptions.NotFoundException;

public abstract class FileActionFind
{
	private String m_Path;
	
	public FileActionFind(String i_Path)
	{
		m_Path = i_Path;
	}
	
	public String Execute() throws FileNotFoundException,IOException, NotFoundException
	{
		String line = null;
		boolean found = false;
		boolean toCountinue = true;
		File f = null;
		Scanner s = null;

		try
		{
			f=new File(m_Path);
			s= new Scanner(f);
			
			//First line number
			s.nextLine();
			
			while(toCountinue && s.hasNextLine())
			{
				line =s.nextLine();
				
				if(ConditionForSearch(line))
				{
					toCountinue = DoWhenFound(line);
					found = true;
					break;
				}
			}
		}
		
		finally
		{
			s.close();
			if(!found)
			{
				WhenNotFound();
			}
		}
		
		return StringToReturn();
	}
	
	protected abstract String StringToReturn();
	protected abstract boolean DoWhenFound(String i_Line);
	protected abstract void WhenNotFound() throws NotFoundException;
	protected abstract boolean ConditionForSearch(String i_Line) throws JSONException;
}
