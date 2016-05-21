package com.product.dbutil.jdbc.register.dao;

import java.util.List;
import java.util.Map;

import com.jdbc.domain.JsonUser;
import com.product.dbutil.jdbc.JdbcUtil;
import com.product.dbutil.jdbc.register.service.RegisterService;

public class RegisterDao implements RegisterService {
	private JdbcUtil utils = null;

	public RegisterDao() {
		utils = new JdbcUtil();
	}

	/*
	 * 用户注册Dao (non-Javadoc)
	 * 
	 * @see
	 * com.product.dbutil.service.RegisterService#registerUser(java.util.List)
	 */
	@Override
	public boolean registerUser(List<Object> params) {
		System.out.println("执行registerUser（）方法一次");// 调试信息
		boolean flag = false;
		utils.getConnection();
		String sql = "insert into userinfo(username,pswd,realname) values(?,?,?)";

		try {
			flag = utils.updataByPrepareStatement(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			// 关闭数据库的连接
			utils.releaseConn();
		}
		return flag;
	}

	@Override
	public boolean register_android(List<Object> params) {
		boolean flag = false;

		System.out.println("执行register_getJsonUser（）方法一次");// 调试信息

		utils.getConnection();
		String sql = "insert into userinfo(username,pswd,realname,email) values(?,?,?,?)";// By
																							// drz添加email字段，增加到4个占位符

		try {
			flag=utils.updataByPrepareStatement(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			// 关闭数据库的连接
			utils.releaseConn();
		}
		return flag;
	}

	@Override
	public boolean isIDin(List<Object> params) {
		boolean flag = false;
		String sql = "select * from userinfo where username=?";
		try {
			utils.getConnection();
			Map<String, Object> map = utils.findSimpleResult(sql, params);
			flag=!map.isEmpty()?true:false;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			utils.releaseConn();
		}
		return flag;
	}

}
