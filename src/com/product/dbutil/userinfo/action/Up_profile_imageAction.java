package com.product.dbutil.userinfo.action;

import java.io.File;
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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.product.dbutil.order.service.OrderService;
import com.product.dbutil.userinfo.dao.UploadUserImageDao;
import com.product.jsonUtils.JsonTools;

public class Up_profile_imageAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService uploadUserImageService;

	/**
	 * Constructor of the object.
	 */
	public Up_profile_imageAction() {
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
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String user_id = request.getParameter("user_id");
		System.out.println("--->>request得到的参数User_id=" + user_id);
		// 表单含有文件要提交
		String path = request.getContextPath();

		List<Object> params = new ArrayList<Object>();
		Map<String, Object> json = new HashMap<String, Object>();

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
//			String realpath = request.getSession().getServletContext()
//					.getRealPath("/upload");
//			System.out.println("Up_profile_imageAction得到的realpath=" + realpath);
//			File dir = new File(realpath);
//			if (!dir.exists()) {
//				dir.mkdirs();
//				System.out.println("upload文件夹不存在，已创建");
//			}
//			System.out.println("upload文件夹已经存在");
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			upload.setSizeMax(24 * 1024 * 1024);// 上传文件的总大小
			upload.setFileSizeMax(8 * 1024 * 1024);// 上传文件的单个大小
			try {
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) {
						if (item.getFieldName().equals("user_id")) {
							user_id = item.getString("UTF-8");
							System.out.println("--->>得到的参数User_id=" + user_id);
						}
					} else {
						String realpath = request.getSession().getServletContext()
								.getRealPath("/upload"+"/"+user_id);
						System.out.println("Up_profile_imageAction得到的realpath=" + realpath);
						File dir = new File(realpath);
						if (!dir.exists()) {
							dir.mkdirs();
							System.out.println("upload/user_id文件夹不存在，已创建");
						}
						System.out.println("upload/user_id文件夹已经存在");
						String image = item.getName();
						params.add(image);
						params.add(user_id);
						String upload_path = realpath;
						System.out.println("----->>上传头像图片的路径：" + upload_path);
						File real_path = new File(upload_path + "/" + image);
						item.write(real_path);
						String sql = "UPDATE userinfo SET profile_image=? WHERE id=?";
						boolean flag = uploadUserImageService.updataOneItem(
								sql, params);

						if (flag) {
							json.put("state", 888);
						} else {
							json.put("state", 111);
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String jsonStr = JsonTools.createJsonString("json", json);
		System.out.println("执行Up_profile_imageAction这个Servlet生成的jsonStr="
				+ jsonStr);
		out.println(jsonStr);

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
		uploadUserImageService = new UploadUserImageDao();
	}

}
