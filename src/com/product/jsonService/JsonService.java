package com.product.jsonService;

import com.jdbc.domain.JsonUser;

public class JsonService {
	/**
	 * 返回一个JsonUser对象，一般是根据提供的参数从数据书库读取
	 * @return
	 */
	public static JsonUser getJsonUser() {
		JsonUser jsonUser = new JsonUser(15,"qwe","ere.jpg",500.0f);
		return jsonUser;
	}

}
