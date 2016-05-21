package com.product.dbutil.product.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.tools.framedump;

import javax.enterprise.inject.New;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.functors.OnePredicate;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.product.dbutil.product.dao.ProductDao;
import com.product.dbutil.product.dao.ProductDao_forAndroid;
import com.product.dbutil.product.service.ProductService;
import com.product.dbutil.product.util.DividePage;
import com.product.dbutil.product.util.UUIDTools;
import com.product.dbutil.spikegoods.dao.SpikeGoodsDao;
import com.product.jsonUtils.ChangeImagePathTool;
import com.product.jsonUtils.CountdownUtil;
import com.product.jsonUtils.JsonTools;
import com.sun.faces.facelets.util.Path;

public class ProductAction_forAndroid extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductService service;
	private CountdownUtil countdownUtil;

	/**
	 * Constructor of the object.
	 */
	public ProductAction_forAndroid() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// countdownUtil=new CountdownUtil(0, 6, 0, 0);
		// countdownUtil.jishi();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String action_flag = request.getParameter("action_flag");
		if (action_flag.equals("add")) {
			addProduct(request, response);
		} else if (action_flag.equals("list")) {
			listProduct(request, response);
		} else if (action_flag.equals("del")) {
			delProduct(request, response);
		} else if (action_flag.equals("view")) {
			viewProduct(request, response);
		} else if (action_flag.equals("getcategory")) {
			getCategory(request, response);
		} else if (action_flag.equals("forwardmodify")) {
			forwardmodify(request, response);
		} else if (action_flag.equals("modifyproduct")) {
			modifyproduct(request, response);
		}

		out.flush();
		out.close();
	}

	/**
	 * 带数据跳转到修改指定商品数据界面
	 * 
	 * @param request
	 * @param response
	 */
	private void forwardmodify(HttpServletRequest request, HttpServletResponse response) {
		List<Object> params = new ArrayList<Object>();
		// String sql1 = "SELECT DISTINCT category FROM category";
		// params = null;
		// List<Map<String, Object>> categoryList =
		// service.getProductCategory(sql1, params);// 主分类的集合
		// //
		// String sql2 = "SELECT DISTINCT sub_category FROM sub_category";
		// List<Map<String, Object>> sub_categoryList =
		// service.getProductCategory(sql2, null);
		// //
		// String sql3 = "SELECT DISTINCT s_sub_category FROM sub_category";
		// List<Map<String, Object>> s_sub_categoryList =
		// service.getProductCategory(sql3, null);
		//
		// request.setAttribute("categoryList", categoryList);
		// request.setAttribute("sub_categoryList", sub_categoryList);
		// request.setAttribute("s_sub_categoryList", s_sub_categoryList);
		//
		String proid = request.getParameter("proid");
		params = new ArrayList<Object>();
		params.add(proid);
		String sql = "SELECT * FROM goods WHERE goods_id=?";
		List<Map<String, Object>> result = service.findMoreResult(sql, params);

		request.setAttribute("result", result);
		getSelectData(request, response);
		try {
			request.getRequestDispatcher("/product/2_1_5xiugai.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 修改指定商品数据
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	private void modifyproduct(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getContextPath();
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		servletFileUpload.setSizeMax(24 * 1024 * 1024);// 上传文件的总大小
		servletFileUpload.setFileSizeMax(8 * 1024 * 1024);// 上传文件的单个大小
		List<FileItem> list = null;
		List<Object> params = new ArrayList<Object>();
		StringBuffer sqlBuffer = new StringBuffer("UPDATE goods SET ");
		try {
			// 解析request的请求
			list = servletFileUpload.parseRequest(request);
			// 取出所有表单的值：判断非文本字段和文本字段
			for (FileItem fileitem : list) {
				if (fileitem.isFormField()) {
					if (fileitem.getFieldName().equals("proname")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
						sqlBuffer.append("goods_name=?,");
					}
					if (fileitem.getFieldName().equals("new_price")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(Double.parseDouble(fileitem.getString("utf-8")));
						sqlBuffer.append("new_price=?,");
					}
					if (fileitem.getFieldName().equals("old_price")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
						sqlBuffer.append("old_price=?,");
					}
					if (fileitem.getFieldName().equals("goods_location")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
						sqlBuffer.append("goods_location=?,");
					}
					if (fileitem.getFieldName().equals("praise_scale")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
						sqlBuffer.append("praise_scale=?,");
					}
					if (fileitem.getFieldName().equals("scales_volume")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
						sqlBuffer.append("scales_volume=?,");
					}
					if (fileitem.getFieldName().equals("goods_promotion")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
						sqlBuffer.append("goods_promotion=?,");
					}
					if (fileitem.getFieldName().equals("category")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
						sqlBuffer.append("category=?,");
					}
					if (fileitem.getFieldName().equals("sub_category")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
						sqlBuffer.append("sub_category=?,");
					}
					if (fileitem.getFieldName().equals("s_sub_category")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
						sqlBuffer.append("s_sub_category=?,");
					}
				} else {
					try {
						String image = fileitem.getName();
						if (!image.equals("") && image != null) {
							params.add(image);
							sqlBuffer.append("goods_image=?,");
							String upload_path = request.getRealPath("/upload");
							System.out.println("----->>" + upload_path);
							File real_path = new File(upload_path + "/" + image);
							fileitem.write(real_path);
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			params.add(df.format(new Date()));
			sqlBuffer.append("create_time=? ");
			String goods_id = request.getParameter("goods_id");
			params.add(Integer.parseInt(goods_id));
			sqlBuffer.append(" WHERE goods_id=? ");
			String sql = sqlBuffer.toString();
			boolean flag = service.modifyProduct(sql, params);
			if (flag) {// 成功添加产品以后
				// 成功添加产品以后，不应该直接转到目标Jsp页面，而应该交给Servlet处理
				// response.sendRedirect(path +
				// "/product/2_1_5.jsp");
				response.sendRedirect(path + "/servlet/ProductAction_forAndroid?action_flag=list");
			}
			// 把数据插入到数据库中

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 带数据跳转到商品添加页面
	 * 
	 * @param request
	 * @param response
	 */
	private void getCategory(HttpServletRequest request, HttpServletResponse response) {
		getSelectData(request, response);

		// String sql1 = "SELECT DISTINCT category FROM category";
		// List<Object> params = new ArrayList<Object>();
		// params = null;
		// List<Map<String, Object>> categoryList =
		// service.getProductCategory(sql1, params);// 主分类的集合
		// //
		// String sql2 = "SELECT DISTINCT sub_category FROM sub_category";
		// List<Map<String, Object>> sub_categoryList =
		// service.getProductCategory(sql2, null);
		// //
		// String sql3 = "SELECT DISTINCT s_sub_category FROM sub_category";
		// List<Map<String, Object>> s_sub_categoryList =
		// service.getProductCategory(sql3, null);
		//
		// request.setAttribute("categoryList", categoryList);
		// request.setAttribute("sub_categoryList", sub_categoryList);
		// request.setAttribute("s_sub_categoryList", s_sub_categoryList);
		try {
			request.getRequestDispatcher("/product/2_1_5tj.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 得到下拉选择框的数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @return
	 */
	private void getSelectData(HttpServletRequest request, HttpServletResponse response) {
		String sql1 = "SELECT DISTINCT category FROM category";
		List<Object> params = new ArrayList<Object>();
		params = null;
		List<Map<String, Object>> categoryList = service.findMoreResult(sql1, params);// 主分类的集合
		System.out.println("----------查询数据库拿到的一级菜单" + categoryList);
		//
		String sql2 = "SELECT * FROM category";
		List<Map<String, Object>> sub_categoryList = service.findMoreResult(sql2, null);
		System.out.println("----------查询数据库拿到的二级菜单" + sub_categoryList);
		//
		String sql3 = "SELECT * FROM sub_category";
		List<Map<String, Object>> s_sub_categoryList = service.findMoreResult(sql3, null);
		System.out.println("----------查询数据库拿到的三级菜单" + s_sub_categoryList);

		List<Object> oneList = new ArrayList<Object>();// 一级菜单
		for (Map<String, Object> map : categoryList) {
			oneList.add(map.get("category") + "");
		}
		Map<String, Object> twoMap = new HashMap<String, Object>();// 二级菜单
		Map<String, Object> threeMap = new HashMap<String, Object>();// 三级菜单
		for (Map<String, Object> map : categoryList) {
			String category = map.get("category") + "";
			List<Object> sub_categoryArray = new ArrayList<Object>();
			for (Map<String, Object> map2 : sub_categoryList) {
				if (category.equals(map2.get("category").toString())) {
					sub_categoryArray.add(map2.get("sub_category") + "");
				}
			}
			twoMap.put(category, sub_categoryArray);
			//
			for (Object sub_categoryStr : sub_categoryArray) {
				String sub_category = (String) sub_categoryStr;
				List<Object> s_sub_categoryArray = new ArrayList<Object>();
				for (Map<String, Object> map2 : s_sub_categoryList) {
					if (sub_category.equals(map2.get("sub_category").toString())) {
						s_sub_categoryArray.add(map2.get("s_sub_category"));
					}
				}
				threeMap.put(sub_category, s_sub_categoryArray);
			}

		}
		System.out.println("+++++++++++++二级菜单map"+twoMap);
		System.out.println("++++++++++三级级菜单map"+threeMap);
		String oneListJson = JsonTools.createJsonString("json", oneList);
		String twoMapJson = JsonTools.createJsonString("json", twoMap);
		String threeMapJson = JsonTools.createJsonString("json", threeMap);
		System.out.println("执行getSelectData（）方法生成的oneListJson=" + oneListJson);
		System.out.println("执行getSelectData（）方法生成的twoMapJson=" + twoMapJson);
		System.out.println("执行getSelectData（）方法生成的threeMapJson=" + threeMapJson);

		request.setAttribute("oneListJson", oneListJson);
		request.setAttribute("twoMapJson", twoMapJson);
		request.setAttribute("threeMapJson", threeMapJson);
		// return request;

	}

	/**
	 * 查看功能
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void viewProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String proid = request.getParameter("proid");
		Map<String, Object> map = service.viewProduct(proid);
		System.out.println("viewProduct得到的Map集合：" + map);
		request.setAttribute("map", map);
		//
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM spickgoods WHERE goods_id=?";
		params.add(proid);
		Map<String, Object> spick_info = new SpikeGoodsDao().queryOneRecord(sql, params);
		System.out.println("viewProduct（）方法spick_info=" + spick_info);
		if (spick_info.get("spick_price") == null) {
			spick_info.put("spick_price", "请输入价格");
		}
		if (spick_info.get("spick_num") == null) {
			spick_info.put("spick_num", "请输入数量");
		}
		if (spick_info.get("spike_time") == null) {
			spick_info.put("spike_time", "请选择时间");
		}
		request.setAttribute("spick_info", spick_info);
		request.getRequestDispatcher("/product/2_1_5xs.jsp").forward(request, response);
	}

	/**
	 * 批量删除操作
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		// 获取用户选中的复选框
		String[] ids = request.getParameterValues("ids");
		boolean flag = service.delProduct(ids);
		if (flag) {
			response.sendRedirect(path + "/servlet/ProductAction_forAndroid?action_flag=list");
		}

	}

	private void listProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// String path=request.getContextPath();
		String proname = request.getParameter("proname");// 接收用户查询关键字
		System.out.println("用户查询关键字:" + proname);// 调试信息
		int recordCount = service.getItemCount();// 获得记录的总条数
		System.out.println("recordCount=" + recordCount);// 调试信息
		int currentPage = 1;// 当前页是第一页
		String pageNum = request.getParameter("pageNum");
		System.out.println("pageNum:" + pageNum);// 调试信息

		// 分页代码
		if (pageNum != null) {
			currentPage = Integer.parseInt(pageNum);
		}
		DividePage pUtil = new DividePage(5, recordCount, currentPage);
		int start = pUtil.getFromIndex();
		int end = pUtil.getToIndex();
		System.out.println("CurrentPage==" + pUtil.getCurrentPage());// 调试信息

		// 已经进行分页的数据集合
		List<Map<String, Object>> list = service.listProduct(proname, start, end);
		System.out.println("原本得到的List集合：" + list);
		System.out.println("ProductAction_forAndroid的ChangeImagePathTool得到的List集合：" + ChangeImagePathTool.chageImagePath(list, request, response));
		// System.out.println("list="+list);//调试信息
		// 分页代码
		request.setAttribute("pUtil", pUtil);
		// ///
		request.setAttribute("proname", proname);
		request.setAttribute("listproduct", list);

		request.getRequestDispatcher("/product/2_1_5.jsp").forward(request, response);
		// request.getRequestDispatcher(path+"/servlet/ProductAction?action_flag=list").forward(request,
		// response);

	}

	
	private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 表单含有文件要提交
		String path = request.getContextPath();
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		servletFileUpload.setSizeMax(24 * 1024 * 1024);// 上传文件的总大小
		servletFileUpload.setFileSizeMax(8 * 1024 * 1024);// 上传文件的单个大小
		List<FileItem> list = null;
		List<Object> params = new ArrayList<Object>();
		String uuID = UUIDTools.getUUID();
		params.add(uuID);// 第一个参数，用于填充goods_id
		System.out.println("uuID=" + uuID);
		try {
			// 解析request的请求
			list = servletFileUpload.parseRequest(request);
			// 取出所有表单的值：判断非文本字段和文本字段
			for (FileItem fileitem : list) {
				if (fileitem.isFormField()) {//判断非文本字段和文本字段
					if (fileitem.getFieldName().equals("proname")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
					}
					if (fileitem.getFieldName().equals("new_price")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
					}
					if (fileitem.getFieldName().equals("old_price")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
					}
					if (fileitem.getFieldName().equals("goods_location")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
					}
					if (fileitem.getFieldName().equals("praise_scale")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
					}
					if (fileitem.getFieldName().equals("scales_volume")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
					}
					if (fileitem.getFieldName().equals("goods_promotion")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
					}
					if (fileitem.getFieldName().equals("category")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
					}
					if (fileitem.getFieldName().equals("sub_category")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
					}
					if (fileitem.getFieldName().equals("s_sub_category")) {
						System.out.println(fileitem.getString("utf-8"));
						params.add(fileitem.getString("utf-8"));
					}
				} else {
					try {
						String image = fileitem.getName();
						params.add(image);
						String upload_path = request.getRealPath("/upload");
						System.out.println("----->>" + upload_path);
						File real_path = new File(upload_path + "/" + image);
						fileitem.write(real_path);
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
						params.add(df.format(new Date()));
						boolean flag = service.addProduct(params);
						if (flag) {// 成功添加产品以后
							// 成功添加产品以后，不应该直接转到目标Jsp页面，而应该交给Servlet处理
							// response.sendRedirect(path +
							// "/product/2_1_5.jsp");
							response.sendRedirect(path + "/servlet/ProductAction_forAndroid?action_flag=list");
						}
						// 把数据插入到数据库中
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		service = new ProductDao_forAndroid();// 接口实例化为实现类
	}

}
