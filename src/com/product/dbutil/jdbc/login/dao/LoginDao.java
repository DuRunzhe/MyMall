package com.product.dbutil.jdbc.login.dao;

import java.util.List;
import java.util.Map;

import com.jdbc.domain.JsonUser;
import com.product.dbutil.jdbc.JdbcUtil;
import com.product.dbutil.jdbc.login.service.LoginService;

public class LoginDao implements LoginService {
	private JdbcUtil jdbcUtil = null;

	public LoginDao() {
		jdbcUtil = new JdbcUtil();
	}

	@Override
	public boolean login(List<Object> params) {
		boolean flag = false;
		String sql = "select * from userinfo where username=? and pswd=?";
		try {
			jdbcUtil.getConnection();
			Map<String, Object> map = jdbcUtil.findSimpleResult(sql, params);
			flag=!map.isEmpty()?true:false;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbcUtil.releaseConn();
		}
		return flag;
	}

	@Override
	public JsonUser login_getJsonUser(List<Object> params) {
		JsonUser jsonUser=new JsonUser();
		String sql = "select * from userinfo where username=? and pswd=?";
		try {
			jdbcUtil.getConnection();
			Map<String, Object> map = jdbcUtil.findSimpleResult(sql, params);
			//从查询结果中生成JsonUser对象
			jsonUser.setId((int)map.get("id"));
			jsonUser.setUsername((String) map.get("username"));
			jsonUser.setMoney((Double) map.get("money"));
			jsonUser.setState((int) map.get("state"));
			jsonUser.setProfile_image((String) map.get("profile_image"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbcUtil.releaseConn();
		}
		return jsonUser;
	}

}
