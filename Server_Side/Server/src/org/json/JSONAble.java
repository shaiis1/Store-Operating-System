package org.json;

public abstract class JSONAble
{
	public JSONObject GetJSONObject()
	{
		return new JSONObject(JSONObject.wrap(this).toString());
	}
}
