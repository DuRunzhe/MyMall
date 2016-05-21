package com.product.dbutil.goodscar.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.product.dbutil.goodscar.service.GoodsCarService;
import com.product.dbutil.jdbc.JdbcUtil;

public class GoodsCarDao implements GoodsCarService {
	private JdbcUtil jdbcUtil;

	public GoodsCarDao() {
		jdbcUtil = new JdbcUtil();
	}

	@Override
	public boolean insertToGoodscar(String user_id, String goods_id) {
		boolean flag = false;
		// insert into goods
		// (goods_id,goods_name,old_price,goods_location,goods_image)
		// values(?,?,?,?,?)

		String sql = "insert into goodscar (goods_id,goods_name,user_id) values(?,(select goods_name from goods where goods_id=?),?)";
		StringBuffer buffer = new StringBuffer(sql);
		List<Object> params = new ArrayList<Object>();
		params.add(goods_id);
		params.add(goods_id);
		params.add(user_id);

		jdbcUtil.getConnection();
		try {
			flag = jdbcUtil.updataByPrepareStatement(buffer.toString(), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deleteInGoodscar(String sql, List<Object> params) {
		boolean flag = false;

		jdbcUtil.getConnection();
		try {
			flag = jdbcUtil.updataByPrepareStatement(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			jdbcUtil.releaseConn();
		}
		return flag;
	}

	@Override
	public boolean clearGoodscar(String user_id) {
		boolean flag = false;
		List<Object> params = new ArrayList<Object>();
		params.add(user_id);
		String sql = "delete from goodscar where user_id=?";

		jdbcUtil.getConnection();
		try {
			flag = jdbcUtil.updataByPrepareStatement(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public List<Map<String, Object>> searchGoodscar(String user_id) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String sql = "select * from goods where goods_id in"
				+ "(select goods_id from goodscar where user_id=?)";

		StringBuffer buffer = new StringBuffer(sql);
		List<Object> params = new ArrayList<Object>();
		params.add(user_id);

		jdbcUtil.getConnection();
		try {
			result = jdbcUtil.findMoreResult(buffer.toString(), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public boolean checkIsinGoodscar(String user_id, String goods_id) {
		boolean flag = false;
		List<Object> params = new ArrayList<Object>();
		params.add(user_id);
		params.add(goods_id);
		String sql = "select * from goodscar where user_id=? and goods_id=?";

		jdbcUtil.getConnection();
		try {
			Map<String, Object> resultMap = jdbcUtil.findSimpleResult(sql,
					params);
			// 数据库中 不存在这个数据为true
			flag = resultMap.isEmpty() ? true : false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}

}
