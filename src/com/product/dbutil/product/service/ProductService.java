package com.product.dbutil.product.service;

import java.util.List;
import java.util.Map;

public interface ProductService {
	public boolean addProduct(List<Object> params);
	/**用于web服务器端通过关键字获取产品信息或者列出全部，并执行分页操作
	 * @param proname
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Map<String, Object>> listProduct(String proname,int start,int end);//提取所有产品的信息
	/**用于安卓端通过关键字获取产品列表信息或者列出全部产品信息
	 * @param proname
	 * @return
	 */
	public List<Map<String, Object>> listProduct(String proname);
	/**用于安卓端通过Sql语句获取产品列表信息或者列出全部产品信息
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> listCategoryProduct(String sql);
	/**用于web端得到产品的分类信息
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getProductCategory(String sql,List<Object> params);
	/**用于web端修改指定商品信息
	 * @param sql
	 * @param params
	 * @return
	 */
	public boolean modifyProduct(String sql,List<Object> params);
	/**查询单条记录
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findMoreResult(String sql,List<Object> params);
	public int getItemCount();//得到总记录条数
	public boolean delProduct(String[] idsStrings);
	public Map<String, Object> viewProduct(String proid);
}
