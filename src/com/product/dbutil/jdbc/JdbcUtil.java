package com.product.dbutil.jdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.jdbc.domain.UserInfo;


public class JdbcUtil {
	private final String USERNAME = "public";
	private final String PWD = "public";
	private final String DRIVERS = "com.mysql.jdbc.Driver";
	private final String URL = "jdbc:mysql://localhost:3306/myconn";
	private Connection conn;
	
	private Statement stmt;//批处理操作对象
	/*
	 * 表示预编译的 SQL 语句的对象。 SQL 语句被预编译并存储在 PreparedStatement
	 * 对象中。然后可以使用此对象多次高效地执行该语句。
	 */
	private PreparedStatement pstmt;
	private ResultSet resultSet;

	public JdbcUtil() {
		try {
			Class.forName(DRIVERS);
			System.out.println("注册驱动成功！执行者是"+this.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 定义获取数据库的连接
	public Connection getConnection() {
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PWD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	//执行批量删除操作
	public boolean deleteByBatch(String[] sql)throws SQLException{
		boolean flag=false;
		stmt=conn.createStatement();
		if(sql!=null){
			for(int i=0;i<sql.length;i++){
				stmt.addBatch(sql[i]);
			}
		}
		int[] count=stmt.executeBatch();
		if(count!=null){
			flag=true;
		}
		return flag;
	}

	/**
	 * 完成对数据库表的添加删除等修改操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public boolean updataByPrepareStatement(String sql, List<Object> params)
			throws SQLException {
		System.out.println("JdbcUtil.updataByPrepareStatement()一次");//调试信息
		boolean flag = false;
		int result = -1;// 表示用户执行sql语句影响到数据库的行数
		
		// 创建一个 PreparedStatement 对象来将参数化的 SQL 语句发送到数据库。
		pstmt = conn.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				/*
				 * 使用给定对象设置指定参数的值。第二个参数必须是 Object 类型；所以，应该对内置类型使用 java.lang
				 * 的等效对象。
				 * 
				 * JDBC 规范指定了一个从 Java Object 类型到 SQL 类型的标准映射关系。在发送到数据库之前
				 * ，给定参数将被转换为相应 SQL 类型。 List集合添加的值被按序映射到数据库表的逐个列
				 */
				pstmt.setObject(index++, params.get(i));
			}
		}
		System.out.println("--------updataByPrepareStatement执行的Sql语句："+sql);
		
		//
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;

		return flag;

	}

	// 查询返回单条记录的方法
	public Map<String, Object> findSimpleResult(String sql, List<Object> params)
			throws SQLException {
		// params表示一行数据库表的记录的列表
		Map<String, Object> map = new HashMap<String, Object>();
		int index = 1;
		
		//建一个 PreparedStatement 对象来将参数化的 SQL 语句发送到数据库。
		pstmt = conn.prepareStatement(sql);// 
		if (params != null && !params.isEmpty()) {
			/*
			 * 使用给定对象设置指定参数的值。第二个参数必须是 Object 类型；所以，应该对内置类型使用 java.lang 的等效对象。
			 * 
			 * JDBC 规范指定了一个从 Java Object 类型到 SQL 类型的标准映射关系。在发送到数据库之前
			 * ，给定参数将被转换为相应 SQL 类型。 List集合添加的值被按序映射到数据库表的逐个列
			 */
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		
		//
		resultSet = pstmt.executeQuery();// 返回查询结果
		ResultSetMetaData metaData = resultSet.getMetaData();
		
		//
		int col_len = metaData.getColumnCount();// 获取列的个数
		while (resultSet.next()) {
			for (int i = 0; i < col_len; i++) {
				// 获取此 ResultSet 对象的列的编号、类型和属性。
				String colsName = metaData.getColumnName(i + 1);
				Object colsValue = resultSet.getObject(colsName);
				if (colsValue == null) {
					colsValue = "";
				}
				map.put(colsName, colsValue);
			}
		}
		System.out.println("findSimpleResult（）方法生成的map="+map.toString());

		return map;
	}

	public List<Map<String, Object>> findMoreResult(String sql,
			List<Object> params) throws SQLException {
		System.out.println("调用findMoreResult方法一次");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;//?
		//
		pstmt = conn.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		//
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		
		//
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<>();
			for (int i = 0; i < cols_len; i++) {
				String colName = metaData.getColumnName(i + 1);
				Object colValue = resultSet.getObject(colName);
				if (colValue == null) {
					colValue = "";
				}
				map.put(colName, colValue);
			}
			list.add(map);
		}

		return list;
	}

	// 反射机制实现实现jdbc数据封装
	public <T> T findSimpleRefResult(String sql, List<Object> params,
			Class<T> cls) throws SQLException {
		// Map<String, Object> map = new HashMap<String, Object>();//调试信息
		T resultObject = null;
		int index = 1;
		pstmt = conn.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int colsLen = metaData.getColumnCount();
		// System.out.println("colsLen="+colsLen);// 调试信息
		int num = 0;
		while (resultSet.next()) {
			num++;// 调试信息
			// 通过反射机制创建实例
			try {
				resultObject = cls.newInstance();
			} catch (InstantiationException | IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (int i = 0; i < colsLen; i++) {
				String colsName = metaData.getColumnName(i + 1);
				// System.out.println("colsName="+colsName);// 调试信息
				Object colsValue = resultSet.getObject(colsName);
				// System.out.println("colsValue="+colsValue.toString());// 调试信息

				// map.put(colsName, colsValue);// 调试信息

				try {
					Field field = cls.getDeclaredField(colsName);// 返回一个 Field
																	// 对象，该对象反映此
																	// Class
																	// 对象所表示的类或接口的指定已声明字段。name
																	// 参数是一个
																	// String，它指定所需字段的简称
					field.setAccessible(true);
					field.set(resultObject, colsValue);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		// System.out.println("map="+map);
		// System.out.println("resultObject="+resultObject);
		System.out.println("num=" + num);
		return resultObject;
	}

	// 反射机制实现实现jdbc数据(多行数据)封装
	public <T> List<T> findMoreRefResult(String sql, List<Object> params,
			Class<T> cls) throws SQLException, InstantiationException,
			IllegalAccessException {
		List<T> list = new ArrayList<T>();
		// T resultObject = null;
		int index = 1;
		pstmt = conn.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int colsLen = metaData.getColumnCount();
		System.out.println("colsLen=" + colsLen);// 调试信息
		int num = 0;
		while (resultSet.next()) {
			num++;
			// 通过反射机制创建实例
			T resultObject = cls.newInstance();
			// try {//无法Try/Catch
			// T resultObject = cls.newInstance();
			// } catch (InstantiationException | IllegalAccessException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			for (int i = 0; i < colsLen; i++) {
				String colsName = metaData.getColumnName(i + 1);
				// System.out.println("colsName="+colsName);// 调试信息
				Object colsValue = resultSet.getObject(colsName);
				// System.out.println("colsValue="+colsValue.toString());// 调试信息
				if (colsValue == null) {
					colsValue = "";
				}
				// map.put(colsName, colsValue);// 调试信息

				try {
					Field field = cls.getDeclaredField(colsName);// 返回一个 Field
																	// 对象，该对象反映此
																	// Class
																	// 对象所表示的类或接口的指定已声明字段。name
																	// 参数是一个
																	// String，它指定所需字段的简称
					field.setAccessible(true);
					field.set(resultObject, colsValue);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			list.add(resultObject);

		}
		// System.out.println("map="+map);
		// System.out.println("resultObject="+resultObject);
		System.out.println("num=" + num);
		return list;
	}

	// 关闭数据库连接的方法
	public void releaseConn() {
		if (resultSet != null) {
			try {
				resultSet.close();
				System.out.println("Success to close resultSet!");
			} catch (SQLException e) {
				System.out
						.println("Fail to close DataBase resultSet connection!");
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
				System.out.println("Success to close PreparedStatement!");
			} catch (Exception e) {
				System.out
						.println("Fail to close DataBase  PreparedStatement connection!");
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
				System.out.println("Success to close DataBase connection!");
			} catch (Exception e) {
				System.out.println("Fail to close DataBase connection!");
				e.printStackTrace();
			}
		}
		if(stmt!=null){
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test_findMoreRefResult() {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		// List<Object> params = new ArrayList<Object>();
		// params.add("321309010205");
		String sql = "select * from user";
		try {
			List<UserInfo> list = jdbcUtil.findMoreRefResult(sql, null,
					UserInfo.class);
			System.out.println(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		jdbcUtil.releaseConn();
	}
	

	@Test
	public void test_findSimpleRefResult() throws InstantiationException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		List<Object> params = new ArrayList<Object>();
		params.add("321309010205");
		String sql = "select * from user where id=?";
		try {
			UserInfo userInfo = jdbcUtil.findSimpleRefResult(sql, params,
					UserInfo.class);
			System.out.println("userInfo.toString()" + userInfo.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcUtil.releaseConn();
	}

	@Test
	public void test_updataByPrepareStatement() {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		String sql = "insert into user(id,name,address) values(?,?,?)";
		List<Object> params = new ArrayList<>();
		params.add("321309010209");
		params.add("默默");
		params.add("河南省安阳市");
		try {
			boolean flag = jdbcUtil.updataByPrepareStatement(sql, params);

			System.out.println("修改数据" + flag);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcUtil.releaseConn();
	}

	@Test
	public void test_findSimpleResult() {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		List<Object> params = new ArrayList<Object>();
		params.add("321309010205");
		String sql = "select * from user where id=?";
		try {
			Map<String, Object> map = jdbcUtil.findSimpleResult(sql, params);
			System.out.println(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcUtil.releaseConn();
	}

	@Test
	public void test_findMoreResult() {
		JdbcUtil jdbcUtil = new JdbcUtil();//这里调用了JdbcUtil的构造方法，构造方法中有注册驱动的代码
		System.out.println("调用test_findMoreResult方法一次");
		jdbcUtil.getConnection();
		String sql = "select * from user";
		try {
			List<Map<String, Object>> list = jdbcUtil.findMoreResult(sql, null);
			System.out.println(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcUtil.releaseConn();
	}
//	public static void main(String[] arg){
//		JdbcUtil jdbcUtil = new JdbcUtil();
//		jdbcUtil.getConnection();
//		// List<Object> params = new ArrayList<Object>();
//		// params.add("321309010205");
//		String sql = "select * from user";
//		try {
//			List<UserInfo> list = jdbcUtil.findMoreRefResult(sql, null,
//					UserInfo.class);
//			System.out.println(list);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		jdbcUtil.releaseConn();
//	}

}
