package com.product.dbutil.jdbc.login.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdbc.domain.JsonUser;
import com.product.dbutil.jdbc.login.dao.LoginDao;
import com.product.dbutil.jdbc.login.service.LoginService;
import com.product.jsonUtils.ChangeImagePathTool;
import com.product.jsonUtils.JsonTools;

public class LoginJsonAction extends HttpServlet {
	private LoginService service;
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the object.
	 */
	public LoginJsonAction() {
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

		
		//产生JSON数据
		//response.setContentType("text/html");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String pswd = request.getParameter("pswd");
//		 out.println("---->>username="+username);
//		 out.println("---->>pswd="+pswd);
		System.out.println("登录的Servlet得到安卓端提交的数据是：username="+username);
		//转码
		//username=new String(username.getBytes("utf-8"), "utf-8");//对于写到数据库的乱码没用,这里是查询数据库,对于解决web端输出到控制台乱码没用
		//username=new String(username.getBytes("iso-8859-1"),"utf-8");//对于解决web端输出到控制台乱码没用UTF-8
		//username=new String(username.getBytes(),"UTF-8");//对于解决web端输出到控制台乱码没用UTF-8
		//username=new String(username.getBytes("GBK"),"UTF-8");//对于解决web端输出到控制台乱码没用UTF-8
		//username=new String(username.getBytes("utf-8"));
		//System.out.println("---->>登录的servlet对提交的数据转码的结果是username="+username+"---->>pswd="+pswd);
		List<Object> params = new ArrayList<Object>();
		params.add(username);
		params.add(pswd);
		JsonUser jsonUser=service.login_getJsonUser(params);
		String imagePath=jsonUser.getProfile_image();
		String user_id=jsonUser.getId()+"";
		imagePath=ChangeImagePathTool.changeUserImagePath(imagePath, user_id, request, response);
		System.out.println("LoginJsonAction得到的转换后的图片url="+imagePath);
		jsonUser.setProfile_image(imagePath);
		
		//String jsonString=JsonTools.createJsonString("json", JsonService.getJsonUser());
		String jsonString=JsonTools.createJsonString("json", jsonUser);
		
		//jsonString=new String(jsonString.getBytes("utf-8"),"utf-8");//对于解决web端输入到控制台乱码没用
		System.out.println("登录servlet输出的Json数据："+jsonString);
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
		service = new LoginDao();
		System.out.println("init()方法执行");
	}

}
