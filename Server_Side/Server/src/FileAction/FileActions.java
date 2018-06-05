package FileAction;

import java.io.*;
import java.util.Scanner;

import org.json.JSONAble;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import FileAction.Exceptions.DeleteFileException;
import FileAction.Exceptions.IncompatibleException;
import FileAction.Exceptions.NotFoundException;
import FileAction.Exceptions.OutOfRangeException;
import FileAction.Exceptions.RenameFileException;
import FileAction.Find.FileActionGetByFileds;
import FileAction.GetData.DataFilterByAnyValue;
import FileAction.GetData.GetAllJSONData;
import FileAction.GetData.GetAllStringData;
import FileAction.GetData.GetFilterdDataByFields;
import FileAction.Update.IncOrDecByFieldAction;
import FileAction.Update.IncreaceFileIndexerAction;
import FileAction.Update.RemoveFromFileAction;
import FileAction.Update.SetFieldAction;
import FileAction.Update.UpdateFileAction;

public class FileActions
{
	String m_Path;
	
	public FileActions(String i_Path)
	{
		m_Path = i_Path;
	}
	
	public void AddToFile(String i_Data) throws IOException, NumberFormatException, JSONException,
	IllegalArgumentException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
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
			
			UpdateFileAction indexerUpdate = new IncreaceFileIndexerAction(m_Path);
			indexerUpdate.Execute();
		}
	}
	
	public void AddToFile(JSONAble i_Data) throws NumberFormatException, JSONException, IllegalArgumentException,
	IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
	{
		AddToFile(i_Data.GetJSONObject().toString());
	}
	
	public void RemoveFromFileByFields(String [] i_FieldsToSearch,String [] i_ValuesToSearch) throws NumberFormatException,
	JSONException, FileNotFoundException, IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
	{
		RemoveFromFileAction RemoveFromFile = new RemoveFromFileAction(i_FieldsToSearch, i_ValuesToSearch, m_Path);
		RemoveFromFile.Execute();
	}
	
	public boolean IsExistsByFields(String[] i_FieldsToSearch, String[] i_ValuesToSearch) throws FileNotFoundException
	{
		boolean result = true;
		
		try
		{
			GetLineByFileds(i_FieldsToSearch, i_ValuesToSearch);
		}
		catch(Exception ex)
		{
			result = false;
		}
		
		return result;
	}
	
	public void IncreaseByFields(String [] i_FieldsToSearch,String [] i_ValuesToSearch,int i_HowMuchToInc) throws NumberFormatException,
	JSONException, FileNotFoundException, IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
	{
		IncOrDecByFieldAction incAction = new IncOrDecByFieldAction("amount",i_FieldsToSearch,i_ValuesToSearch,m_Path,i_HowMuchToInc);;
		incAction.Execute();
	}
	public void DecreaseByFields(String [] i_FieldsToSearch,String [] i_ValuesToSearch,int i_HowMuchToDec) throws NumberFormatException,
	JSONException, FileNotFoundException, IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
	{
		IncOrDecByFieldAction decAction = new IncOrDecByFieldAction("amount",i_FieldsToSearch,i_ValuesToSearch,m_Path,-i_HowMuchToDec);;
		decAction.Execute();
	}
	
	public int GetCurrentIndexer() throws FileNotFoundException,NumberFormatException,IOException
	{
		int number = -1;
		File f = null;
		Scanner s = null;
			
		try
		{
			f=new File(m_Path);
			s= new Scanner(f);
			number = Integer.parseInt(s.nextLine());
		}
		finally
		{
			s.close();
		}
		return number;
	}
	public String GetLineByFileds(String [] i_FieldsToSearch, String [] i_ValuesToSearch) throws IncompatibleException, FileNotFoundException,
	IOException, NotFoundException
	{
		FileActionGetByFileds getByFields = new FileActionGetByFileds(m_Path, i_FieldsToSearch, i_ValuesToSearch);
		return getByFields.Execute();
	}
	public JSONObject GetJSONObjectByFields(String [] i_FieldsToSearch, String [] i_ValuesToSearch) throws JSONException, FileNotFoundException,
	IncompatibleException, IOException, NotFoundException
	{

		return new JSONObject(GetLineByFileds(i_FieldsToSearch,i_ValuesToSearch));
	}
	public String GetFieldValueByFields(String [] i_FieldsToSearch, String [] i_ValuesToSearch ,String i_FieldToReturn) throws JSONException,
	FileNotFoundException, IncompatibleException, IOException, NotFoundException
	{
		JSONObject currentJSON = GetJSONObjectByFields(i_FieldsToSearch, i_ValuesToSearch);
		return currentJSON.getString(i_FieldToReturn);
	}

	public void SetField(String [] i_FieldsToSearch,String [] i_ValuesToSearch,String i_FieldToSet,String i_ValueToSet) throws NumberFormatException,
	JSONException, FileNotFoundException, IllegalArgumentException, IOException, NotFoundException, DeleteFileException, RenameFileException, OutOfRangeException
	{
		SetFieldAction setField = new SetFieldAction(i_FieldsToSearch, i_ValuesToSearch, m_Path, i_FieldToSet, i_ValueToSet);
		setField.Execute();
	}
	public JSONArray GetFileAllJSONData() throws JSONException, IOException
	{
		GetAllJSONData allData = new GetAllJSONData(m_Path);
		return allData.Execute();
	}
	public JSONArray GetFileAllStringData() throws JSONException, IOException
	{
		GetAllStringData allData = new GetAllStringData(m_Path);
		return allData.Execute();
	}
	
	public JSONArray GetFileDataByFields(String [] i_FieldsToFilterBy,String [] i_ValuesToFilterBy) throws IncompatibleException,
	FileNotFoundException, JSONException, IOException
	{
		GetFilterdDataByFields filtedDataAction = new GetFilterdDataByFields(m_Path, i_FieldsToFilterBy, i_ValuesToFilterBy);
		return filtedDataAction.Execute();
	}
	public JSONArray GetFileDataByFieldsAndFiledAndAnyValue(String [] i_FieldsToFilterBy,String [] i_ValuesToFilterBy,String i_FieldForAnyValue,String [] i_AnyValue) throws FileNotFoundException,
	JSONException, IOException, IncompatibleException
	{
		DataFilterByAnyValue anyValueDataFilter = new DataFilterByAnyValue(m_Path,i_FieldsToFilterBy,i_ValuesToFilterBy, i_FieldForAnyValue, i_AnyValue);
		return anyValueDataFilter.Execute();
	}
}
