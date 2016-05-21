package com.product.jsonUtils;

import net.sf.json.JSONObject;

public class JsonTools {
	/**
	 * 根据提供的键值对参数创建Json字符串,//需要一个返回JsonUser对象的方法//
	 * @param key
	 * @param value
	 * @return
	 */
	public static String createJsonString(String key,Object value){
		String jsonString=null;
		JSONObject jsonObject=new JSONObject();
		jsonObject.put(key, value);
		jsonString=jsonObject.toString();
		return jsonString;
	}

}
