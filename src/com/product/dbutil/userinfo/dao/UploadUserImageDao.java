package com.product.dbutil.userinfo.dao;

import java.util.List;
import java.util.Map;

import com.product.dbutil.order.dao.OrderDao;

public class UploadUserImageDao extends OrderDao {

	public UploadUserImageDao() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean updataOneItem(String sql, List<Object> params) {
		// TODO Auto-generated method stub
		return super.updataOneItem(sql, params);
	}

	@Override
	public Map<String, Object> queryOneRecord(String sql, List<Object> params) {
		// TODO Auto-generated method stub
		return super.queryOneRecord(sql, params);
	}

	@Override
	public List<Map<String, Object>> queryMultiRecord(String sql,
			List<Object> params) {
		// TODO Auto-generated method stub
		return super.queryMultiRecord(sql, params);
	}

}
