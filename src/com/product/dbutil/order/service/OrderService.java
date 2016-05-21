package com.product.dbutil.order.service;

import java.util.List;
import java.util.Map;

public interface OrderService {

	/**创建订单项,一般对应三个数据表的操作：order表，goodlist表，logistics表
	 * @param sql
	 * @param params
	 * @return
	 */
	public abstract boolean updataOneItem(String sql, List<Object> params);

	/**查询一条记录
	 * @param sql
	 * @param params
	 * @return
	 */
	public abstract Map<String, Object> queryOneRecord(String sql,
			List<Object> params);

	/**查询多条记录
	 * @param sql
	 * @param params
	 * @return 结果的List集合
	 */
	public abstract List<Map<String, Object>> queryMultiRecord(String sql,
			List<Object> params);

}
