package com.limix.hello.odbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 通过JDBC-ODBC 桥接器连接 IMPALA 类型错误 dialect 参考
 * 背景: 调试IMPALA的ODBC连接,SQL请求是正确的,但是查询结果错误,经过调试,是因为类型匹配错误原因导致
 * 
 * 说明: 查询IMPALA的文档,其类型对应关系 INT 对应SQL_TYPE为INTEGER 对应JAVA类型 LONG类型
 * @author limix
 */
public class ImpalaODBCTest {

	public static void main(String[] args) {
		Connection ct = null;
		Statement sm = null;
		try {
			String sql = "select time_by_day.the_year as c0 from time_by_day time_by_day group by time_by_day.the_year";
			String dialectType = args.length > 2 ? args[1] : "string";
			// 加载驱动
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			ct = DriverManager.getConnection("jdbc:odbc:Impala", "", "");
			sm = ct.createStatement();
			ResultSet rs = sm.executeQuery(sql);
			System.out.println("cloume type" + rs.getMetaData().getColumnType(1));
			while (rs.next()) {
				switch (dialectType) {
				case "int":
					System.out.println("the year is:%s" + rs.getInt(1));
					break;
				case "long":
					System.out.println("the year is:%s" + rs.getLong(1));
					break;
				default:
					System.out.println("the year is:%s" + rs.getString(1));
					break;
				}

				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			// 关闭顺序是，谁后创建则先关闭
			try {
				if (sm != null)
					sm.close();
				if (ct != null)
					ct.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
