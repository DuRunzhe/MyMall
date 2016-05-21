package com.product.dbutil.jdbc.register.service;

import java.util.List;

import com.jdbc.domain.JsonUser;

public interface RegisterService {
	public boolean registerUser(List<Object> params);//完成用户注册
	/**完成用户注册，用于手机端，只修改了参数个数
	 * @param params
	 * @return
	 */
	public boolean register_android(List<Object> params);//By Drz 
	/**判断注册的ID是否已经存在
	 * @param params
	 * @return
	 */
	public boolean isIDin(List<Object> params);//By drz

}
