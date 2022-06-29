package DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DAO {
	// oracle db정보
	private String jdbc_driver = "oracle.jdbc.driver.OracleDriver";
	private String oracle_url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String connectedId = "hr";
	private String connectedPwd = "hr";
	
	// 공통적으로 사용할 필드
	protected Connection conn;
	protected Statement stmt;
	protected PreparedStatement pstmt;
	protected ResultSet rs;
	
	public DAO() {
//		dbConfig();
	}
	
	public void connect() {
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(oracle_url, connectedId, connectedPwd);
		} catch(ClassNotFoundException e) {
			System.out.println("jdbc_driver 로딩 실패");
		} catch(SQLException e) {
			System.out.println("db연결 실패");
			
		}
	}
	
//	public void dbConfig() {
//		String resource = "config/db.properties";
//		Properties properties = new Properties();
//		
//		try {
//			String filePath = ClassLoader.getSystemClassLoader().getResource(resource).getPath();	
//			properties.load(new FileInputStream(filePath));
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
//		jdbc_driver = properties.getProperty("driver");
//		oracle_url = properties.getProperty("url");
//		connectedId = properties.getProperty("id");
//		connectedPwd = properties.getProperty("password");
//	}
	
	public void disconnect() {
		if (rs != null)
			try {
				rs.close();
				if (stmt != null) stmt.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}