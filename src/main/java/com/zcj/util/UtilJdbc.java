package com.zcj.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.zcj.util.json.json.JSONArray;
import com.zcj.util.json.json.JSONException;
import com.zcj.util.json.json.JSONObject;

public class UtilJdbc {

	public static String DATABASETYPE_SQLSERVER = "sqlserver";
	public static String DATABASETYPE_MYSQL = "mysql";
	public static String DATABASETYPE_ORACLE = "oracle";
	
	/**
	 * 
	 * @param databaseType UtilJdbc.DATABASETYPE_SQLSERVER
	 * @param ip 192.168.1.119
	 * @param port 1433
	 * @param databaseName txl
	 * @param username "sa"
	 * @param password "123456"
	 * @param querySql "select * from db_Dept"
	 * @return
	 */
	public static String query(String databaseType, String ip, int port, String databaseName, String username, String password, String querySql) {
		String result = null;
		
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		try {
			if (DATABASETYPE_SQLSERVER.equals(databaseType)) {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
				conn = DriverManager.getConnection("jdbc:sqlserver://"+ip+":"+port+";DatabaseName="+databaseName, username, password);
			} else if (DATABASETYPE_MYSQL.equals(databaseType)) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+databaseName, username, password);
			} else if (DATABASETYPE_ORACLE.equals(databaseType)) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":"+port+":"+databaseName+"", username, password);
			} else {
				return result;
			}
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySql);
			result = resultSetToJson(rs);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static String resultSetToJson(ResultSet rs) throws SQLException, JSONException {
		JSONArray array = new JSONArray();

		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();

		// 遍历ResultSet中的每条数据
		while (rs.next()) {
			JSONObject jsonObj = new JSONObject();

			// 遍历每一列
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnLabel(i);
				String value = rs.getString(columnName);
				jsonObj.put(columnName, value);
			}
			array.put(jsonObj);
		}

		return array.toString();
	}
	
}
