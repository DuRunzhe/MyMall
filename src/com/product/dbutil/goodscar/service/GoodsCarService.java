package com.product.dbutil.goodscar.service;

import java.util.List;
import java.util.Map;

public interface GoodsCarService {
	/**
	 * 插入指定商品到购物车
	 * 
	 * @param user_id
	 * @param goods_id
	 * @return
	 */
	public boolean insertToGoodscar(String user_id, String goods_id);
	/**检查购物车中是否存在某商品
	 * @param user_id
	 * @param goods_id
	 * @return
	 */
	public boolean checkIsinGoodscar(String user_id, String goods_id);

	/**
	 * 删除商品在购物车
	 * 
	 * @param user_id
	 * @param goods_id
	 * @return
	 */
	public boolean deleteInGoodscar(String sql,List<Object> params);

	/**
	 * 清空购物车
	 * 
	 * @param user_id
	 * @param goods_id
	 * @return
	 */
	public boolean clearGoodscar(String user_id);

	/**
	 * 查询购物车某用户的商品
	 * 
	 * @param user_id
	 * @return
	 */
	public List<Map<String, Object>> searchGoodscar(String user_id);

}
