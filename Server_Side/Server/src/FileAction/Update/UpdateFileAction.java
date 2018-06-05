package FileAction.Update;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import FileAction.Exceptions.DeleteFileException;
import FileAction.Exceptions.NotFoundException;
import FileAction.Exceptions.OutOfRangeException;
import FileAction.Exceptions.RenameFileException;

public abstract class UpdateFileAction
{
	private String [] m_FieldsToSearch;
	private String [] m_ValuesToSearch;
	private String m_DBPath;
	
	public UpdateFileAction(String [] i_FieldsToSearch,String [] i_ValuesToSearch,String i_DBPath)
	{
		m_FieldsToSearch = i_FieldsToSearch;
		m_ValuesToSearch = i_ValuesToSearch;
		m_DBPath = i_DBPath;
	}
	
	public final void Execute() throws JSONException,IOException,FileNotFoundException,NumberFormatException
	,IllegalArgumentException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
	{
		int number;
		File inputFile = null;
        File tempFile = null;
		Scanner fileScanner = null;
        PrintWriter filePrinter = null;
        JSONObject json;
        String line;
        boolean found = false;
        
        try
        {
        	inputFile = new File(m_DBPath);
            tempFile = new File(inputFile.getAbsolutePath() + ".tmp");
            fileScanner = new Scanner(inputFile);
            filePrinter = new PrintWriter(tempFile);
            String s = fileScanner.nextLine();
        	number = Integer.parseInt(s);
            if(m_FieldsToSearch == null && m_ValuesToSearch==null)//indcates file index increacement
            {
            	found=true;
            	number++;
            }
        	filePrinter.println(number);
        	filePrinter.flush();
            
            while (fileScanner.hasNextLine())
            {
            	line = fileScanner.nextLine();
            	
                if (found || !isWhatIAmSearching(json = new JSONObject(line)))
                {
                	filePrinter.println(line);
                	filePrinter.flush();
                }
                else
                {
                	found=true;
                	DoWhenFound(json,filePrinter);//Injection Point
                }
            }
        }
        finally
        {
            
            filePrinter.close();
            fileScanner.close();
            
            if(!found)
            {
            	throw new NotFoundException(m_FieldsToSearch,m_ValuesToSearch);
            }
            //Delete the original file
            if (!inputFile.delete())
            {
                throw new DeleteFileException();
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
            {
            	throw new RenameFileException();
            }
        }
	}
	
	private boolean isWhatIAmSearching(JSONObject json)
	{
		boolean itIs = true;
		
    	for (int i=0;i<m_FieldsToSearch.length;i++)
    	{
    		if(!json.getString(m_FieldsToSearch[i]).equals(m_ValuesToSearch[i]))
    		{
    			itIs =false;
    			break;
    		}
    	}
    	
    	return itIs;
	}
	
	protected abstract void DoWhenFound(JSONObject i_Data,PrintWriter i_filePrinter) throws JSONException,OutOfRangeException;
}
