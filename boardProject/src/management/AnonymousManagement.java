package management;

public class AnonymousManagement extends Management {
	
	public AnonymousManagement() {
		while (true) {
			menuPrint();
			int selectNum = selectMenu();
			if (selectNum == 1) {
				contentMenuPrint();
			} else if (selectNum == 2) {
				commentMenuPrint();
			}  else if (selectNum == 9) {
				back();
				// 뒤로가기 메소드는 다시 loginControl을 호출하는 것으로 가자
				new loginControl();
			} else {
				showInputError();
			}

		}
	}
	
	private void menuPrint() {
		System.out.println("=====================================");
		System.out.println("1. 게시글 관리   2. 댓글 관리    9. 뒤로 가기");
		System.out.println("=====================================");
	}
	
	private void contentMenuPrint() {
		selectBoard();
		if (boardName.equals("")) {
			System.out.println("등록된 게시판이 아닙니다.");
			return;
		} 
		if (boardName.contains("NOTICE")) {
			System.out.println("접근 권한이 없습니다.");
			return;
		} 
		if (boardName.contains("USER")) {
			System.out.println("접근 권한이 없습니다.");
			return;
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
			return;
		}
		if (boardName.contains("NOTICE")) {
			System.out.println("접근 권한이 없습니다.");
			return;
		}
		if (boardName.contains("USER")) {
			System.out.println("접근 권한이 없습니다.");
			return;
		}
		System.out.println("=============================================================");
		System.out.println("1. 댓글 생성   2. 댓글 수정    3. 댓글 삭제   4. 댓글 조회    9. 뒤로가기");
		System.out.println("=============================================================");
		commentMenuRun();
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

}
