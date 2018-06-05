package Workers;

import org.json.*;
import Person.Person;

public abstract class Worker extends Person
{
	protected eBranches m_Branch;
	protected String m_BankAccountNumber;
	protected String m_WorkerNumber;
	
	public Worker(String i_ID,String i_FirstName,String i_LastName,String i_PhoneNumber,eBranches i_Branch,String i_WorkerNumber,String i_BankAccountNumber)
	{
		super(i_ID,i_FirstName,i_LastName,i_PhoneNumber);
		m_Branch = i_Branch;
		m_WorkerNumber = i_WorkerNumber;
		m_BankAccountNumber = i_BankAccountNumber;
	}
	
	public Worker(Worker i_Worker)
	{
		this(i_Worker.m_ID,i_Worker.m_FirstName,i_Worker.m_LastName,i_Worker.m_PhoneNumber,i_Worker.m_Branch,"",i_Worker.m_BankAccountNumber);
		m_WorkerNumber = i_Worker.m_WorkerNumber;
	}
	
	public Worker(JSONObject i_WorkerData)
	{
		super(i_WorkerData.getString("ID"),i_WorkerData.getString("firstName"),i_WorkerData.getString("lastName"),i_WorkerData.getString("phoneNumber"));
		m_Branch = i_WorkerData.getEnum(eBranches.class, "branch");
		m_WorkerNumber = i_WorkerData.getString("workerNumber");
		m_BankAccountNumber = i_WorkerData.getString("bankAccountNumber");
	}
	
	public eBranches getBranch()
	{
		return m_Branch;
	}
	
	public String getWorkerNumber()
	{
		return m_WorkerNumber;
	}
	
	public void setWorkerNumber(String i_WorkerNumber)
	{
		m_WorkerNumber = i_WorkerNumber;
	}
	
	public String getBankAccountNumber()
	{
		return m_BankAccountNumber;
	}
	
	
	@Override
	public String toString()
	{
		return String.format("%s %s (ID:%s) ", m_FirstName,m_LastName,m_ID);
	}
}
