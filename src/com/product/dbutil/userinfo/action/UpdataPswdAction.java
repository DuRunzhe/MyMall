package com.product.dbutil.userinfo.action;

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

import com.product.dbutil.order.service.OrderService;
import com.product.dbutil.userinfo.dao.UpPswdDao;
import com.product.jsonUtils.JsonTools;

public class UpdataPswdAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService upPswdService;

	/**
	 * Constructor of the object.
	 */
	public UpdataPswdAction() {
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
		String old_pswd = request.getParameter("old_pswd");
		String new_pswd = request.getParameter("new_pswd");

		List<Object> params = new ArrayList<Object>();
		String sqlQuery = "SELECT pswd FROM userinfo WHERE id=?";
		params.add(user_id);
		Map<String, Object> map = new HashMap<String, Object>();
		map = upPswdService.queryOneRecord(sqlQuery, params);
		int state = 10;
		if (old_pswd.equals(map.get("pswd").toString())) {
			String sql = "UPDATE userinfo SET pswd=? where id=?";
			params = new ArrayList<Object>();
			params.add(new_pswd);
			params.add(user_id);
			boolean flag = upPswdService.updataOneItem(sql, params);
			if (flag) {
				state = 888;// 修改密码成功
			} else {
				state = 111;// 数据库操作失败
			}
		} else {
			state = 555;// 原密码错误
		}
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("state", state);
		// 生成并输出Json
		String jsonString = JsonTools.createJsonString("json", json);
		System.out.println("执行UpdataPswdAction这个Servlet生成的jsonString="
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
		upPswdService = new UpPswdDao();
	}

}
