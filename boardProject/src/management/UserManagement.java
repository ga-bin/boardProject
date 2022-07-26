package management;

// 유저로 로그인했을 때 실행되는 클래스
public class UserManagement extends Management {
	
	// 유저로 로그인했을 때 실행되는 첫 화면
	public void run() {
		while (true) {
			menuPrint();
			int selectNum = selectMenu();
			// 유저들이 접근하면 안되는 게시판에 접근하는 것을 막기
			boolean accessible = blockUser();
			if (accessible == false) {
				return;
			}
			if (selectNum == 1) {
				contentMenuPrint();
			} else if (selectNum == 2) {
				commentMenuPrint();
			} else if (selectNum == 9) {
				back();
				// 뒤로가기 메소드는 다시 loginControl을 호출하는 것으로 가자
				LoginControl loginControl = new LoginControl();
				loginControl.run();
			} else {
				showInputError();
			}

		}
	}
	
	// 유저로 로그인 했을 떄 
	private void menuPrint() {
		System.out.println("=====================================");
		System.out.println("1. 게시글 관리   2. 댓글 관리    9. 뒤로 가기");
		System.out.println("=====================================");
	}
	
	// 유저들이 접근하면 안되는 게시판에 접근할 경우 막기
	private boolean blockUser() {
		// 사용불가능한 게시판에 접근할 경우 막기
		blockUnusedBoard();
		// 유저가 등록되지 않은 게시판에 접근하는 경우 막기
		if (boardName.equals("")) {
			System.out.println("등록된 게시판이 아닙니다.");
			return false;
		}
		return true;
	}
	
	// 1. 게시글 관리 선택했을 때 출력화면
	private void contentMenuPrint() {
		System.out.println("====================================================================");
		System.out.println("1. 게시글 작성    2. 게시글 삭제  3. 게시글 전체 조회   4. 글 번호로 게시글 조회");
		System.out.println("5. 제목으로 게시글 조회    6. 내용으로 게시글 조회    7. 게시글 수정     9. 뒤로가기");
		System.out.println("====================================================================");
		contentMenuRun();
	}
	
	// 1. 게시글 관리 실행
	private void contentMenuRun() {
		while (true) {
			int selectNum = selectMenu();
			if (selectNum == 1) {
				createContent();
			} else if (selectNum == 2) {
				deleteContent();
			} else if (selectNum == 3) {
				showAllContent();
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
				run();
			} else {
				showInputError();
				return;
			}
		}
	}

	// 2. 댓글 관리 선택했을 떄 출력 화면
	private void commentMenuPrint() {
		if (boardName.equals("")) {
			System.out.println("등록된 게시판이 아닙니다.");
			return;
		}
	
		System.out.println("=============================================================");
		System.out.println("1. 댓글 생성   2. 댓글 수정    3. 댓글 삭제   4. 댓글 조회    9. 뒤로가기");
		System.out.println("=============================================================");
		commentMenuRun();
	}

	
	
	// 2. 댓글 관리 실행
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

}
