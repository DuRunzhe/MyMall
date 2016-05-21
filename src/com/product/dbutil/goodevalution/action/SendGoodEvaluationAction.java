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
import com.product.jsonUtils.CharEncodingTool;
import com.product.jsonUtils.JsonTools;

public class SendGoodEvaluationAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService goodEvalutionService;

	/**
	 * Constructor of the object.
	 */
	public SendGoodEvaluationAction() {
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

		String user_id = request.getParameter("user_id");
		String evalution_id = request.getParameter("evalution_id");
		String evalution_time = request.getParameter("evalution_time");
		String user_image = request.getParameter("user_image");
		String good_id = request.getParameter("good_id");
		String evalution_text = request.getParameter("evalution_text");
		String user_name = request.getParameter("user_name");
		
		//user_image=new String(user_image.getBytes(), "utf-8");
		user_name=CharEncodingTool.changeEncode(user_name,"iso-8859-1", "UTF-8");
		evalution_text=new String(evalution_text.getBytes("iso-8859-1"), "utf-8");
		System.out.println("SendGoodEvaluationAction接受的字符为："+"evalution_text="+evalution_text);
		
		//查询某商品的当前评论数
		String sqlNum="SELECT COUNT(*) FROM goodevalution WHERE good_id=?";
		params=new ArrayList<Object>();
		params.add(good_id);
		Map<String, Object> goodNumMap=goodEvalutionService.queryOneRecord(sqlNum, params);
		int scales_volume=1;
		if(goodNumMap.get("COUNT(*)")!=null){
			scales_volume=Integer.parseInt(goodNumMap.get("COUNT(*)").toString());
			scales_volume+=1;
			//System.out.println("---scales_volume="+scales_volume+"goodNumMap"+goodNumMap);
		}
		String sqlUpGoodEvaNum="UPDATE goods SET scales_volume=? WHERE goods_id=?";
		params=new ArrayList<Object>();
		params.add(scales_volume);
		params.add(good_id);
		boolean flagNum=goodEvalutionService.updataOneItem(sqlUpGoodEvaNum, params);
		if(flagNum){
			System.out.println("--更新评论数目成功！"+scales_volume+"goodNumMap"+goodNumMap);
		}else {
			System.out.println("--更新评论数目失败！");
		}
		//查询用户头像图片(注意这里并没有在用户更换图片以后更新该表中的头像Url)
		String sqlImage = "SELECT profile_image FROM userinfo WHERE id=?";
		params = new ArrayList<Object>();
		params.add(user_id);
		Map<String, Object> imageMap = new HashMap<String, Object>();
		imageMap = goodEvalutionService.queryOneRecord(sqlImage, params);
		user_image=imageMap.get("profile_image").toString();
		
		//params.add(user_id);
		params.add(user_image);
		params.add(evalution_id);
		params.add(evalution_time);
		params.add(evalution_text);
		params.add(good_id);
		params.add(user_name);

		String sql = "INSERT INTO goodevalution (user_id,user_image,evalution_id,evalution_time,evalution_text,good_id,user_name) VALUES(?,?,?,?,?,?,?)";
		boolean flag=goodEvalutionService.updataOneItem(sql, params);
		Map<String, Object> json=new HashMap<String, Object>();
		
		if(flag){
			json.put("state", 888);
			
		}else {
			json.put("state", 111);
		}
		String jsonString = JsonTools.createJsonString("json", json);
		System.out.println("执行SendGoodEvaluationAction这个Servlet生成的jsonString="
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
