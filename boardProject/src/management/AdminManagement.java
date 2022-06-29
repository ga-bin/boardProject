package management;

import java.util.List;

import VO.BoardInfo;

// 관리자 기능 관리하는 클래스
public class AdminManagement extends Management {
	
	// 관리자로 로그인 했을 때 실행
	public void run() {
		menuPrint();
		while (true) {
			int selectNum = selectMenu();
			if (selectNum == 1) {
				boardMenuPrint();
			} else if (selectNum == 2) {
				contentMenuPrint();
			} else if (selectNum == 3) {
				commentMenuPrint();
			} else if (selectNum == 9) {
				back();
				// 뒤로가기 메소드는 다시 loginControl을 호출하는 것으로 가자
				LoginControl loginControl = new LoginControl();
				loginControl.run();
			} else {
				showInputError();
				return;
			}

		}
	}
	
	// 관리자로 로그인한 경우 첫 화면
	private void menuPrint() {
		System.out.println("===================================================");
		System.out.println("1. 게시판 관리    2. 게시글 관리    3. 댓글 관리    9. 뒤로가기");
		System.out.println("===================================================");
	}
	
	// 1. 게시판 관리 선택했을 때 나오는 화면
	private void boardMenuPrint() {
		System.out.println("========================================================");
		System.out.println("1. 게시판 전체 조회   2. 사용 가능한 게시판 조회  3. 게시판 생성");
		System.out.println(" 4. 게시판 사용 가능하도록 5. 게시판 사용 불가능하도록    9. 뒤로가기");
		System.out.println("========================================================");
		boardMenuRun();
	}
	
	// 1. 게시판 관리 기능
	private void boardMenuRun() {
		while (true) {
			int selectNum = selectMenu();
			if (selectNum == 1) {
				showAllBoard();
			} else if (selectNum == 2) {
				showUsedBoard();
			} else if (selectNum == 3) {
				createBoard();
			} else if (selectNum == 4) {
				makeUsedBoard();
			} else if (selectNum == 5) {
				makeUnusedBoard();
			} else if (selectNum == 9) {
				back();
				run();
			} else {
				showInputError();
				return;
			}

		}
	}
	
	// 2. 게시글 관리 선택했을 때 나오는 화면
	private void contentMenuPrint() {
		System.out.println("====================================================================");
		System.out.println("1. 게시글 작성    2. 게시글 삭제  3. 게시글 전체 조회   4. 글 번호로 게시글 조회");
		System.out.println("5. 제목으로 게시글 조회    6. 내용으로 게시글 조회    7. 게시글 수정     9. 뒤로가기");
		System.out.println("====================================================================");
		contentMenuRun();
	}
	
	// 2. 게시글 관리 기능
	private void contentMenuRun() {
		while (true) {
			int selectNum = selectMenu();
			boolean usedBoard = blockUnusedBoard();
			if (usedBoard == false) {
				return;
			}
			if (selectNum == 1) {
				createContent();
			} else if (selectNum == 2) {
				deleteContent();
			} else if (selectNum == 3) {
				showAllContent();
			} else if (selectNum == 4) {
				showContentByNum();
			} else if (selectNum == 5) {
				showContentByTitle();
			} else if (selectNum == 6) {
				showContentByCon();
			} else if (selectNum == 7) {
				updateTitleOrContent();
			} else if (selectNum == 9) {
				back();
				run();
			} else {
				showInputError();
				return;
			}
		}
	} 
	
	
	// 3. 댓글 관리 선택했을 때 나오는 화면
	private void commentMenuPrint() {
		showAllContent();
		
		System.out.println("=============================================================");
		System.out.println("1. 댓글 생성   2. 댓글 수정    3. 댓글 삭제   4. 댓글 조회    9. 뒤로가기");
		System.out.println("=============================================================");
		commentMenuRun();
	}
	
	
	// 3. 댓글 관리 기능
	private void commentMenuRun() {
		while (true) {
			int selectNum = selectMenu();
			if (selectNum == 1) {
				createComment();
			} else if (selectNum == 2) {
				updateComment();
			} else if (selectNum == 3) {
				deleteComment();
			} else if (selectNum == 4) {
				showComment();
			} else if (selectNum == 9) {
				back();
				run();
			} else {
				showInputError();
				return;
			}
		}
	}
	
	// 1. 게시판 관리 -> 1. 전체 게시판 조회
	private void showAllBoard() {
		System.out.println("전체 게시판을 조회합니다.");
		List<BoardInfo> list = bDAO.showAllBoard();
		for (BoardInfo boardInfo : list) {
			System.out.println(boardInfo);
		}
	}

	// 1. 게시판 관리 -> 2. 사용 가능한 게시판 조회
	private void showUsedBoard() {
		System.out.println("사용가능한 게시판을 조회합니다.");
		List<BoardInfo> list = bDAO.showUsedBoard();
		for (BoardInfo boardInfo : list) {
			System.out.println(boardInfo);
		}
	}
	
	// 1. 게시판 관리 -> 3. 게시판 생성
	private void createBoard() {
		// 게시판 이름을 입력받아서
		System.out.println("생성할 게시판 이름을 입력하세요");
		boardName = sc.nextLine();
		
		// 게시판 유형을 선택받고 선택받은 유형에 따라서 _USER인지 _ANONY인지 구분
		System.out.println("생성할 게시판 유형을 입력해주세요");
		System.out.println("=======================");
		System.out.println("1. 유저게시판    2. 익명게시판");
		System.out.println("=======================");
		int selectNum = inputNum();
		if (selectNum == 1) {
			boardName = boardName += "_USER";
		} else if (selectNum == 2) {
			boardName = boardName += "_ANONY";
		}
		
		// 입력받은 boardName이 이미 등록되어 있는 게시판일 경우 생성 불가능하도록 예외처리
		if (bDAO.selectBoard(boardName + "_CONTENT_BOARD") != null
				&& boardName != "NOTICE_CONTENT_BOARD") {
			System.out.println("이미 있는 게시판입니다. 다른 이름을 입력해주세요");
			createBoard();
		}
		
		// 입력받은 boardName으로 게시글테이블, 댓글테이블, 각각의 시퀀스 생성하고
		// 새로 생성한 게시글테이블 이름 boardinfo에 등록
		bDAO.createContentTable(boardName);
		bDAO.createCommentTable(boardName);
		bDAO.createSeq(boardName, "CONTENT");
		bDAO.createSeq(boardName, "COMMENT");
		bDAO.insertTableToBoardInfo(boardName);

	}

	// 1. 게시판 관리 -> 4. 게시판 사용가능하도록
	private void makeUsedBoard() {
		System.out.println("사용 가능하게 할 게시판 이름을 입력하세요");
		boardName = sc.nextLine();
		
		// boardInfo테이블에 저장되어 있지 않은 경우 기능 사용 불가능
		if (bDAO.selectBoard(boardName) == null) {
			System.out.println("등록된 게시판이 아닙니다.");
			makeUsedBoard();
		}
		// usAble이 0이면 사용 가능
		bDAO.makeToggleUsedBoard(boardName, 0);

	}
 
	// 1. 게시판 관리 -> 5. 게시판 사용 불가능하다록
	private void makeUnusedBoard() {
		System.out.println("사용 중지할 게시판 이름을 입력하세요");
		boardName = sc.nextLine();
		
		// boardInfo테이블에 저장되어 있지 않은 경우 사용 불가능
		if (bDAO.selectBoard(boardName) == null) {
			makeUnusedBoard();
		}
		
		// usAble이 1이면 사용 불가능
		bDAO.makeToggleUsedBoard(boardName, 1);
		boardName = ""; // 다른 기능들에 접근권한을 다 막기 위해서
	}
}
