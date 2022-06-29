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
				board.setUsAble(rs.getInt("usable"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return board;
	}
	
	// 
	public String createBoardNameUnderbar(String names) {
		String[] list = names.split(" ");
		String name = list[0];
		for (int i = 1 ; i < list.length; i++) {
			name += "_" + list[i];
		}
		return name;
	}
	
	public String createCommentBoardName(String tableName) {
		String[] list = tableName.split(" ");
		String commentBoardName = list[0] + "_" + list[1] + "_COMMENT_BOARD";
		return commentBoardName;
	}
	
	public String createSequenceName(String tableName) {
		String[] list = tableName.split(" ");

		String sequenceName = "";
		for (int i = 0; i < 3; i++) {
			sequenceName += list[i] + "_";
		}
		return sequenceName + "_SEQ";
	}
	
	public BoardInfo selectBoard(String tableName) {
		BoardInfo board = null;
		String boardName = createBoardNameUnderbar(tableName);
		try {
			connect();
			String sql = "SELECT * FROM boardInfo WHERE board_name = '" + boardName + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				board = new BoardInfo();
				board.setUsAble(rs.getInt("usable"));
				String names = rs.getString("board_name");	
				String name = createBoardNameUnderbar(names);
				board.setBoardName(name);
				System.out.println(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return board;
	}

	public void createContentTable(String tableName) {
		try {
			connect();
			String sql = "CREATE table " + tableName + "_content_board" + "    (text_num number,"
					+ "    member_id varchar2(100)," + "    title varchar2(1000)," + "    content varchar2(1000),"
					+ "		constraint " + tableName + "_content_board_pk primary key(text_num)" + ")";
			stmt = conn.createStatement();
			boolean result = stmt.execute(sql);
			System.out.println("게시판 테이블 생성 성공");
		} catch (SQLException e) {
			System.out.println("게시판 테이블 생성 실패");
		} finally {
			disconnect();
		}
	}

	public void createCommentTable(String tableName) {
		try {
			connect();
			String sql = "CREATE table " + tableName
					+ "_comment_board (text_num number, comment_num number, title varchar2(1000), comment_text varchar2(1000),"
					+ "foreign key(comment_num) references " + tableName + "_content_board(text_num)" + ")";
			stmt = conn.createStatement();
			boolean result = stmt.execute(sql);
			System.out.println("댓글 테이블 생성 성공");
		} catch (SQLException e) {
			System.out.println("댓글 테이블 생성 실패");
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
		String[] names = tableName.split(" ");
		String boardName = names[0];
		for (int i = 1; i < names.length; i++) {
			boardName += " " + names[i];
		}
		boardName = boardName.toUpperCase();
		
		try {
			connect();
			String sql = "INSERT into boardinfo (board_name, board_number) VALUES ('" + boardName + " CONTENT BOARD" + "', boardinfo_seq.nextval)";
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				System.out.println("테이블 등록 완료");
			} else {
				System.out.println("테이블 등록 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void createSeq(String tableName, String boardType) {
		try {
			connect();
			String sql = "CREATE SEQUENCE " + tableName + "_" + boardType
					+ "_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 MAXVALUE 9999 NOCYCLE NOCACHE NOORDER";
			stmt = conn.createStatement();
			Boolean result = stmt.execute(sql);

			System.out.println("시퀀스 생성 성공");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public List<BoardInfo> showAllBoard() {
		List<BoardInfo> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT * FROM boardinfo";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				BoardInfo boardinfo = new BoardInfo();
				boardinfo.setBoardName(rs.getString("board_name"));
				boardinfo.setUsAble(rs.getInt("usable"));

				list.add(boardinfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	public BoardInfo showOneBoard(String tableName) {
		BoardInfo boardInfo = null;
		try {
			connect();
			String sql = "SELECT * FROM boardinfo WHERE board_name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				boardInfo = new BoardInfo();
				boardInfo.setBoardName(rs.getString("board_name"));
				boardInfo.setUsAble(rs.getInt("usable"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return boardInfo;
	}
	public List<BoardInfo> showUsedBoard() {
		List<BoardInfo> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT * FROM boardinfo WHERE usable = 0";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				BoardInfo boardInfo = new BoardInfo();
				boardInfo.setBoardName(rs.getString("board_name"));
				boardInfo.setUsAble(rs.getInt("usable"));

				list.add(boardInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	public void makeToggleUsedBoard(String boardName, int usAble) {
		try {
			connect();
			String sql = "UPDATE boardinfo SET usable = ? WHERE board_name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, usAble);
			pstmt.setString(2, boardName + " CONTENT BOARD");
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println(boardName + "의 사용여부가 정상적으로 변경되었습니다.");
			} else {
				System.out.println(boardName + "의 사용여부가 정상적으로 변경되지 않았습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void createContent(String tableName, Content content) {
		// 나중에 managemenct에서 createcontent를 돌릴때 시퀀스명.nextval이런식으로 구성하면 될 것 같다.
		try {
		String[] names = tableName.split(" ");
		String sequence = names[0];
		for (int i = 1; i < names.length-1; i++) {
			sequence += "_" + names[i];
		}
		sequence += "_SEQ";
			connect();
			
			String boardName = createBoardNameUnderbar(tableName);
			
			String sql = "INSERT INTO " + boardName + " VALUES (" + sequence + ".nextval, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(3, content.getContent());
			pstmt.setString(2, content.getTitle());
			pstmt.setString(1, content.getMemberId());

			int result = pstmt.executeUpdate();
			
			
			if (result > 0) {
				System.out.println("게시글이 등록되었습니다.");
			} else {
				System.out.println("게시글이 등록되지 않았습니다.");
			}
		}
		 catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
		


	public List<Content> showAllContent(String tableName) {
		String boardName = createBoardNameUnderbar(tableName);
		List<Content> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT * FROM " + boardName + " ORDER BY text_num"; 
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
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
	public Content showContentByNum(String tableName, int textNum) {
		Content content = null;
		String boardName = createBoardNameUnderbar(tableName);
		try {
			connect();
			String sql = "SELECT * FROM " + boardName + " WHERE text_num = " + textNum;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				content = new Content();
				content.setContent(rs.getString("content"));
				content.setMemberId(rs.getString("member_id"));
				content.setTextNum(rs.getInt("text_num"));
				content.setTitle(rs.getString("title"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();

		}

		return content;

	}

	public List<Content> showContentByCon(String tableName, String textContent) {
		String boardName = createBoardNameUnderbar(tableName);
		List<Content> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT * FROM " + boardName + " WHERE content Like '%" + textContent + "%'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Content content = new Content();
				content.setContent(rs.getString("content"));
				content.setMemberId(rs.getString("member_id"));
				content.setTextNum(rs.getInt("text_num"));
				content.setTitle(rs.getString("title"));

				list.add(content);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}

	public List<Content> showContentByTitle(String tableName, String title) {
		String boardName = createBoardNameUnderbar(tableName);
		List<Content> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT * FROM " + boardName + " WHERE title Like '%" + title + "%'";
	
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Content content = new Content();
				content.setContent(rs.getString("content"));
				content.setMemberId(rs.getString("member_id"));
				content.setTextNum(rs.getInt("text_num"));
				content.setTitle(rs.getString("title"));

				list.add(content);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}

	public void updateTitle(String tableName, String newTitle, int textNum) {
		String boardName = createBoardNameUnderbar(tableName);
		try {
			connect();
			String sql = "UPDATE " + boardName + " SET (title) = ? WHERE text_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newTitle);
			pstmt.setInt(2, textNum);

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("정상적으로 제목이 수정되었습니다.");
			} else {
				System.out.println("정상적으로 제목이 수정되지 않았습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void updateContent(String tableName, String newContent, int textNum) {
		String boardName = createBoardNameUnderbar(tableName);
		try {
			connect();
			String sql = "UPDATE " + boardName + " SET (content) = ? WHERE text_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newContent);
			pstmt.setInt(2, textNum);

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("정상적으로 게시글 내용이 수정되었습니다.");
			} else {
				System.out.println("정상적으로 게시글 내용이 수정되지 않았습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// management에서 updateTitle과 updatecontent로직을 동시에 돌리자
	// 제목을 수정안하려면 0을 클릭해라 이런식으로 해서

	public void deleteContent(String tableName, int textNum) {
		String boardName = createBoardNameUnderbar(tableName);
		try {
			connect();
			String sql = "DELETE FROM " + boardName + " WHERE text_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, textNum);
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("정상적으로 게시글이 삭제되었습니다.");
			} else {
				System.out.println("정상적으로 게시글이 삭제되지 않았습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void createComment(String tableName, Comment comment) {
		// 시퀀스.nextval(
		String boardName = createBoardNameUnderbar(tableName);
		String sequenceName = boardName.substring(0, 17) + "_SEQ";
		
		try {
			connect();
			String sql = "INSERT INTO " + boardName + " VALUES (?, " + sequenceName + ".nextval, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comment.getTextNum());
			pstmt.setString(2, comment.getMemberId());
			pstmt.setString(3, comment.getComment());

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("정상적으로 댓글이 작성되었습니다.");
			} else {
				System.out.println("정상적으로 댓글이 작성되지 않았습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	// 해당 글에 있는 댓글 전체 조회
	public List<Comment> showAllComment(String tableName, int textNum) {
		String boardName = createCommentBoardName(tableName);
		List<Comment> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT * FROM " + boardName + " WHERE text_num = " + textNum + " ORDER BY comment_num";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setComment(rs.getString("comment_text"));
				comment.setCommentNum(rs.getInt("comment_num"));
				comment.setMemberId(rs.getString("member_id"));
				comment.setTextNum(rs.getInt("text_num"));

				list.add(comment);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}

	public Comment showOneComment(String tableName, int commentNum) {
		String boardName = createCommentBoardName(tableName);
		Comment comment = null;
		try {
			connect();
			String sql = "SELECT * FROM " + boardName + " WHERE comment_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentNum);

			rs = pstmt.executeQuery();
			if (rs.next()) {	
				comment = new Comment();
				comment.setComment(rs.getString("comment_text"));
				comment.setCommentNum(rs.getInt("comment_num"));
				comment.setMemberId(rs.getString("member_id"));
				comment.setTextNum(rs.getInt("text_num"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return comment;
	}

	public void updateComment(String tableName, Comment comment) {
		String boardName = createCommentBoardName(tableName);
		try {
			connect();
			String sql = "UPDATE " + boardName + " SET comment_text = ? WHERE text_num = ? AND comment_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getComment());
			pstmt.setInt(2, comment.getTextNum());
			pstmt.setInt(3, comment.getCommentNum());
						
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("댓글이 정상적으로 업데이트 되었습니다.");
			} else {
				System.out.println("댓글이 정상적으로 업데이트 되지 않았습니다.");
			}
		} catch (SQLException e) {
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
	public void deleteComment(String tableName, int commentNum) {
		String boardName = createCommentBoardName(tableName);
		try {
			connect();
			String sql = "DELETE FROM "+ boardName + " WHERE comment_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentNum);

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("댓글이 정상적으로 삭제 되었습니다.");
			} else {
				System.out.println("댓글이 정상적으로 삭제 되지 않았습니다.");
			}
		} catch (SQLException e) {
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
