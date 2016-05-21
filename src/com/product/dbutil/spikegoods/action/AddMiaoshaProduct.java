package com.product.dbutil.spikegoods.action;

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
import com.product.dbutil.spikegoods.dao.SpikeGoodsDao;

public class AddMiaoshaProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService spikeService;
	/**
	 * Constructor of the object.
	 */
	public AddMiaoshaProduct() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("UTF-8");//
		PrintWriter out = response.getWriter();
		
		String goods_id=request.getParameter("goods_id");
		String ms_price=request.getParameter("ms_price");
		String ms_num=request.getParameter("ms_num");
		String time=request.getParameter("time");
		
//		Map<String, Object>spick_info=new HashMap<String, Object>();
//		spick_info.put("goods_id",goods_id);
//		spick_info.put("ms_price",ms_price);
//		spick_info.put("ms_num",ms_num);
//		spick_info.put("time",time);
		
		
		//先查看秒杀表中是否已经有了该商品
		List<Object> params=new ArrayList<Object>();
		String sql1="SELECT goods_id FROM spickgoods WHERE goods_id=?";
		params.add(goods_id);
		Map<String, Object> result1=spikeService.queryOneRecord(sql1, params);
		//查询该商品的原有信息
		params=new ArrayList<Object>();
		params.add(goods_id);
		String sql2="SELECT * FROM goods WHERE goods_id=?";
		Map<String, Object> goodMap=spikeService.queryOneRecord(sql2, params);
		//修改秒杀商品比表
		params=new ArrayList<Object>();
		String sql3="";
		if(result1.get("goods_id")!=null&&result1.get("goods_id")!=""){
			sql3="UPDATE spickgoods SET spick_price=?,goods_price=?,spick_num=?,spike_time=? WHERE goods_id=?";
			
		}else {
			sql3="INSERT INTO spickgoods (spick_price,goods_price,spick_num,spike_time,goods_id) VALUES(?,?,?,?,?)";
			
		}
		params.add(ms_price);
		params.add(Double.parseDouble(goodMap.get("new_price").toString()));
		params.add(ms_num);
		params.add(time);
		params.add(goods_id);
		boolean flag1=spikeService.updataOneItem(sql3, params);
		//修改原商品表
		params=new ArrayList<Object>();
		boolean flag4=false;
		if(Integer.parseInt(goodMap.get("goods_location").toString())>=Integer.parseInt(ms_num)){
			String sql4="UPDATE goods SET goods_location=? WHERE goods_id=?";
			params.add(Integer.parseInt(goodMap.get("goods_location").toString())-Integer.parseInt(ms_num));
			params.add(goods_id);
			flag4=spikeService.updataOneItem(sql4, params);
		}
		if(flag1&&flag4){
			out.println("添加成功！正在跳转");	
		}else {
			out.println("添加失败！");
		}
		try {
			//request.setAttribute("spick_info", spick_info);
			response.sendRedirect(path + "/servlet/ProductAction_forAndroid?action_flag=view&proid="+goods_id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		spikeService = new SpikeGoodsDao();
	}

}
