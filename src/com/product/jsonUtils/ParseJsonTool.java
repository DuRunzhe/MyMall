package com.product.jsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.product.jsonService.JsonService;

import org.json.*;

public class ParseJsonTool {

	/**
	 * 解析出从购物车删除操作生成的List集合的Json字符串
	 * 
	 * @param goods_ids
	 *            由List<object>生成的Json字符串
	 * @return List<object>集合
	 */
	public static List<Object> getGoodsIds(String goods_ids) {
		List<Object> list = new ArrayList<Object>();
		try {

			// JsonObject jsonObject=new JsonObject();
			// JsonElement jsonElement=jsonObject.get(goods_ids);
			Gson gson = new Gson();
			list = gson.fromJson(goods_ids, new TypeToken<List<Object>>() {
			}.getType());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * {"json":"[6817197, 11694822, 10633984]"}
	 * 
	 * @param json
	 * @return
	 */
	public static List<Object> getListFromJson(String json) {
		List<Object> list = new ArrayList<Object>();
		try {
			JSONObject jsonObject = new JSONObject(json);
			String arrString = jsonObject.getString("json");
			JSONArray jsonArray = new JSONArray(arrString);
			System.out.println("jsonArray=" + jsonArray.toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				System.out.println("jsonArray.get(i)" + jsonArray.get(i));
				list.add((int) jsonArray.get(i));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}

	/**
	 * {"goodsJson":
	 * "[{goods_image=http:\/\/192.168.0.201:8080\/manager\/upload\/nvzhuang2.jpg,
	 * goods_num=2, goods_name=女装2, goods_id=9778204},
	 * {goods_image=http:\/\/192.168.0.201:8080\/manager\/upload\/bangong2.jpg,
	 * goods_num=1, goods_name=屏保, goods_id=10633984}]"}
	 * 
	 * @param json
	 * @return
	 */
	public static List<Map<String, Object>> getGoodsListFromJson(String json) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		System.out.println("getGoodsListFromJson方法得到的Json字符串" + json);
		try {
			JSONObject jsonObject = new JSONObject(json);
			String arrString = jsonObject.getString("goodsJson");
			System.out.println("arrString=" + arrString);
			JSONArray jsonArray = new JSONArray(arrString);

			// JSONObject jsonObject = new JSONObject(json);
			// JSONArray jsonArray =jsonObject.getJSONArray("goodsJson");
			System.out.println("jsonArray=" + jsonArray.toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsObj = new JSONObject(jsonArray.getString(i));
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("goods_id", jsObj.getInt("goods_id"));
				//map.put("goods_name", jsObj.getInt("goods_name"));
				map.put("goods_num", jsObj.getInt("goods_num"));
				//map.put("goods_image", jsObj.getInt("goods_image"));
				list.add(map);
				// System.out.println("jsonArray.get(i)" + jsonArray.get(i));
				// list.add((int) jsonArray.get(i));

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
