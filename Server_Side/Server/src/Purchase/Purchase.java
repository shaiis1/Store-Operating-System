package Purchase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONAble;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Customer.CreateCustomerException;
import Customer.Customer;
import Customer.CustomerFactory;
import Items.Item;
import Items.ItemAmount;
import Workers.Cashier;
import Workers.eBranches;

public class Purchase extends JSONAble
{
	public static final DateFormat s_DateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private Date m_Date;
	private Map<Integer,ItemAmount> m_ItemsAmount;
	private Customer m_Buyer;
	private Cashier m_Cashier;
	private double m_TotalBill;
	
	public Purchase(Customer i_Buyer,Cashier i_Cashier,Date i_Date)
	{
		m_Date = i_Date;
		m_ItemsAmount = new LinkedHashMap<Integer,ItemAmount>();
		m_Buyer = i_Buyer;
		m_Cashier = i_Cashier;
		m_TotalBill = 0;
	}
	
	public Purchase(JSONObject i_JSONPurchase) throws JSONException, CreateCustomerException, ParseException
	{
		this(CustomerFactory.CreateCustomer(i_JSONPurchase.getJSONObject("buyer")),
			 new  Cashier(i_JSONPurchase.getJSONObject("cashier")),
			 s_DateFormat.parse(i_JSONPurchase.getString("date"))	 
			);
		m_TotalBill = i_JSONPurchase.getDouble("totalBill");
		JSONArray JSONArr = i_JSONPurchase.getJSONArray("items");
		ItemAmount current;
		
		for(int i=0;i<JSONArr.length();i++)
		{
			current = new ItemAmount(JSONArr.getJSONObject(i));
			m_ItemsAmount.put(current.hashCode(),current);
		}
	}
	
	public void AddAmount(Item i_Item,int i_Amount)
	{
		this.AddAmount(new ItemAmount(m_Cashier.getBranch(),i_Item,i_Amount));
	}

	public void AddAmount(ItemAmount i_ItemAmount)
	{
		
		if(m_ItemsAmount.containsKey(i_ItemAmount.hashCode()))
		{
			i_ItemAmount.setAmount( i_ItemAmount.getAmount() + m_ItemsAmount.get(i_ItemAmount.hashCode()).getAmount());
			m_ItemsAmount.remove(i_ItemAmount.hashCode());
		}
		
		m_ItemsAmount.put(i_ItemAmount.hashCode(), i_ItemAmount);
	}
	
	public void RemoveFromPurchase(ItemAmount i_ItemAmount)
	{
		m_ItemsAmount.remove(i_ItemAmount.hashCode());
	}
	
	public Map<Integer,ItemAmount> GetItemsAmount()
	{
		return m_ItemsAmount;
	}
	
	public JSONArray getItems()
	{
		JSONArray arrToReturn = new JSONArray();
		JSONObject itemAmountJSON;
		
		for(Map.Entry<Integer, ItemAmount> pair : m_ItemsAmount.entrySet())
		{
			itemAmountJSON = pair.getValue().GetJSONObject();
			arrToReturn.put(itemAmountJSON);
		}
		
		return arrToReturn;
	}
	
	public Date GetDate()
	{
		return m_Date;
	}
	
	public String getDate()
	{
		return s_DateFormat.format(m_Date);
	}
	
	public Cashier GetCashier()
	{
		return m_Cashier;
	}
	public JSONObject getCashier()
	{
		return m_Cashier.GetJSONObject();
	}
	
	public Customer GetBuyer()
	{
		return m_Buyer;
	}
	public JSONObject getBuyer()
	{
		return m_Buyer.GetJSONObject();
	}
	
	public void setTotalBill(double i_TotalBill)
	{
		m_TotalBill = i_TotalBill;
	}
	public double getTotalBill()
	{
		return m_TotalBill;
	}
	
	public eBranches getBranch()
	{
		return m_Cashier.getBranch();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s bought ", m_Buyer.toString()));
		
		for(Map.Entry<Integer, ItemAmount> pair : m_ItemsAmount.entrySet())
		{
			sb.append(String.format("%s, ", pair.getValue().toString()));
		}
		
		sb.append('.');
		sb.append(String.format("Total bill: %f.",m_TotalBill));
		
		return sb.toString();
	}

}
