package FileAction.GetData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;

public abstract class FileActionGetLines
{
	private String m_Path;
	
	public FileActionGetLines(String i_Path)
	{
		m_Path = i_Path;
	}
	
	public JSONArray Execute() throws FileNotFoundException,JSONException,IOException
	{
		JSONArray allFileArray = new JSONArray();
		File inputFile = null;
		Scanner fileScanner = null;
		
        try
        {
        	inputFile = new File(m_Path);
            fileScanner = new Scanner(inputFile);
            fileScanner.nextLine();

            while (fileScanner.hasNextLine())
            {	
            	doOnEveryLine(fileScanner.nextLine(),allFileArray);//injection point
            }
        }
        finally
        {
            fileScanner.close();
        }
        
		return allFileArray;
	}
	
	protected abstract void doOnEveryLine(String i_Line,JSONArray i_Array);
	
}


