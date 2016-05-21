package com.product.dbutil.goodevalution.action;

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

import com.product.dbutil.goodevalution.dao.GoodEvalutionDao;
import com.product.dbutil.order.service.OrderService;
import com.product.jsonUtils.ChangeImagePathTool;
import com.product.jsonUtils.JsonTools;

public class GetGoodEvaluationAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService goodEvalutionService;

	/**
	 * Constructor of the object.
	 */
	public GetGoodEvaluationAction() {
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
		String good_id = request.getParameter("good_id");
		//String user_id = request.getParameter("user_id");
		params.add(good_id);
		String sql = "SELECT user_name,user_image,evalution_text,evalution_time,user_id  FROM goodevalution WHERE good_id=? order by  evalution_time";
		List<Map<String, Object>> list = goodEvalutionService.queryMultiRecord(
				sql, params);
//		//查询用户头像图片
//		String sqlImage = "SELECT profile_image FROM userinfo WHERE id=?";
//		params = new ArrayList<Object>();
//		params.add(user_id);
//		Map<String, Object> imageMap = new HashMap<String, Object>();
//		imageMap = goodEvalutionService.queryOneRecord(sqlImage, params);
//		imageMap.put("user_id", user_id);
//		imageMap = ChangeImagePathTool.changeUserImagePath("profile_image",
//				imageMap, request, response);
		// 转换头像图片路径
		list=ChangeImagePathTool.changeUserImagePath(list, request,
		 response);
		
//		Map<String, Object> map2=new HashMap<String, Object>();
//		map2.put("user_image", imageMap.get("profile_image"));
//		list.add(map2);

		Map<String, Object> json = new HashMap<String, Object>();

		if (list.size() > 0) {
			json.put("state", 888);

		} else {
			json.put("state", 111);
		}
		json.put("goodevalution", list);
		String jsonString = JsonTools.createJsonString("json", json);
		System.out.println("执行GetGoodEvaluationAction这个Servlet生成的jsonString="
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
		goodEvalutionService = new GoodEvalutionDao();
	}

}
