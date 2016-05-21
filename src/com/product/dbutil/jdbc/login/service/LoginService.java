package com.product.dbutil.jdbc.login.service;

import java.util.List;

import com.jdbc.domain.JsonUser;

public interface LoginService {
	/**
	 * 登录功能的接口方法，返回登录成功标志
	 * @param params
	 * @return
	 */
	public boolean login(List<Object> params);
	/**
	 * 登录功能的接口方法，返回登录的用户对象
	 * @return
	 */
	public JsonUser login_getJsonUser(List<Object> params);

}
