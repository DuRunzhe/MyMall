package com.product.dbutil.jdbc.login.action;

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

import com.jdbc.domain.JsonUser;
import com.product.dbutil.jdbc.login.dao.UserDao;
import com.product.dbutil.jdbc.login.service.UserService;
import com.product.jsonUtils.ChangeImagePathTool;
import com.product.jsonUtils.CharEncodingTool;
import com.product.jsonUtils.JsonTools;

public class UpdataUserAction extends HttpServlet {
private UserService userService;
private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the object.
	 */
	public UpdataUserAction() {
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

		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String sql=request.getParameter("sql");
		int user_id=Integer.parseInt(request.getParameter("id"));
		List<Object> params = new ArrayList<Object>();
		params.add(user_id);
		
		Map<String, Object> map=new HashMap<String, Object>();
		map=userService.updataUser(sql, params);
		
		if(map==null){
			System.out.println("UpdataUserAction操作数据库失败！");
			return;
		}
		map.put("user_id", user_id);
		//转换头像图片路径
		Map<String, Object> newMap=new HashMap<String, Object>();
		newMap=ChangeImagePathTool.changeUserImagePath(map, request, response);
		System.out.println("UpdataUserAction中ChangeImagePathTool得到的map集合："+newMap);
		
		JsonUser jsonUser=new JsonUser();
		//从查询结果中生成JsonUser对象
		jsonUser.setId((int)newMap.get("user_id"));
		jsonUser.setUsername((String) newMap.get("username"));
		jsonUser.setMoney((Double) newMap.get("money"));
		jsonUser.setState((int) newMap.get("state"));
		jsonUser.setProfile_image((String) newMap.get("profile_image"));
		
		String jsonString=JsonTools.createJsonString("json", jsonUser);
		System.out.println("UpdataUserAction得到的Json字符串："+jsonString);
		out.println(jsonString);
		out.flush();
		out.close();
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		userService=new UserDao();
	}

}
