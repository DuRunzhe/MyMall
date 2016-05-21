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

import net.sf.json.util.JSONUtils;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.product.dbutil.goodscar.dao.GoodsCarDao;
import com.product.dbutil.goodscar.service.GoodsCarService;
import com.product.jsonUtils.JsonTools;
import com.product.jsonUtils.ParseJsonTool;

public class DeleteInGoodscarAction extends HttpServlet {
	private GoodsCarService service;
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public DeleteInGoodscarAction() {
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
		//System.out.println("--------getParameterMap"+request.getParameterMap().get("goods_id"));
		//System.out.println("--------getAttribute"+request.getAttribute("goods_id"));
		String user_id = request.getParameter("user_id");
		String goods_ids = request.getParameter("goods_ids");
		System.out.println("----------预删除的user_id=" + user_id);
		System.out.println("----------预删除的goods_id=" + goods_ids);
		//解析Json
		
		//删除购物车指定商品
		List<Object> list=ParseJsonTool.getListFromJson(goods_ids);
		System.out.println("DeleteInGoodscarAction 的Json字符串解析出的List集合为："+list.toString());
		list.add(user_id);
		StringBuffer sql=new StringBuffer("DELETE FROM goodscar where goods_id in(");
		for(int i=0;i<list.size()-1;i++){
			sql.append("?");
			if(i<list.size()-2){
				sql.append(",");
			}
		}
		sql.append(") AND user_id=?");
		System.out.println("DeleteInGoodscarAction生成的sql"+sql.toString());
		boolean flag = service.deleteInGoodscar(sql.toString(), list);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (flag) {
			map.put("state", 001);
		} else {
			map.put("state", 010);
		}
		// servlet/InsertGoodsCartAction
		// 生成并输出Json
		String jsonString = JsonTools.createJsonString("json", map);
		System.out.println("执行DeleteInGoodscarAction生成的jsonString="
				+ jsonString);
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
