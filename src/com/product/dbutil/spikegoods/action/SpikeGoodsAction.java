package com.product.dbutil.spikegoods.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product.dbutil.order.service.OrderService;
import com.product.dbutil.spikegoods.dao.SpikeGoodsDao;
import com.product.jsonUtils.ChangeImagePathTool;
import com.product.jsonUtils.JsonTools;

public class SpikeGoodsAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService spikeService;

	/**
	 * Constructor of the object.
	 */
	public SpikeGoodsAction() {
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

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("UTF-8");//
		PrintWriter out = response.getWriter();

		List<Object> params = new ArrayList<Object>();
		//

		 String sql1 = "SELECT * FROM spickgoods WHERE spick_num>0 ORDER BY spike_time DESC";
		//String sql1 = "SELECT * FROM spickgoods ORDER BY spike_time ";
		//String sql1 = "SELECT * FROM spickgoods";
		List<Map<String, Object>> goodsList = spikeService.queryMultiRecord(sql1, null);
		int state = 111;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (goodsList.size() >= 1) {
			//
			String sql2 = "SELECT * FROM goods WHERE goods_id=?";

			for (int i = 0; i < goodsList.size(); i++) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					long timeEnd = sdf.parse(goodsList.get(i).get("spike_time").toString()).getTime();
					long timeStart = sdf.parse(sdf.format(new Date())).getTime();//
					if (timeStart < timeEnd) {
						Map<String, Object> goodsMap = new HashMap<String, Object>();
						goodsMap.put("goods_id", goodsList.get(i).get("goods_id"));
						goodsMap.put("spick_price", goodsList.get(i).get("spick_price"));
						goodsMap.put("goods_price", goodsList.get(i).get("goods_price"));//
						goodsMap.put("spick_num", goodsList.get(i).get("spick_num"));
						goodsMap.put("spike_time", goodsList.get(i).get("spike_time").toString());
						System.out.println("time=" + goodsList.get(i).get("spike_time").toString());

						params = new ArrayList<Object>();
						params.add(goodsList.get(i).get("goods_id") + "");
						Map<String, Object> goods = spikeService.queryOneRecord(sql2, params);
						if (goods != null) {
							state = 888;
							goodsMap.put("goods_name", goods.get("goods_name"));
							String imagePath = ChangeImagePathTool.changeProductImagePath(goods.get("goods_image") + "", request, response);
							goodsMap.put("goods_image", imagePath);
							goodsMap.put("old_price", goods.get("old_price"));
							goodsMap.put("praise_scale", goods.get("praise_scale"));
							goodsMap.put("scales_volume", goods.get("scales_volume"));
							goodsMap.put("create_time", goods.get("create_time"));
							goodsMap.put("goods_promotion", goods.get("goods_promotion"));
							goodsMap.put("goods_location", goods.get("goods_location"));
							goodsMap.put("category", goods.get("category"));
							goodsMap.put("sub_category", goods.get("sub_category"));
							goodsMap.put("s_sub_category", goods.get("s_sub_category"));
						} else {// 商品表中不存在该商品
							state = 120;
						}
						result.add(goodsMap);
					} else {
						System.out.println(" ----i="+i);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} else {// 秒杀表中没有秒杀杀品
			state = 100;
		}
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("state", state);
		json.put("result", result);

		// 生成并输出Json
		String jsonString = JsonTools.createJsonString("json", json);
		System.out.println("执行SpikeGoodsAction这个Servlet生成的jsonString=" + jsonString);
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
		spikeService = new SpikeGoodsDao();
	}

}
