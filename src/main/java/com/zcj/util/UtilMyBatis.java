package com.zcj.util;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import com.mysql.jdbc.Connection;

/**
 * 
 * <dependency>
 *		<groupId>org.mybatis</groupId>
 *		<artifactId>mybatis</artifactId>
 *		<version>3.2.2</version>
 *	</dependency>
 *	<dependency>
 *		<groupId>mysql</groupId>
 *		<artifactId>mysql-connector-java</artifactId>
 *		<version>5.1.18</version>
 *	</dependency>
 *
 * @author zouchongjin@sina.com
 * @data 2014年7月22日
 */
public class UtilMyBatis {

	/**
	 * MyBatis 工具运行Sql文件执行MySQL语句<br/>
	 * 
	 * 使用方法：<br/>
	 * 	UtilMyBatis.myBatisRunMySqlFile("conf/application.properties", "jdbc.url", "jdbc.driver", "jdbc.username", "jdbc.password", "conf/version3.sql");
	 * @param jdbcFilePath
	 * @param urlKey
	 * @param driverKey
	 * @param usernameKey
	 * @param passwordKey
	 * @param sqlPath
	 * @return
	 */
	public static boolean myBatisRunMySqlFile(String jdbcFilePath, String urlKey, String driverKey, String usernameKey, String passwordKey, String sqlPath) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Properties props = Resources.getResourceAsProperties(jdbcFilePath);
			String url = props.getProperty(urlKey);
			String driver = props.getProperty(driverKey);
			String username = props.getProperty(usernameKey);
			String password = props.getProperty(passwordKey);
			Class.forName(driver).newInstance();
			conn = (Connection) DriverManager.getConnection(url, username, password);
			ScriptRunner runner = new ScriptRunner(conn);
			runner.setErrorLogWriter(null);
			runner.setLogWriter(null);
			runner.runScript(Resources.getResourceAsReader(sqlPath));
			return true;
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return false;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		}
	}
}
