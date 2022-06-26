package management;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import DAO.BoardDAO;
import VO.BoardInfo;
import VO.Comment;
import VO.Content;
import VO.LoginMember;

public class Management {
	
	BoardDAO bDAO = BoardDAO.getInstance();
	Scanner sc = new Scanner(System.in);
	String boardName;
	LoginMember loginMember = LoginMember.getInstance();
	
	protected String selectBoard() {
		boardName = "";
		System.out.println("게시판 선택>");
		System.out.println("========================================");
		System.out.println("1. 공지사항 게시판    2. 유저게시판   3. 익명게시판");
		System.out.println("========================================");
		int selectNum = Integer.parseInt(sc.nextLine());
		try {
			BoardInfo board = bDAO.selectBoard(selectNum);
			boardName += board.getBoardName();
		} catch(NumberFormatException e) {
			System.out.println("숫자 형식으로 입력해 주세요");
			return selectBoard();
		} 
		return boardName;
	}

	
	protected void createContent() {
		findBoard();
			
		Content content = null;
		content.setMemberId(loginMember.getMemberId());
		System.out.println("제목을 입력하세요");
		content.setTitle(sc.nextLine());
		System.out.println("내용을 입력하세요");
		content.setContent(sc.nextLine());
		
		bDAO.createContent(boardName, content, boardName);
	}
	
	
	protected void showContentByNum() {
		findBoard();
		
		System.out.println("번호를 입력하세요");
		int contentNum = inputNum();
		if (bDAO.selectBoard(contentNum) == null) {
			System.out.println("등록된 게시글이 아닙니다. 다시 입력하세요");
			showContentByNum();
		}
		Content content = bDAO.showContentByNum(boardName, contentNum);
		System.out.println(content);
		
	}
	
	protected void showContentByCon() {
		findBoard();
		System.out.println("내용의 일부를 입력하세요");
		String contentText = sc.nextLine();
		if (bDAO.showContentByCon(boardName, contentText) == null) {
			System.out.println("등록된 게시글이 아닙니다. 다시 입력하세요");
			showContentByCon();
		}
		List<Content> list = bDAO.showContentByCon(boardName, contentText);
		for (Content content : list) {
			System.out.println(content);
		}
		
	}
	
	protected void showContentByTitle() {
		findBoard();
		System.out.println("제목의 일부를 입력하세요");
		String titleText = sc.nextLine();
		if (bDAO.showContentByTitle(boardName, titleText) == null) {
			System.out.println("작성된 글이 없습니다.");
			return;
		}
		List<Content> list = bDAO.showContentByTitle(boardName, titleText);
		for (Content content : list) {
			System.out.println(content);
		}
	}
	
	
	protected void updateTitleOrContent() {
		findBoard();
		
		System.out.println("수정할 글의 번호를 입력하세요");
		int contentNum = Integer.parseInt(sc.nextLine());
		Content content = bDAO.showContentByNum(boardName, contentNum);
		
		if (content == null) {
			System.out.println("작성된 글이 아닙니다.");
			updateTitleOrContent();
		}
		
		if (content.getMemberId() != loginMember.getMemberId()) {
			System.out.println("권한이 없습니다.");
			updateTitleOrContent();
		}
		
		System.out.println("수정할 제목을 입력하세요(수정하지 않을 경우 0)");
		String contentText = sc.nextLine();
		
		if (!contentText.equals("0")) {
			bDAO.updateTitle(boardName, sc.nextLine(), contentNum);
		} 
		
		System.out.println("수정할 게시글 내용을 입력하세요(수정하지 않을 경우 0)");
		String commentText = sc.nextLine();
		
		if (!commentText.equals("0")) {
		bDAO.updateContent(boardName, sc.nextLine(), contentNum);
		}
	}
	
	
	
	protected void deleteContent() {
		findBoard();
		
		System.out.println("삭제할 글의 번호를 입력하세요");
		int contenNum = Integer.parseInt(sc.nextLine());
		Content content = bDAO.showContentByNum(boardName, contenNum);
		if (content == null) {
			System.out.println("작성된 글이 아닙니다.");
			deleteContent();
		} 
		if (content.getMemberId() != loginMember.getMemberId()) {
			System.out.println("삭제할 권한이 없습니다.");
		} 
		
	}
	
	protected void createComment() {
		findBoard();
		// 게시글 번호를 입력받아서
		// 그 게시글이 있는 게시글인지 아닌지 확인하고
		// 있는 게시글인 경우에는 작성할 댓글을 입력해라
		// 그래서 createComment메소드 실행시키면 된다.
		System.out.println("댓글을 달 게시글 번호를 입력하세요");
		int contentNum = Integer.parseInt(sc.nextLine());
		if (bDAO.showContentByNum(boardName, contentNum) == null) {
			System.out.println("작성된 글이 아닙니다.");
			createComment();
		}
		
		
		System.out.println("작성할 댓글 내용을 입력하세요");
		// 나중에 엔터쳐도 바로 등록안되고 특정글자 눌러야 입력되도록 처리해보자
		Comment comment = inputComment();
		comment.setTextNum(contentNum);
		
		// comment안에 내용 입력받아서 넣기
		
		bDAO.createComment(createCommentBoardName(), comment, boardName+"_comment_seq.nextval()");
		// 게시글 테이블은 boardinfo안에 들어있어서 select문을 통해 가지고 온 selectboard메소드로 가져올 수 있다
		// 그러면 boardinfo 테이블에 없는 댓글 테이블은 어떤 방식으로 가져와야 하는거지?
		
		// 컬럼을 추가해 댓글 테이블도 넣는다
		// 그럴경우 boardinfo에 카디션 프로덕트가 발생할 테니 댓글 테이블을 담을 테이블 하나를 더 만들어서
		// selectboard메소드를 하나 더 만들어서 돌린다.
		
		// 해결방법 더깔끔한 것은 없나
		// boardName에서 가지고 온 특정 문자열을 자르고 거기에 comment_board를 붙혀서 반환하는 메소드를 만든다
		// 그것을 createComment의 첫번째 인자로 넣어준다.
	}
	
	
	
	protected int showComment() {
		findBoard();
		System.out.println("댓글을 볼 게시글 번호를 입력하세요.");
		int contentNum = Integer.parseInt(sc.nextLine());
		if (bDAO.showContentByNum(boardName, contentNum) == null) {
			System.out.println("작성된 게시글이 아닙니다.");
			return showComment();
		} 
		
		List<Comment> list = bDAO.showAllComment(boardName, contentNum);
		for (Comment comment : list) {
			System.out.println(comment);
		}
		return contentNum;
	}
	
	protected void updateComment() {
		findBoard();
		int contentNum = showComment();
		// contentNum을 바탕으로 거기에 작성된 댓글 전부를 보여주는 메소드
		System.out.println("수정할 댓글 번호를 입력하세요");
		int commentNum = Integer.parseInt(sc.nextLine());
		
		// 만약 그 번호에 해당하는 댓글이 없으면
		// 해당하는 댓글이 없습니다 하고 다시 메소드 호출
		if (bDAO.showAllComment(createCommentBoardName(), contentNum).size() == 0) {
			System.out.println("해당하는 댓글이 없습니다.");
			updateComment();
		}
		
		Comment comment = inputComment();
		comment.setTextNum(contentNum);
		
		// 만약 그 번호에 해당하는 댓글이 있으면
		// 댓글의 작성자가 loginId 와 같은지 확인하기
		if (loginMember.getMemberId() == bDAO.showOneComment(createCommentBoardName(), commentNum).getMemberId()) {
			System.out.println("수정할 댓글 내용을 입력하세요");
			bDAO.updateComment(createCommentBoardName(), comment, sc.nextLine());
		} else {
			System.out.println("수정권한이 없습니다.");
		}
		
		
		
	}
	
	protected void deleteComment() {
		findBoard();
		// 해당 게시글에 대한 전체 댓글을 출력해야한다.
		// 그 출력한 댓글을 바탕으로 유저가 번호를 선택하게 하기
		int contentNum = showComment();
		System.out.println("수정할 댓글 번호를 입력하세요");
		int commentNum = Integer.parseInt(sc.nextLine());
		
		// 만약 그 번호에 해당하는 댓글이 있으면
		// 댓글의 작성자가 loginId와 같은지 확인하기
		// 만약에 같을 경우에는 삭제하는 메소드를 실행하고
		// 아닐 경우에는 삭제 권한이 없습니다. 출력하기
		if (bDAO.showOneComment(boardName, commentNum) == null) {
			System.out.println("해당하는 댓글이 없습니다.");
			deleteComment();
		}

		if (loginMember.getMemberId() == bDAO.showOneComment(boardName, commentNum).getMemberId()) {
			Comment comment = inputComment();
			comment.setTextNum(contentNum);
			bDAO.deleteComment(boardName, comment);
		} else {
			System.out.println("삭제 권한이 없습니다.");
		}
	}
	
	// 테이블을 생성하는것까지는 좋은데 걔들의 management는 어떤식으로
	// 해야하는가?
	// 그냥 management객체를 갈아 끼워야하나 usermanagement랑 adminmanagement로
	// 고민을 해보자 
	// 어차피 유저들한테 접근권한을 다르게 주는것이라서
	// 
	
	
	protected int selectMenu() {
		int selectNum = 0;
		System.out.println("메뉴를 선택하세요");
		try {
			selectNum = Integer.parseInt(sc.nextLine());
		} catch(NumberFormatException e) {
			System.out.println("숫자 형식으로 입력해 주세요");
			return selectMenu();
		}
		return selectNum;
	}
	
	protected void back() {
		System.out.println("뒤로갑니다.");
	}
	
	protected void showInputError() {
		System.out.println("메뉴에 있는 숫자를 입력해 주세요");
	}
	
	protected int inputNum() {
		int selectNum = 0;
		try {
			selectNum = Integer.parseInt(sc.nextLine());
		} catch(NumberFormatException e) {
			System.out.println("숫자 형식으로 입력해 주세요");
		}
		return selectNum;
	}
	
	protected void findBoard() {
		if (bDAO.selectBoard(boardName) == null) {
			System.out.println("게시판이 없습니다.");
			return;
		}
	}
	
	protected Comment inputComment() {
		Comment comment = new Comment();
		System.out.println("게시글 내용을 입력하세요");
		comment.setComment(sc.nextLine());
		comment.setMemberId(loginMember.getMemberId());
		
		return comment;
	}
	
	protected String createCommentBoardName() {
		String[] list = boardName.split("_");
		return list[0] + "_comment_board";
	}
	
 }
