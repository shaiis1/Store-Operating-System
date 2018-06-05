package FileAction.Update;

import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

import FileAction.Exceptions.OutOfRangeException;

public class IncOrDecByFieldAction extends UpdateFileAction
{
	private int m_Amount;
	private String m_AmountField;
	
	public IncOrDecByFieldAction(String i_AmountField,String [] i_FieldsToSearch,String [] i_ValuesToSearch,String i_DBPath,int i_Amount)
	{
		super(i_FieldsToSearch, i_ValuesToSearch, i_DBPath);
		m_Amount = i_Amount;
		m_AmountField = i_AmountField;
	}

	@Override
	protected void DoWhenFound(JSONObject i_Data, PrintWriter i_filePrinter) throws JSONException,OutOfRangeException
	{
		int newAmount;
		int currentAmount = i_Data.getInt(m_AmountField);
		boolean isZero = false;
		
		newAmount = currentAmount + m_Amount;
		
		try
		{
			if(newAmount<0)
			{
				throw new OutOfRangeException("There is no enough amount(only "+currentAmount+ ") and you want "+((-1)*m_Amount));
			}
			if(newAmount==0)
			{
				isZero = true;
			}
			
			else if(newAmount>0)
			{
				i_Data.remove(m_AmountField);
				i_Data.put(m_AmountField, newAmount);
			}
		}
		finally
		{
			if(!isZero)//if is zero - remove!
			{
				i_filePrinter.println(i_Data.toString());
				i_filePrinter.flush();
			}
		}
	}
}
