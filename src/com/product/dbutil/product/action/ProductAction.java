package com.product.dbutil.product.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.product.dbutil.product.dao.ProductDao;
import com.product.dbutil.product.service.ProductService;
import com.product.dbutil.product.util.DividePage;
import com.product.dbutil.product.util.UUIDTools;
import com.sun.faces.facelets.util.Path;

public class ProductAction extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductService service;

	/**
	 * Constructor of the object.
	 */
	public ProductAction() {
		super();
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String action_flag = request.getParameter("action_flag");
		if (action_flag.equals("add")) {
			addProduct(request, response);
		}else if (action_flag.equals("list")) {
			listProduct(request, response);
		}else if(action_flag.equals("del")){
			delProduct(request,response);
		}else if(action_flag.equals("view")){
			viewProduct(request,response);
		}
		

		out.flush();
		out.close();
	}

	/**
	 * 查看功能
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void viewProduct(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		String proid=request.getParameter("proid");
		Map<String, Object> map=service.viewProduct(proid);
		request.setAttribute("map", map);
		request.getRequestDispatcher("/product/2_1_5xs.jsp").forward(request, response);
		
	}

	/**
	 * 批量删除操作
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String path=request.getContextPath();
		//获取用户选中的复选框
		String[] ids=request.getParameterValues("ids");
		boolean flag=service.delProduct(ids);
		if(flag){
			response.sendRedirect(path+"/servlet/ProductAction?action_flag=list");
		}
		
	}

	private void listProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		
		//String path=request.getContextPath();
		String proname=request.getParameter("proname");//接收用户查询关键字
		System.out.println("用户查询关键字:"+proname);//调试信息
		int recordCount=service.getItemCount();//获得记录的总条数
		System.out.println("recordCount="+recordCount);//调试信息
		int currentPage=1;//当前页是第一页
		String pageNum=request.getParameter("pageNum");
		System.out.println("pageNum:"+pageNum);//调试信息
		
		//分页代码
		if(pageNum!=null){
			currentPage=Integer.parseInt(pageNum);
		}  
		DividePage pUtil=new DividePage(5, recordCount, currentPage);
		int start=pUtil.getFromIndex();
		int end=pUtil.getToIndex();
		System.out.println("CurrentPage=="+pUtil.getCurrentPage());//调试信息
		
		//已经进行分页的数据集合
		List<Map<String, Object>> list=service.listProduct(proname,start,end);
		//System.out.println("list="+list);//调试信息
		//分页代码
		request.setAttribute("pUtil", pUtil);
		/////
		request.setAttribute("proname", proname);
		request.setAttribute("listproduct", list);
		
		request.getRequestDispatcher("/product/2_1_5.jsp").forward(request, response);
		//request.getRequestDispatcher(path+"/servlet/ProductAction?action_flag=list").forward(request, response);
		
	}

	private void addProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 表单含有文件要提交
		String path = request.getContextPath();
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(
				diskFileItemFactory);
		servletFileUpload.setSizeMax(24 * 1024 * 1024);// 上传文件的总大小
		servletFileUpload.setFileSizeMax(8 * 1024 * 1024);// 上传文件的单个大小
		List<FileItem> list = null;
		List<Object> params = new ArrayList<Object>();
		params.add(UUIDTools.getUUID());
		try {
			// 解析request的请求
			list = servletFileUpload.parseRequest(request);
			// 取出所有表单的值：判断非文本字段和文本字段
			for (FileItem fileitem : list) {
				if (fileitem.isFormField()) {
					if (fileitem.getFieldName().equals("proname")) {
						params.add(fileitem.getString("utf-8"));
					}
					if (fileitem.getFieldName().equals("proprice")) {
						params.add(fileitem.getString("utf-8"));
					}
					if (fileitem.getFieldName().equals("proaddress")) {
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
						boolean flag = service.addProduct(params);
						if (flag) {//成功添加产品以后
							//成功添加产品以后，不应该直接转到目标Jsp页面，而应该交给Servlet处理
							//response.sendRedirect(path + "/product/2_1_5.jsp");
							response.sendRedirect(path + "/servlet/ProductAction?action_flag=list");
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
		service = new ProductDao();// 接口实例化为实现类
	}

}
