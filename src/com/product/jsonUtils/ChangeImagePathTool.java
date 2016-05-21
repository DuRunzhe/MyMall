package com.product.jsonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeImagePathTool {
	public static List<Map<String, Object>> chageImagePath(List<Map<String, Object>> list, HttpServletRequest request, HttpServletResponse response) {
		// request.getContextPath()+"\\upload\\"+map.get("goods_image"
		// http://122.207.132.185:8080/manager/servlet/ProductAction_forAndroid?action_flag=list&pageNum=1
//		String ip = request.getRemoteHost();
		String ip = request.getLocalAddr();
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> map : list) {
			String path = "http://" + ip + ":8080" + request.getContextPath() + "/upload/" + map.get("goods_image");
			map.put("goods_image", path);
			newList.add(map);
		}
		return newList;
	}

	/**
	 * 更改用户头像图片路径为Http地址
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	public static Map<String, Object> changeUserImagePath(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
//		String ip = request.getRemoteHost();
		String ip = request.getLocalAddr();
		String user_id = map.get("user_id").toString();
		String path = "http://" + ip + ":8080" + request.getContextPath() + "/upload/" + user_id + "/" + map.get("profile_image").toString();
		map.put("profile_image", path);

		return map;
	}

	public static Map<String, Object> changeUserImagePath(String key, Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
		//String ip = request.getRemoteHost();
		String ip = request.getLocalAddr();
		String user_id = map.get("user_id").toString();
		String path = "http://" + ip + ":8080" + request.getContextPath() + "/upload/" + user_id + "/" + map.get(key).toString();
		map.put(key, path);
		return map;

	}

	/**
	 * 更改用户头像图片路径为Http地址
	 * 
	 * @param imagePath
	 * @param user_id
	 * @param request
	 * @param response
	 * @return
	 */
	public static String changeUserImagePath(String imagePath, String user_id, HttpServletRequest request, HttpServletResponse response) {
		//String ip = request.getRemoteHost();
		String ip = request.getLocalAddr();
		String path = "http://" + ip + ":8080" + request.getContextPath() + "/upload/" + user_id + "/" + imagePath;

		return path;

	}

	/**
	 * 更改用户头像图片路径为Http地址
	 * 
	 * @param list
	 * @param request
	 * @param response
	 * @return
	 */
	public static List<Map<String, Object>> changeUserImagePath(List<Map<String, Object>> list, HttpServletRequest request, HttpServletResponse response) {
		//String ip = request.getRemoteHost();
		String ip = request.getLocalAddr();
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> map : list) {
			String path = "http://" + ip + ":8080" + request.getContextPath() + "/upload/" + map.get("user_id") + "/" + map.get("user_image");
			map.put("user_image", path);
			newList.add(map);
		}
		return newList;

	}

	/**
	 * 改变商品的图片
	 * 
	 * @param imagePath
	 * @param request
	 * @param response
	 * @return 新的图片路径字符串
	 */
	public static String changeProductImagePath(String imagePath, HttpServletRequest request, HttpServletResponse response) {
//		String ip = request.getRemoteHost();
		 Calendar c = Calendar.getInstance(); 
		 try {
			c.setTime(new SimpleDateFormat("yyyyMMddHHmmss").parse("1467378181000"));
			System.out.println(c.getTimeInMillis());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println();
		String ip = request.getLocalAddr();
		System.out.println("request.getLocalAddr()="+request.getLocalAddr());
		String path = "http://" + ip + ":8080" + request.getContextPath() + "/upload/"+imagePath;
		return path;
	}
}
