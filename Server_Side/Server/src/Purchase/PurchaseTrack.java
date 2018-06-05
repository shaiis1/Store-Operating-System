package Purchase;

import org.json.JSONAble;
import org.json.JSONObject;

public class PurchaseTrack extends JSONAble
{
	String m_CustomerType;
	double m_Discount;
	
	public PurchaseTrack(String i_CustomerType,double i_Discount)
	{
		m_CustomerType = i_CustomerType;
		m_Discount = i_Discount;
	}
	
	public PurchaseTrack(JSONObject i_PurchaseTrackJSON)
	{
		m_CustomerType = i_PurchaseTrackJSON.getString("customerType");
		m_Discount = i_PurchaseTrackJSON.getDouble("discount");
	}
	
	public String getCustomerType()
	{
		return m_CustomerType;
	}
	
	public double getDiscount()
	{
		return m_Discount;
	}
}
