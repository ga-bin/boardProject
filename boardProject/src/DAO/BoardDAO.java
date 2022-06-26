package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import VO.BoardInfo;
import VO.Comment;
import VO.Content;

public class BoardDAO extends DAO {
	// 싱글톤
	private static BoardDAO bDAO = null;
	private BoardDAO() {
		
	}
	
	public static BoardDAO getInstance() {
		if (bDAO == null) {
		bDAO = new BoardDAO();
		} 
		return bDAO;
	}
	
	
	public BoardInfo selectBoard(int tableNum) {
		BoardInfo board = null;
		try {
			connect();
			String sql = "SELECT * FROM boardInfo WHERE board_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tableNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				board = new BoardInfo();
				board.setBoardName(rs.getString("board_name"));
				board.setBoardNum(rs.getInt("board_number"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return board;
	}
	
	public BoardInfo selectBoard(String tableName) {
		BoardInfo board = null;
		try {
			connect();
			String sql = "SELECT * FROM boardInfo WHERE board_name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				board = new BoardInfo();
				board.setBoardName(rs.getString("board_name"));
				board.setBoardNum(rs.getInt("board_number"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return board;
	}
	
	
	public void createContentTable(String tableName) {
		System.out.println(tableName + "_content_board");
		try {
			connect();
			String sql = "CREATE table " + tableName + "_content_board"
					+ "    (text_num number,"
					+ "    member_id varchar(2),"
					+ "    title varchar(2),"
					+ "    content varchar(2)"
					+ ")";
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				System.out.println("게시판 테이블 생성 성공");
			} else {
				System.out.println("게시판 테이블 생성 실패");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public void createCommentTable(String tableName) {
		try {
			connect();
			String sql = "CREATE table" + tableName + "_comment_board (text_num number, comment_num number, title varchar(2), comment varchar(2),"
					+ "constraint foreign key(comment_num) references " + tableName + "_content_board (text_num)";
			stmt = conn.createStatement();	
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				System.out.println("댓글테이블 생성 성공");
			} else {
				System.out.println("댓글테이블 생성 실패");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	// management에서 관리자가 게시판 생성하기 했을 때 createContentTable이랑
	// createCommentTable돌리기 둘다 해야함
	// 그 안에 createSequenceName로직도 돌려야하는데
	// createSequenceName로직을 createCommentSeqName createContentSeqName으로
	// 나눌지는 생각해 보자
	
	public void insertTableToBoardInfo(String tableName) {
		try {
			connect();
			String sql = "INSERT into boardinfo (board_name) VALUES ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName + "_content_board");
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("테이블 등록 완료");
			} else {
				System.out.println("테이블 등록 실패");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	
	public void createSeq(String tableName, String boardType) {
		try {
			connect();
			String sql = "CREATE SEQUENCE ? INCREMENT BY 1 START WITH 1 MINVALUE 1 MAXVALUE 9999 NOCYCLE NOCACHE NOORDER";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName + "_" + boardType + "_seq");
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("시퀀스 생성 성공");
			} else {
				System.out.println("시퀀스 생성 실패");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public List<BoardInfo> showAllBoard() {
		List<BoardInfo> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT * FROM boardinfo ORDER BY board_number";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				BoardInfo boardinfo = new BoardInfo();
				boardinfo.setBoardName(rs.getString("board_name"));
				boardinfo.setBoardNum(rs.getInt("board_number"));
				boardinfo.setUsAble(rs.getInt("usable"));
				
				list.add(boardinfo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	public List<BoardInfo> showUsedBoard() {
		List<BoardInfo> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT * FROM boardinfo WHERE usable = 0";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				BoardInfo boardInfo = new BoardInfo();
				boardInfo.setBoardName(rs.getString("board_name"));
				boardInfo.setBoardNum(rs.getInt("board_number"));
				boardInfo.setUsAble(rs.getInt("usable"));
				
				list.add(boardInfo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	public void makeToggleUsedBoard(String boardName, int usedNum) {
		try {
			connect();
			String sql = "UPDATE boardInfo SET usable = ? WHERE board_name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, usedNum);
			pstmt.setString(2, boardName+"_content_board");
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println(boardName + "의 사용여부가 정상적으로 변경되었습니다.");
			} else {
				System.out.println(boardName + "의 사용여부가 정상적으로 변경되지 않았습니다.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	
	
	
	public void createContent(String tableName, Content content, String sequence) {
		// 나중에 managemenct에서 createcontent를 돌릴때 시퀀스명.nextval이런식으로 구성하면 될 것 같다.
		try {
			connect();
			String sql = "INSERT INTO ? VALUES (" + sequence +", ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			pstmt.setString(3, content.getMemberId());
			pstmt.setString(4, content.getTitle());
			pstmt.setString(5, content.getContent());
			
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("게시글이 등록되었습니다.");
			} else {
				System.out.println("게시글이 등록되지 않았습니다.");
			};
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	
	public Content showContentByNum(String tableName, int textNum) {
		Content content = null;
		try {
			connect();
			String sql = "SELECT * FROM ? WHERE text_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			pstmt.setInt(2, textNum);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				content = new Content();
				content.setContent(rs.getString("content"));
				content.setMemberId(rs.getString("member_id"));
				content.setTextNum(rs.getInt("text_num"));
				content.setTitle(rs.getString("title"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
			
		}
		
		return content;
		
	}
	
	public List<Content> showContentByCon(String tableName, String textContent) {
		List<Content> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT * FROM ? WHERE content Like '%" + textContent + "%'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			while(rs.next()) {
				Content content = new Content();
				content.setContent(rs.getString("content"));
				content.setMemberId(rs.getString("member_id"));
				content.setTextNum(rs.getInt("text_num"));
				content.setTitle(rs.getString("title"));
				
				list.add(content);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		return list;
	}
	
	public List<Content> showContentByTitle(String tableName, String title) {
		List<Content> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT * FROM ? WHERE title Like '%" + title + "%'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			while(rs.next()) {
				Content content = new Content();
				content.setContent(rs.getString("content"));
				content.setMemberId(rs.getString("member_id"));
				content.setTextNum(rs.getInt("text_num"));
				content.setTitle(rs.getString("title"));
				
				list.add(content);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		return list;
	}
	
	public void updateTitle(String tableName, String newTitle, int textNum) {
		try {
			connect();
			String sql = "UPDATE ? SET (title) = ? WHERE text_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			pstmt.setString(2, newTitle);
			pstmt.setInt(3, textNum);
			
			int result = pstmt.executeUpdate();
			
			if (result > 0) {
				System.out.println("정상적으로 제목이 수정되었습니다.");
			} else {
				System.out.println("정상적으로 제목이 수정되지 않았습니다.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public void updateContent(String tableName, String newContent, int textNum) {
		try {
			connect();
			String sql = "UPDATE ? SET (content) = ? WHERE text_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			pstmt.setString(2, newContent);
			pstmt.setInt(3, textNum);
			
			int result = pstmt.executeUpdate();
			
			if (result > 0) {
				System.out.println("정상적으로 게시글 내용이 수정되었습니다.");
			} else {
				System.out.println("정상적으로 게시글 내용이 수정되지 않았습니다.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	// management에서 updateTitle과 updatecontent로직을 동시에 돌리자
	// 제목을 수정안하려면 0을 클릭해라 이런식으로 해서
	
	public void deleteContent(String tableName, int textNum) {
		try {
			connect();
			String sql = "DELETE FROM ? WHERE text_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			pstmt.setInt(2, textNum);
			int result = pstmt.executeUpdate();
			
			if (result > 0) {
				System.out.println("정상적으로 게시글이 삭제되었습니다.");
			} else {
				System.out.println("정상적으로 게시글이 삭제되지 않았습니다.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public void createComment(String tableName, Comment comment, String sequence) {
		// 시퀀스.nextval(
		try {
			connect();
			String sql = "INSERT INTO ? VALUES (?, " + sequence +", ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			pstmt.setString(2, comment.getMemberId());
			pstmt.setString(3, comment.getComment());
			
			int result = pstmt.executeUpdate();
			if (result > 0 ) {
				System.out.println("정상적으로 댓글이 작성되었습니다.");
			} else {
				System.out.println("정상적으로 댓글이 작성되지 않았습니다.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
	}
	
	// 해당 글에 있는 댓글 전체 조회
	public List<Comment> showAllComment(String tableName, int textNum) {
		List<Comment> list =  new ArrayList<>();
		try {
			connect();
			String sql = "SELECT * FROM ? WHERE text_num = ? ORDER BY comment_num";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Comment comment = new Comment();
				comment.setComment(rs.getString("comment"));
				comment.setCommentNum(rs.getInt("comment_num"));
				comment.setMemberId(rs.getString("member_id"));
				comment.setTextNum(rs.getInt("text_num"));
				
				list.add(comment);
				
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		return list;
	}
	
	public Comment showOneComment(String tableName, int commentNum) {
		Comment comment = null;
		try {
			connect();
			String sql = "SELECT * FROM ? WHERE comment_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			pstmt.setInt(2, commentNum);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				comment.setComment(rs.getString("comment"));
				comment.setCommentNum(rs.getInt("comment_num"));
				comment.setMemberId(rs.getString("member_id"));
				comment.setTextNum(rs.getInt("text_num"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return comment;
	}
	
		
	public void updateComment(String tableName, Comment comment, String newComment) {
		try {
			connect();
			String sql = "UPDATE INTO ? (comment) VALUES (?) WHERE text_num = ? AND comment_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			pstmt.setString(2, newComment);
			pstmt.setInt(3, comment.getTextNum());
			pstmt.setInt(4, comment.getCommentNum());
			
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("댓글이 정상적으로 업데이트 되었습니다.");
			} else {
				System.out.println("댓글이 정상적으로 업데이트 되지 않았습니다.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		// 특정 text_num안에 있는 comment_num을 들고와서
		// 해결한다.
		// management에서 그 유저가 작성한 글인지 확인해서 그 유저가 작성한 글일 경우
		// update가 일어날 수 있도록 해야한다/
	}
	  // 테이블 한 로우에 특정 컬럼값이 부족하다(null)이다
	// dao의 어느 메소드에 매개변수로 그 객체를 가져와서 사용하는 경우에
	// 
	public void deleteComment(String tableName, Comment comment) {
		try {
			connect();
			String sql = "UPDATE INTO ? (comment) VALUES (?) WHERE text_num = ? AND comment_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			pstmt.setString(2, comment.getComment());
			pstmt.setInt(3, comment.getTextNum());
			pstmt.setInt(4, comment.getCommentNum());
			
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("댓글이 정상적으로 업데이트 되었습니다.");
			} else {
				System.out.println("댓글이 정상적으로 업데이트 되지 않았습니다.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		// 특정 text_num안에 있는 comment_num을 들고와서
		// 해결한다.
		// management에서 그 유저가 작성한 글인지 확인해서 그 유저가 작성한 글일 경우
		// update나 delete가 일어날 수 있도록 해야한다/
	}
			
}
