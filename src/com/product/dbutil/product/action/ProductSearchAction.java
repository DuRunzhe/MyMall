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
import com.product.jsonUtils.ChangeImagePathTool;
import com.product.jsonUtils.JsonTools;

/**
 * @author drzwin32
 * 
 *
 */
public class ProductSearchAction extends HttpServlet {
	private ProductService service;
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the object.
	 */
	public ProductSearchAction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("UTF-8");//
		String path = request.getContextPath();
		PrintWriter out = response.getWriter();
		String search_field = request.getParameter("search_field");
		search_field=new String(search_field.getBytes("iso8859-1"),"utf-8");
		System.out.println("search_field="+search_field);
		// List<Object> params = new ArrayList<Object>();
		// params.add(search_field);

		List<Map<String, Object>> list = service.listProduct(search_field);
		list=ChangeImagePathTool.chageImagePath(list, request, response);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goods", list);
		if (list.size() > 0) {
			
			map.put("state", "001");

		} else {
			map.put("state", "012");
		}
		
//		List<Map<String, Object>> lists=new ArrayList<Map<String,Object>>();
//		lists.add(map);
		// 生成并输出Json
		String jsonString = JsonTools.createJsonString("json", map);
		System.out.println("ProductSearchAction生成的jsonString="+jsonString);
		out.println(jsonString);
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
		service = new ProductDao_forAndroid();//实例化
	}

}
