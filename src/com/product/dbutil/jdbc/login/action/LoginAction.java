package com.product.dbutil.jdbc.login.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product.dbutil.jdbc.login.dao.LoginDao;
import com.product.dbutil.jdbc.login.service.LoginService;

public class LoginAction extends HttpServlet {
	private LoginService service;
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public LoginAction() {
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

		doPost(request, response);
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

		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		String path=request.getContextPath();
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String pswd = request.getParameter("pswd");
//		 out.println("---->>username="+username);
//		 out.println("---->>pswd="+pswd);
		System.out.println("---->>username="+username+"---->>pswd="+pswd);
		List<Object> params = new ArrayList<Object>();
		params.add(username);
		params.add(pswd);

		boolean flag = service.login(params);
		System.out.println("flag="+flag);
		if(flag){
			request.getSession().setAttribute("username",username);
			System.out.println("username="+request.getAttribute("username"));
			response.sendRedirect(path+"/main.jsp");
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
		service = new LoginDao();
		System.out.println("init()方法执行");
	}

}
