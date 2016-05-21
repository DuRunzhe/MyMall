package com.product.dbutil.order.action;

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

import com.product.dbutil.order.dao.OrderDao;
import com.product.dbutil.order.service.OrderService;
import com.product.jsonUtils.JsonTools;

public class SearchOrderInfoAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService;

	/**
	 * Constructor of the object.
	 */
	public SearchOrderInfoAction() {
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
		List<Object> params = new ArrayList<Object>();

		// 根据user_id在Order表中查询属于该用户的订单编号
		String id = request.getParameter("id");
		System.out.println("SearchOrderInfoAction得到的参数id=" + id);
		params.add(id);
		String sql1 = "SELECT order_id,logistics_id FROM `order` WHERE user_id=?";
		List<Map<String, Object>> orderIdList = new ArrayList<Map<String, Object>>();
		orderIdList = orderService.queryMultiRecord(sql1, params);

		// 根据得到的Order_id去logistics表中查询物流信息
		String sql2 = "SELECT logistics_id,updata_time,logistics_info FROM logistics WHERE logistics_id=?";
		Map<String, Object> logisticsMap = new HashMap<String, Object>();
		for (Map<String, Object> map : orderIdList) {
			params = new ArrayList<Object>();
			params.add(map.get("logistics_id"));
			List<Map<String, Object>> logisticsList = new ArrayList<Map<String, Object>>();
			logisticsList = orderService.queryMultiRecord(sql2, params);
			logisticsMap.put(map.get("order_id").toString(), logisticsList);
		}

		// 根据得到的Order_id去goodList表中查询商品清单和数量
		String sql3 = "SELECT goods_name,goods_num FROM goodslist WHERE order_id=?";
		Map<String, Object> goodsMap = new HashMap<String, Object>();
		for (Map<String, Object> map : orderIdList) {
			params = new ArrayList<Object>();
			params.add(map.get("order_id"));
			List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();
			goodsList = orderService.queryMultiRecord(sql3, params);
			goodsMap.put(map.get("order_id").toString(), goodsList);
		}

		// 生成并输出Json
		// List<Object> json = new ArrayList<Object>();
		// json.add(goodsMap);
		// json.add(logisticsMap);
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("orderIdList", orderIdList);
		json.put("goodslist", goodsMap);
		json.put("logisticlist", logisticsMap);
		
		String jsonString = JsonTools.createJsonString("json", json);
		System.out.println("执行PayOrderAction这个Servlet生成的jsonString="
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
		orderService = new OrderDao();
	}

}
