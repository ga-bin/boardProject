package management;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import DAO.BoardDAO;
import VO.BoardInfo;
import VO.Content;
import VO.LoginMember;

public class Management {
	
	BoardDAO bDAO = BoardDAO.getInstance();
	Scanner sc = new Scanner(System.in);
	String boardName;
	LoginMember loginMember = LoginMember.getInstance();
	
	protected String createTableNameByNum() {
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
			return createTableNameByNum();
		} 
		return boardName;
	}
	
	
	// 나중에 테이블을 관리자가 개설하고 나면 else if문으로 테이블 이름을 받는 것이 아니라
	// 자동으로 테이블 이름을 받도록 할 수는 없을지 생각해 보기
	// 디비에 저장된 테이블 번호가 있을 것이고 그 번호를 매개변수로 받아서 그 테이블을 조회하는 
	// selectBoard 메소드 만들고
	// 만든 메소드를 바탕으로 selectBoard메소드를 여기서 실행해서 그 이름을 가지고 와서
	// return 하는 방식으로 하면 될 것 같은데
	// selectTable메소드를 어디에 만들어야하는지 고민을 해보자
	// 완성!!!!
	
	// sequence 만들기
	protected String createContentSeqName() {
		String sequence = "";
		
		return sequence;
	}
	
	protected String createCommentSeqName() {
		String sequence = "";
		
		return sequence;
	}
	protected void createContent() {
		Content content = null;
		content.setMemberId(loginMember.getMemberId());
		System.out.println("제목을 입력하세요");
		content.setTitle(sc.nextLine());
		System.out.println("내용을 입력하세요");
		content.setContent(sc.nextLine());
		
		bDAO.createContent(boardName, content, boardName);
	}
	
	
	protected void showContentByNum() {
		System.out.println("번호를 입력하세요");
		int selectNum = inputNum();
		
		Content content = bDAO.showContentByNum(boardName, selectNum);
		System.out.println(content);
		
	}
	
	protected void showContentByCon() {
		System.out.println("내용의 일부를 입력하세요");
		String contentText = sc.nextLine();
		if (bDAO.showContentByCon(boardName, contentText) == null) {
			System.out.println("작성된 글이 없습니다.");
		}
		List<Content> list = bDAO.showContentByCon(boardName, contentText);
		for (Content content : list) {
			System.out.println(content);
		}
		
	}
	
	protected void showContentByTitle() {
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
	
	
	protected void updateTitle() {
		System.out.println("수정할 글의 번호를 입력하세요");
		int selectNum = inputNum();
		if (bDAO.showContentByNum(boardName, selectNum) == null) {
			System.out.println("작성된 글이 아닙니다.");
			return;
		}
		Content content = bDAO.showContentByNum(boardName, selectNum);
		// 작성한 글의 ID가 로그인 한 유저의 아이디와 일치하는지 확인
		// 일치하는 경우에만 수정실시
		// 일치하지 않은 경우에는 권한이 없습니다 띄우기
		if (loginMember.getMemberId() == content.getMemberId()) {
			System.out.println("바꿀 제목을 입력하세요");
			String newTitle = sc.nextLine();
			bDAO.updateTitle(boardName, newTitle, selectNum);
		}
	}
	
	protected void updateContent() {
		System.out.println("수정할 글의 번호를 입력하세요");
		int selectNum = inputNum();
		if (bDAO.showContentByNum(boardName, selectNum) == null) {
			System.out.println("작성된 글이 아닙니다.");
			return;
		}
		
		
	}
	
	protected void deleteContent() {
		
	}
	
	protected void createComment() {
		
	}
	
	protected void showComment() {
		
	}
	
	protected void updateComment() {
	
	}
	
	protected void deleteComment() {
		
	}
	
	protected int selectMenu() {
		int selectNum = 0;
		System.out.println("메뉴를 선택하세요");
		try {
			
		} catch(NumberFormatException e) {
			System.out.println("숫자 형식으로 입력해 주세요");
			return selectMenu();
		}
		return selectNum;
	}
	
	protected void exit() {
		System.out.println("프로그램을 종료합니다.");
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
 }
