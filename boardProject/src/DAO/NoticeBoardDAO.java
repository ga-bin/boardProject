package DAO;

import java.sql.SQLException;

import VO.Content;

public class NoticeBoardDAO extends DAO {
	// 싱글톤
	private static NoticeBoardDAO nDAO = null;
	private NoticeBoardDAO() {
		
	}
	
	public static NoticeBoardDAO getInstance() {
		if (nDAO == null) {
		nDAO = new NoticeBoardDAO();
		} 
		return nDAO;
	}
	
	public void createContent(Content content) {
		try {
			connect();
			String sql = "INSERT INTO ? VALUES (notice_content_pk.nextval(), ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, content.getTableName());
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public void
			
}
