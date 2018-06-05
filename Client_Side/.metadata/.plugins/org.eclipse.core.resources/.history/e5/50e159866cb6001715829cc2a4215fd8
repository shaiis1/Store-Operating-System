package Items;

import org.json.JSONAble;
import org.json.JSONObject;

import Workers.eBranches;

public class ItemAmount extends JSONAble
{
	private Item m_Item;
	private int m_Amount;
	private eBranches m_Branch;
	
	public ItemAmount (eBranches i_Branch, Item i_Item, int i_Amount)
	{
		m_Item = i_Item;
		m_Amount = i_Amount;
		m_Branch = i_Branch;
	}
	
	public ItemAmount (eBranches i_Branch, eItemsName i_ItemName,eItemCategory i_Category, int i_Amount)
	{
		this(i_Branch,
			  new Item(i_ItemName,i_Category),
			  i_Amount);
	}

	public ItemAmount(JSONObject i_JSONItemAmont)
	{
		this(i_JSONItemAmont.getEnum(eBranches.class, "branch"),
			 i_JSONItemAmont.getEnum(eItemsName.class, "itemName"),
			 i_JSONItemAmont.getEnum(eItemCategory.class,"itemCategory"),
			 i_JSONItemAmont.getInt("amount")
			);
	}
	
	@Override
	public int hashCode()
	{
		return m_Branch.ordinal()*10000 + m_Item.getItemName().ordinal();
	}
	
	public Item GetItem()
	{
		return m_Item;
	}
	public eItemsName getItemName()
	{
		return m_Item.getItemName();
	}
	public eItemCategory getItemCategory()
	{
		return m_Item.getCategory();
	}
	public eBranches getBranch()
	{
		return m_Branch;
	}
	public int getAmount()
	{
		return m_Amount;
	}
	public void setAmount(int i_Amount)
	{
		m_Amount = i_Amount;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s (%d)",m_Item.getItemName().name(),m_Amount);
	}
}
