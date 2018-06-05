package Items;

import org.json.JSONAble;
import org.json.JSONObject;

public class Item extends JSONAble{
	
	private eItemsName m_ItemName;
	private double m_Price;
	private eItemCategory m_Category;
	
	public Item (eItemsName i_ItemName){
		m_ItemName = i_ItemName;
		m_Price = 0;
		
		if(i_ItemName.equals(eItemsName.ButtonedShirt) || i_ItemName.equals(eItemsName.TShirt)){
			m_Category = eItemCategory.Shirt;
		}
		else if(i_ItemName.equals(eItemsName.JeansPants)|| i_ItemName.equals(eItemsName.TailoredPants)){
			m_Category = eItemCategory.Pants;
		}
		else{
			m_Category = eItemCategory.Upper;
		}
	}
	
	public Item (eItemsName i_ItemName, eItemCategory i_Category){
		m_ItemName = i_ItemName;
		m_Price = 0;
		m_Category = i_Category;
	}
	
	public Item (eItemsName i_ItemName, eItemCategory i_Category, double i_Price){
		this(i_ItemName, i_Category);
	}
	
	public Item (JSONObject i_JSONItem){
		this(i_JSONItem.getEnum(eItemsName.class, "itemName"), i_JSONItem.getEnum(eItemCategory.class, "category"),
				i_JSONItem.getDouble("price"));
	}
	
    @Override
    public int hashCode(){
        return m_ItemName.ordinal();
    }
	
	public eItemsName getItemName(){
		return m_ItemName;
	}
	
	public double getPrice(){
		return m_Price;
	}
	public void SetPrice(double i_Price){
		m_Price = i_Price;
	}
	
	public eItemCategory getCategory(){
		return m_Category;
	}
}//end of Item