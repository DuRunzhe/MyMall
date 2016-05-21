package com.product.dbutil.jdbc.login.service;

import java.util.List;
import java.util.Map;

public interface UserService {
	/**更新用户信息
	 * @param sql
	 * @param params
	 * @return
	 */
	public Map<String, Object> updataUser(String sql,List<Object> params);

}
