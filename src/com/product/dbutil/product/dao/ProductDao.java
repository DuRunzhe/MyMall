package com.product.dbutil.product.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.product.dbutil.jdbc.JdbcUtil;
import com.product.dbutil.product.service.ProductService;

public class ProductDao implements ProductService {
	private JdbcUtil jdbcUtil;

	public ProductDao() {
		jdbcUtil = new JdbcUtil();
	}

	@Override
	public boolean addProduct(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into product (proid,proname,proprice,proaddress,proimage) values(?,?,?,?,?)";
			jdbcUtil.getConnection();
			flag = jdbcUtil.updataByPrepareStatement(sql, params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/* (non-Javadoc)
	 * 提取产品的信息
	 * @see com.product.dbutil.product.service.ProductService#listProduct()
	 */
	@Override
	public List<Map<String, Object>> listProduct(String proname,int start,int end) {
		List<Map<String, Object>> list=new ArrayList<>();
		String sql="select * from product where (1=1) ";
		StringBuffer buffer=new StringBuffer(sql);
		List<Object> params=new ArrayList<Object>();
		try {
			System.out.println("proname="+proname);//调试信息
			if(proname!=null){
				
				buffer.append("and proname like ?");
				params.add("%"+proname+"%");
			}
			//分页代码
			buffer.append(" limit ?,? ");
			System.out.println("start="+start+"end="+end);//调试信息
			params.add(start);
			params.add(end);
			/////
			jdbcUtil.getConnection();
			list=jdbcUtil.findMoreResult(buffer.toString(), params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbcUtil.releaseConn();
		}
		return list;
	}

	@Override
	public int getItemCount() {
		int result=0;
		Map<String, Object> map=null;
		String sql="select  count(*) mycount from product";
		try {
			jdbcUtil.getConnection();
			map=jdbcUtil.findSimpleResult(sql, null);
			result=Integer.parseInt(map.get("mycount").toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbcUtil.releaseConn();
		}
		return result;
	}

	/* (non-Javadoc)
	 * 批量删除操作
	 * @see com.product.dbutil.product.service.ProductService#delProduct(java.lang.String[])
	 */
	@Override
	public boolean delProduct(String[] ids) {
		boolean flag=false;
		try {
			jdbcUtil.getConnection();
			String[] sql=new String[ids.length];
			if(ids!=null){//如果选中的有复选框按钮
				for(int i=0;i<ids.length;i++){
					sql[i]="delete from product where proid='"+ids[i]+"'";
				}
			}
			flag=jdbcUtil.deleteByBatch(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbcUtil.releaseConn();
		}
		
		return flag;
	}

	@Override
	public Map<String, Object> viewProduct(String proid) {
		Map<String, Object> map=null;
		try {
			String sql="select * from product where proid=?";
			List<Object> params=new ArrayList<Object>();
			params.add(proid);
			jdbcUtil.getConnection();
			map=jdbcUtil.findSimpleResult(sql, params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbcUtil.releaseConn();
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> listProduct(String proname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> listCategoryProduct(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getProductCategory(String sql, List<Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean modifyProduct(String sql, List<Object> params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Map<String, Object>> findMoreResult(String sql, List<Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

}
