package com.product.dbutil.product.androidaction;

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

import com.product.dbutil.goodscar.dao.GoodsCarDao;
import com.product.dbutil.goodscar.service.GoodsCarService;
import com.product.jsonUtils.ChangeImagePathTool;
import com.product.jsonUtils.JsonTools;

public class SearchGoodscarAction extends HttpServlet {
	private GoodsCarService service;
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public SearchGoodscarAction() {
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
		PrintWriter out = response.getWriter();
		String user_id = request.getParameter("user_id");
		
		
		List<Map<String, Object>> list = service.searchGoodscar(user_id);
		List<Map<String, Object>> newList=new ArrayList<Map<String,Object>>();
		newList=ChangeImagePathTool.chageImagePath(list, request, response);
		System.out.println("SearchGoodscarAction的ChangeImagePathTool得到的List集合："+newList);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goods", newList);
		if (newList.size() > 0) {
			
			map.put("state", 1);

		} else {
			map.put("state", 12);
		}
	
		// 生成并输出Json
		String jsonString = JsonTools.createJsonString("json", map);
		System.out.println("执行SearchGoodscarAction这个Servlet生成的jsonString="+jsonString);
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
		service = new GoodsCarDao();
	}

}
