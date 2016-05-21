package com.product.dbutil.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.product.dbutil.jdbc.JdbcUtil;
import com.product.dbutil.order.service.OrderService;

public class OrderDao implements OrderService {
	private JdbcUtil jdbcUtil;
	public OrderDao() {
		jdbcUtil = new JdbcUtil();
	}
	/* (non-Javadoc)
	 * @see com.product.dbutil.order.dao.OrderService#updataOneItem(java.lang.String, java.util.List)
	 */
	@Override
	public boolean updataOneItem(String sql,List<Object> params){
		boolean flag=false;
		jdbcUtil.getConnection();
		try {
			flag=jdbcUtil.updataByPrepareStatement(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			// 关闭数据库的连接
			jdbcUtil.releaseConn();
		}
		
		return flag;
	}
	/* (non-Javadoc)
	 * @see com.product.dbutil.order.dao.OrderService#queryOneRecord(java.lang.String, java.util.List)
	 */
	@Override
	public Map<String, Object> queryOneRecord(String sql,List<Object> params){
		Map<String, Object> result=new HashMap<String, Object>();
		jdbcUtil.getConnection();
		try {
			result=jdbcUtil.findSimpleResult(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			// 关闭数据库的连接
			jdbcUtil.releaseConn();
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see com.product.dbutil.order.dao.OrderService#queryMultiRecord(java.lang.String, java.util.List)
	 */
	@Override
	public List<Map<String, Object>> queryMultiRecord(String sql,List<Object> params){
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		jdbcUtil.getConnection();
		try {
			list=jdbcUtil.findMoreResult(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			// 关闭数据库的连接
			jdbcUtil.releaseConn();
		}
		return list;
	}
	

}
