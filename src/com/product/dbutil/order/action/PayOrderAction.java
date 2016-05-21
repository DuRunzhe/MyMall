package com.product.dbutil.order.action;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.product.dbutil.order.dao.OrderDao;
import com.product.dbutil.order.service.OrderService;
import com.product.dbutil.product.util.UUIDTools;
import com.product.jsonUtils.JsonTools;
import com.product.jsonUtils.ParseJsonTool;

public class PayOrderAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService;
	/**
	 * Constructor of the object.
	 */
	public PayOrderAction() {
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
		
		String transaction_price=request.getParameter("transaction_price");
		String user_id=request.getParameter("user_id");
		String receiver_name=request.getParameter("receiver_name");
		String receiver_phone=request.getParameter("receiver_phone");
		String receiver_address=request.getParameter("receiver_address");
		String receiver_postcode=request.getParameter("receiver_postcode");
		String order_id=request.getParameter("order_id");
		String sqlStringBuffer=request.getParameter("sqlStringBuffer");
		String pswd=request.getParameter("pswd");
		String goodsJson=request.getParameter("goodsJson");
		String orderFrom=request.getParameter("orderFrom");
		
		System.out.println("receiver_name="+receiver_name);
		System.out.println("receiver_address="+receiver_address);
		
		//goodsJson=new String(goodsJson.getBytes("iso8859-1"),"utf-8");
		//String sqlStringBuffer=new String(sqlStringBuffer1.getBytes("iso8859-1"),"utf-8");
		//String receiver_name=new String(receiver_name1.getBytes("iso8859-1"),"utf-8");
		//String receiver_address=new String(receiver_address1.getBytes("iso8859-1"),"GBK");
		System.out.println("sqlStringBuffer="+sqlStringBuffer);
		System.out.println("receiver_name="+receiver_name);
		System.out.println("receiver_address="+receiver_address);
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> params=new ArrayList<Object>();
		int state=0;
		
		
		//String goods_numString=request.getParameter("goods_numString");
		
		//验证密码
		String sqlCheckPswd="SELECT pswd FROM userinfo WHERE id=?";
		params.add(user_id);
		Map<String, Object> pswdMap=orderService.queryOneRecord(sqlCheckPswd, params);
		if(pswdMap==null||!pswd.equals(pswdMap.get("pswd").toString())){
			System.out.println("pswd="+pswd+"  pswdMap.get(pswd)="+pswdMap.get("pswd"));
			map.put("state", 1255);
		}else {
			//解析出订单商品的Json字符串
			
			List<Map<String, Object>> goodsList=ParseJsonTool.getGoodsListFromJson(goodsJson);
			System.out.println("---解析出订单商品的Json字符串集合是："+goodsList);
			String sqlQuery="";
			String sqlUpdata="";
			if(Integer.parseInt(orderFrom)==6052){//来自秒杀商品的
				sqlQuery="SELECT goods_id FROM spickgoods WHERE spick_num>=? AND goods_id=?";
				sqlUpdata="UPDATE spickgoods g SET g.spick_num=" +
						"(SELECT t.spick_num FROM(SELECT spick_num,goods_id FROM spickgoods)t " +
						"WHERE t.goods_id=?)-? WHERE g.goods_id=?";
			}else if(Integer.parseInt(orderFrom)==6051){
				sqlQuery="SELECT goods_id FROM goods WHERE goods_location>? AND goods_id=?";
				sqlUpdata="UPDATE goods g SET g.goods_location=" +
						"(SELECT t.goods_location FROM(SELECT goods_location,goods_id FROM goods)t " +
						"WHERE t.goods_id=?)-? WHERE g.goods_id=?";
			}
			for(int i=0;i<goodsList.size();i++){
				params=new ArrayList<Object>();
				int goods_location=(int) goodsList.get(i).get("goods_num");
				int goods_id=(int) goodsList.get(i).get("goods_id");
				params.add(goods_location);
				params.add(goods_id);
				Map<String, Object> goodMap=orderService.queryOneRecord(sqlQuery, params);
				if(goodMap.get("goods_id")!=null){
					System.out.println("--商品库存充足 goods_id="+goodMap.get("goods_id"));
				}else {//有商品库存不足
					System.out.println("--有商品库存不足");
					state=655;
					break;
				}
			}
			if(state==655){//有商品库存不足
				map.put("state", state);
			}else {//商品库存充足
				//修改商品库存
				boolean flag6=false;
				String sql6=sqlUpdata;
				for(int j=0;j<goodsList.size();j++){
					params=new ArrayList<Object>();
					int goods_id=(int) goodsList.get(j).get("goods_id");
					int goods_location=(int) goodsList.get(j).get("goods_num");
					params.add(goods_id);
					params.add(goods_location);
					params.add(goods_id);
					flag6=orderService.updataOneItem(sql6, params);
					if(!flag6){//修改商品库存失败
						break;
					}
					
				}
				//查询当前余额
				params=new ArrayList<Object>();
				String sql1="SELECT money FROM userinfo WHERE id=?";
				params.add(user_id);
				
				double current_money=(double) orderService.queryOneRecord(sql1, params).get("money");
				//从Userinfo表中money字段减去相应的钱数
				params=new ArrayList<Object>();
				params.add(current_money);
				params.add(transaction_price);
				params.add(user_id);
				String sql2="UPDATE userinfo SET money = ?-? WHERE id = ?";
				boolean flag2=orderService.updataOneItem(sql2, params);
				//创建订单表项
				params=new ArrayList<Object>();
				params.add(order_id);
				params.add(transaction_price);
				params.add(user_id);
				if(flag2){
					params.add("已支付");
				}
				else {
					params.add("未支付");
				}
				String logistics_id=UUIDTools.getUUID();//物流单号
				params.add(logistics_id);
				String sql3="INSERT INTO `order` (order_id,transaction_price,user_id,transaction_status,logistics_id) VALUES(?,?,?,?,?) ";
				boolean flag3=orderService.updataOneItem(sql3, params);
				//插入goodslist表项
				StringBuffer sql4=new StringBuffer();
				params=null;
				sql4.append("INSERT INTO goodslist (order_id,goods_id,goods_name,goods_num)");
				sql4.append(sqlStringBuffer);
				boolean flag4=orderService.updataOneItem(sql4.toString(), params);
				//插入logistics表项
				params=new ArrayList<Object>();
				params.add(logistics_id);
				params.add("正在出库...");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				params.add(df.format(new Date()));
				params.add(receiver_name);
				params.add(receiver_phone);
				params.add(receiver_address);
				params.add("xx手机商城");
				params.add(receiver_postcode);
				boolean flag5=false;
				if(flag3){//成功创建了order表项，才能插入物流数据
					String sql5="INSERT INTO logistics (logistics_id,logistics_info,updata_time,receiver_name,receiver_phone,receiver_address,sender_name,receiver_postcode) VALUES(?,?,?,?,?,?,?,?)";
					flag5=orderService.updataOneItem(sql5, params);
				}
				
				//修改商品库存
				//String sql6="select ";
				
				System.out.println("flag2="+flag2+"flag3="+flag3+"flag4="+flag4+"flag5="+flag5);
				//Map<String, Object> map = new HashMap<String, Object>();
				if (flag2&&flag3&&flag4&&flag5) {//支付成功
					
					map.put("state", 1208);
					double my_money=current_money-Double.parseDouble(transaction_price);
					map.put("mymoney", my_money);

				} else {//支付失败
					map.put("state", 1210);
				}
			}
			
			
		}
		
	
		// 生成并输出Json
		String jsonString = JsonTools.createJsonString("json", map);
		System.out.println("执行PayOrderAction这个Servlet生成的jsonString="+jsonString);
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
		orderService=new OrderDao();
	}

}
