package com.product.dbutil.jdbc.register.action;

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

import com.product.dbutil.jdbc.register.dao.RegisterDao;
import com.product.dbutil.jdbc.register.service.RegisterService;
import com.product.jsonUtils.JsonTools;

/**
 * 用于手机端执行注册的Servlet类
 * 
 * @author drzwin32
 * 
 */
public class RegisterJsonAction extends HttpServlet {
	private RegisterService service;
	//private LoginService loginService;

	/**
	 * Constructor of the object.
	 */
	public RegisterJsonAction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");// 所有流向服务器的数据都交给了request对象，这里设置request对象使用的编码格式，不设置将导致提交到服务器的数据乱码
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String realname = request.getParameter("realname");
		String pswd = request.getParameter("pswd");
		String email = "";// By drz
		email = request.getParameter("email");// By drz
		System.out.println("注册的Servlet得到安卓端提交的数据是：username="+username);
		//转码
		//username=new String(username.getBytes("utf-8"), "utf-8");//可以解决写到数据库乱码问题
		//username=new String(username.getBytes(), "utf-8");//不行
		//username=new String(username.getBytes("iso-8859-1"), "utf-8");
		// out.println("---->>username="+username);
		// out.println("---->>realname="+realname);
		// out.println("---->>pswd="+pswd);
		// 填充sql语句占位符的内容（）RegisterDao类的registerUser（）方法
		//System.out.println("注册的Servlet对提交的数据转码的结果是：username="+username);
		Map<String, Integer> map=null;
		List<Object> params = new ArrayList<>();
		params.add(username);// 把从request对象提取的参数交给存储sql参数的list集合，顺序参照registerUser（）方法的sql语句
		if (service.isIDin(params)) {//注册的ID已经存在
			map = new HashMap<>();
			map.put("state", 2);
			String jsonString = JsonTools.createJsonString("json", map);
			out.println(jsonString);
			out.flush();
			out.close();
			return;
		}
		params.add(pswd);
		params.add(realname);
		params.add(email);// By drz
		// 执行操作数据库的方法
		
		boolean flag = service.register_android(params);
		System.out.println("flag=" + flag);
		if (flag) {// 操作数据库成功
			// response.sendRedirect(path + "/index.jsp");
			map = new HashMap<>();
			map.put("state", 1);
			String jsonString = JsonTools.createJsonString("json", map);
			out.println(jsonString);
		}else {
			System.out.println("操作数据库失败");
		}

		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		service = new RegisterDao();
		//loginService=new LoginDao();
	}

}
