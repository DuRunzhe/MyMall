package com.product.dbutil.jdbc.login.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.product.dbutil.jdbc.JdbcUtil;
import com.product.dbutil.jdbc.login.service.UserService;

public class UserDao implements UserService{
	private JdbcUtil jdbcUtil = null;
	public UserDao() {
		jdbcUtil = new JdbcUtil();
	}
	@Override
	public Map<String, Object> updataUser(String sql, List<Object> params) {
		Map<String, Object> result=new HashMap<String, Object>();
		jdbcUtil.getConnection();
		
		try {
			result=jdbcUtil.findSimpleResult(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			jdbcUtil.releaseConn();
		}
		
		return result;
	}

	

}
