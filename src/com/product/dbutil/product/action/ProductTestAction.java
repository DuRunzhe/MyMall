package com.product.dbutil.product.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product.dbutil.product.dao.ProductDao_forAndroid;
import com.product.dbutil.product.service.ProductService;
import com.product.jsonUtils.JsonTools;

public class ProductTestAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService service;

	/**
	 * Constructor of the object.
	 */
	public ProductTestAction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String sql1 = "SELECT DISTINCT category FROM category";
		List<Object> params = new ArrayList<Object>();
		params = null;
		List<Map<String, Object>> categoryList = service.findMoreResult(sql1, params);// 主分类的集合
		//
		String sql2 = "SELECT * FROM category";
		List<Map<String, Object>> sub_categoryList = service.findMoreResult(sql2, null);
		//
		String sql3 = "SELECT * FROM sub_category";
		List<Map<String, Object>> s_sub_categoryList = service.findMoreResult(sql3, null);

		List<Object> oneList = new ArrayList<Object>();// 一级菜单
		for (Map<String, Object> map : categoryList) {
			oneList.add(map.get("category") + "");
		}
		Map<String, Object> twoMap = new HashMap<String, Object>();// 二级菜单
		Map<String, Object> threeMap = new HashMap<String, Object>();// 三级菜单
		for (Map<String, Object> map : categoryList) {
			String category = map.get("category") + "";
			List<Object> sub_categoryArray = new ArrayList<Object>();
			for (Map<String, Object> map2 : sub_categoryList) {
				if (category.equals(map2.get("category").toString())) {
					sub_categoryArray.add(map2.get("sub_category") + "");
				}
			}
			twoMap.put(category, sub_categoryArray);
			//
			for (Object sub_categoryStr : sub_categoryArray) {
				String sub_category = (String) sub_categoryStr;
				List<Object> s_sub_categoryArray = new ArrayList<Object>();
				for (Map<String, Object> map2 : s_sub_categoryList) {
					if (sub_category.equals(map2.get("sub_category").toString())) {
						s_sub_categoryArray.add(map2.get("s_sub_category"));
					}
				}
				threeMap.put(sub_category, s_sub_categoryArray);
			}

		}

		String oneListJson = JsonTools.createJsonString("json", oneList);
		String twoMapJson = JsonTools.createJsonString("json", twoMap);
		String threeMapJson = JsonTools.createJsonString("json", threeMap);
		System.out.println("执行ProductTestAction这个Servlet生成的oneListJson=" + oneListJson);
		System.out.println("执行ProductTestAction这个Servlet生成的twoMapJson=" + twoMapJson);
		System.out.println("执行ProductTestAction这个Servlet生成的threeMapJson=" + threeMapJson);
		out.println(oneListJson);
		out.println("------------------");
		out.println(twoMapJson);
		out.println("------------------");
		out.println(threeMapJson);
		
		request.setAttribute("oneListJson", oneListJson);
		request.setAttribute("twoMapJson", twoMapJson);
		request.setAttribute("threeMapJson", threeMapJson);
		try {
			request.getRequestDispatcher("/MyJsp.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// out.println(categoryList);
		// out.println("------------------");
		// out.println(twoMap);
		// out.println("------------------");
		// out.println(threeMap);
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		service = new ProductDao_forAndroid();// 接口实例化为实现类
	}

}
