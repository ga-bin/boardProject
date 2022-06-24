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
		// sequence를 만들려고하는ㄷ
		return sequence;
	}
	// sequence를 만들려고하는데
	// 테이블 이름 명 생성한 거랑 똑같이, 시퀀스를 가지고 오고 싶다.
	// 그런데 시퀀스 이름을 테이블에 저정해 놓은 것이 아니라서
	// selectBoard메소드는 사용할 수 없다
	// 그럼 어떻게 해결할 것인가
	// 테이블 이름이 뭐 일때 시퀀스는 뭐
	// 테이블 이름이 뭐 일때 시퀀스는 뭐 이런식으로 가지고 올 수 밖에 없나
	
	
	// 나중에 create문을 돌릴 때 특정이름을 받아서 시퀀스도 알아서 생성되도록 처리하자
	// 그러는 경우 create로 새 게시판을 만들때 번호를 자동적으로 부여받을 수 있을테니까
	// 그러면 지금 가지고 있는 시퀀스는 어떤 방식으로 가지고 오는가
	// 시퀀스를 조회하는 메소드를 사용해서 sql문에 셋팅하고 selectSequence를 하는 
	// 메소드를 boardDAO에 만들고 그거를 가져와서 이 메소드에 셋팅하자
	
	// 시퀀스 생성 문제점
	// 1. 테이블별로 시퀀스가 다르게 생성되는데 그걸 어떻게 처리할 것인가
	// 2. 
	
	// 해결방법
	// 1. 일단 먼저 관리자가 게시판을 생성할 때 무조건 content테이블과 comment테이블이 생성될 수 있도록
	// 메소드를 작성한다
	// 2. 생성된 content테이블과 comment테이블에 맞는 시퀀스를 만드는 함수를 따로 만든다
	// 3. 그 시퀀스명을 가지고와서 createContent에서 시퀀스.nextval이런 식으로 돌리면 된다.
	
	// 이를 위해 관리자가 게시판을 생성할 때 사용할 createBoard메소드를 dao에 만든다, 그리고 그 메소드를
	// management에서 돌린다.
	
	// 그런데 이미 생성된 시퀀스들은 어떤 방식으로 들고와야하는거지?
	// 하드코딩하자
	// 유저게시판 관리자게시판, 익명 게시판은 이미 있는 게시판들인데?
	
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
