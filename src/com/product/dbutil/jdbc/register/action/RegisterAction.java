package com.product.dbutil.jdbc.register.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product.dbutil.jdbc.register.dao.RegisterDao;
import com.product.dbutil.jdbc.register.service.RegisterService;

public class RegisterAction extends HttpServlet {
	private RegisterService service;

	/**
	 * Constructor of the object.
	 */
	public RegisterAction() {
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

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");//所有流向服务器的数据都交给了request对象，这里设置request对象使用的编码格式，不设置将导致提交到服务器的数据乱码
		String path = request.getContextPath();
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String realname = request.getParameter("realname");
		String pswd = request.getParameter("pswd");
		String email="";//By drz
		email=request.getParameter("email");//By drz
		// out.println("---->>username="+username);
		// out.println("---->>realname="+realname);
		// out.println("---->>pswd="+pswd);
		// 填充sql语句占位符的内容（）RegisterDao类的registerUser（）方法
		List<Object> params = new ArrayList<>();
		params.add(username);// 把从request对象提取的参数交给存储sql参数的list集合，顺序参照registerUser（）方法的sql语句
		params.add(pswd);
		params.add(realname);
		//params.add(email);//By drz
		// 执行操作数据库的方法
		boolean flag = service.registerUser(params);
		System.out.println("flag=" + flag);
		if (flag) {// 操作数据库成功，跳转页面
			response.sendRedirect(path + "/index.jsp");
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
	}

}
