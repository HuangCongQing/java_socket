package com.imooc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//ע�����ǽ�ԭ��������Util util=new Util();
   
public class Util {
//	private static final String URL = "jdbc:mysql://127.0.0.1:3306/imooc";
//	private static final String NAME = "root";
//	private static final String PASSWORD = "199666";
//
//	public static Connection conn=null;
//
//	static{
//	// ������ݿ������
//	try {
//		Class.forName("com.mysql.jdbc.Driver");
//		conn = DriverManager.getConnection(URL, NAME, PASSWORD);
//	} catch (SQLException e) {
//		e.printStackTrace();
//	} catch (ClassNotFoundException e) {
//		e.printStackTrace();
//	}
//	}
	
//	public  Connection getConnection(){
//	
//	
//	return conn;
//}

	public static  Connection getConnection(){
	     String driverClassName="com.mysql.jdbc.Driver";
		String URL = "jdbc:mysql://127.0.0.1:3306/imooc";
	   String username = "root";
	    String password = "199666";
	    Connection conn=null;
		try {
			Class.forName(driverClassName);
			conn=DriverManager.getConnection(URL, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

//	�ر���Դ�Ĳ���
	public static  void closeAll( Connection conn,Statement stmt,ResultSet rs){
		
		try {
			if(conn!=null){
				conn.close();
			}
			if(stmt!=null){
				stmt.close();
			}if(rs!=null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
