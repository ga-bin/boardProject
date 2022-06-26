package management;

import java.util.List;

import VO.BoardInfo;

public class AdminManagement extends Management {

	public void run() {
		menuPrint();
		while (true){
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
				new loginControl();
				return;
			} else {
				showInputError();
				return;
			}

		}
	}

	private void menuPrint() {
		System.out.println("===================================================");
		System.out.println("1. 게시판 관리    2. 게시글 관리    3. 댓글 관리    9. 뒤로가기");
		System.out.println("===================================================");
	}

	private void boardMenuPrint() {
		System.out.println("========================================================");
		System.out.println("1. 게시판 전체 조회   2. 사용 가능한 게시판 조회  3. 게시판 생성");
		System.out.println(" 4. 게시판 사용 가능하도록 5. 게시판 사용 불가능하도록    9. 뒤로가기");
		System.out.println("========================================================");
		boardMenuRun();
	}

	private void contentMenuPrint() {
		selectBoard();
		if (boardName.equals("")) {
			System.out.println("등록된 게시판이 아닙니다.");
		}
		System.out.println("=================================================================");
		System.out.println("1. 게시글 작성    2. 게시글 삭제   3. 글 번호로 게시글 조회  4. 제목으로 게시글 조회");
		System.out.println("5. 내용으로 게시글 조회    6. 게시글 수정     9. 뒤로가기");
		System.out.println("=================================================================");
		contentMenuRun();
	}

	private void commentMenuPrint() {
		selectBoard();
		if (boardName.equals("")) {
			System.out.println("등록된 게시판이 아닙니다.");
		}
		System.out.println("=============================================================");
		System.out.println("1. 댓글 생성   2. 댓글 수정    3. 댓글 삭제   4. 댓글 조회    9. 뒤로가기");
		System.out.println("=============================================================");
		commentMenuRun();
	}

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

	private void contentMenuRun() {
		while (true) {
			System.out.println("메뉴를 입력하세요");
			int selectNum = selectMenu();
			if (selectNum == 1) {
				createContent();
			} else if (selectNum == 2) {
				deleteContent();
			} else if (selectNum == 3) {
				showContentByNum();
			} else if (selectNum == 4) {
				showContentByTitle();
			} else if (selectNum == 5) {
				showContentByCon();
			} else if (selectNum == 6) {
				updateTitleOrContent();
			} else if (selectNum == 9) {
				back();
				return;
			} else {
				showInputError();
				return;
			}
		}
	}

	private void commentMenuRun() {
		while (true) {
			System.out.println("메뉴를 입력하세요");
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
				return;
			} else {
				showInputError();
				return;
			}
		}
	}

	private void createBoard() {
		System.out.println("생성할 게시판 이름을 입력하세요");
		boardName = sc.nextLine();
		// 유저들이 선택할 수 있는 게시판이 바뀔 수도 있다
		// 뒤로가기 등을 통해서 유저들이 다른 게시판을 선택하고나면
		// 그 게시판의 이름이 boardName에 들어갈 수 있도록 selectBoard가 실행되어야 한다
		// 그러면 createBoardNameByNum과 selectBoard는 어떻게 분리되어야 하는가
		if (bDAO.selectBoard(boardName+"_user_content_board") != null
				&& bDAO.selectBoard(boardName +"_anonymous_content_board") != null
				&& boardName != "notice_content_board") {
			System.out.println("이미 있는 게시판입니다. 다른 이름을 입력해주세요");
			createBoard();
		}
		System.out.println("생성할 게시판 유형을 입력해주세요");
		System.out.println("=======================");
		System.out.println("1. 유저게시판    2. 익명게시판");
		System.out.println("=======================");
		int selectNum = inputNum();
		if (selectNum == 1) {
			boardName = boardName += "_user";
		} else if (selectNum == 2) {
			boardName = boardName += "_anonymous";
		}
		System.out.println(boardName);
		bDAO.createContentTable(boardName);
		bDAO.createCommentTable(boardName);
		bDAO.insertTableToBoardInfo(boardName);
		bDAO.createSeq(boardName, "content");
		bDAO.createSeq(boardName, "comment");
		
	}

	private void showAllBoard() {
		System.out.println("전체 게시판을 조회합니다.");
		List<BoardInfo> list = bDAO.showAllBoard();
		for (BoardInfo boardInfo : list) {
			System.out.println(boardInfo);
		}
	}

	private void showUsedBoard() {
		System.out.println("사용가능한 게시판을 조회합니다.");
		List<BoardInfo> list = bDAO.showUsedBoard();
		for (BoardInfo boardInfo : list) {
			System.out.println(boardInfo);
		}
	}

	private void makeUnusedBoard() {
		System.out.println("사용 중지할 게시판 이름을 입력하세요");
		boardName = sc.nextLine();

		if (bDAO.selectBoard(boardName) == null) {
			System.out.println("등록된 게시판이 아닙니다.");
			makeUnusedBoard();
		}

		bDAO.makeToggleUsedBoard(boardName, 1);
		boardName = ""; // 다른 기능들에 접근권한을 다 막기 위해서
	}

	private void makeUsedBoard() {
		System.out.println("사용 가능하게 할 게시판 이름을 입력하세요");
		boardName = sc.nextLine();

		if (bDAO.selectBoard(boardName) == null) {
			System.out.println("등록된 게시판이 아닙니다.");
			makeUsedBoard();
		}
		bDAO.makeToggleUsedBoard(boardName, 0);

	}

}
