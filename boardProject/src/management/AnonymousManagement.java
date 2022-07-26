package management;

public class AnonymousManagement extends Management {
	
	// AnoymousManagement가 실행되었을 때 강제로 익명 유저를 anony로 로그인
	public AnonymousManagement() {
		loginMember.setMemberId("anony");
		loginMember.setMemberPwd("anony");

	}

	public void run() {
		while (true) {
			menuPrint();
			int selectNum = selectMenu();
			boolean accessible = blockAnonyUser();
			if (!accessible) {
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

	private void menuPrint() {
		System.out.println("=====================================");
		System.out.println("1. 게시글 관리   2. 댓글 관리    9. 뒤로 가기");
		System.out.println("=====================================");
	}

	private boolean blockAnonyUser() {
		blockUnusedBoard();
		if (boardName.contains("USER") || boardName.contains("NOTICE") && loginMember.getMemberId() == "anony") {
			System.out.println("로그인 하지 않은 회원은 접근 권한이 없습니다.");
			return false;
		}

		if (boardName.equals("")) {
			System.out.println("등록된 게시판이 아닙니다.");
			return false;
		}
		return true;

	}

	private void contentMenuPrint() {
		System.out.println("====================================================================");
		System.out.println("1. 게시글 작성    2. 게시글 삭제  3. 게시글 전체 조회   4. 글 번호로 게시글 조회");
		System.out.println("5. 제목으로 게시글 조회    6. 내용으로 게시글 조회    7. 게시글 수정     9. 뒤로가기");
		System.out.println("====================================================================");
		contentMenuRun();
	}

	private void commentMenuPrint() {
		if (boardName.equals("")) {
			System.out.println("등록된 게시판이 아닙니다.");
			return;
		}
		showAllContent();

		System.out.println("=============================================================");
		System.out.println("1. 댓글 생성   2. 댓글 수정    3. 댓글 삭제   4. 댓글 조회    9. 뒤로가기");
		System.out.println("=============================================================");
		commentMenuRun();
	}

	private void contentMenuRun() {
		while (true) {
			int selectNum = selectMenu();
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
