package management;

import java.sql.SQLException;
import java.util.Scanner;

import DAO.MemberDAO;
import VO.LoginMember;
import VO.Member;

public class LoginControl {
	Scanner sc = new Scanner(System.in);
	MemberDAO mDAO = MemberDAO.getInstance();

	public void run() {
		menuPrint();
		while (true) {
			int selectNum = menuSelect();
			if (selectNum == 1) {
				memberShip();
			} else if (selectNum == 2) {
				login();
			} else if (selectNum == 3) {
				AnonymousManagement anonymousManagement = new AnonymousManagement();
				anonymousManagement.run();
			} else if (selectNum == 4) {
				logOut();
			}
			else if (selectNum == 9) {
				exit();
				break;
			} else {
				showInputError();
			}
		}
	}

	public void menuPrint() {
		System.out.println("=============================================================");
		System.out.println("1.회원가입     2. 로그인    3. 익명게시판   4. 로그 아웃     9. 프로그램종료");
		System.out.println("==============================================================");
	}

	public int menuSelect() {
		int selectNum = 0;	
		System.out.println("메뉴를 입력하세요");
		try {
			selectNum = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("숫자 형식으로 입력하세요");
		}
		return selectNum;
	}

	public void exit() {
		System.out.println("프로그램을 종료합니다.");
	}

	public void showInputError() {
		System.out.println("메뉴에 있는 숫자를 입력하세요");
	}

	// 회원가입
	// 만약에 기존의 멤버 id가 존재하면 회원가입 못하게 막기
	// 만약 pwd가 12자리 이하면 회원가입 못하게 막기

	public void memberShip() {
		System.out.println("회원가입 정보를 입력해 주세요");
		Member member = inputInfo();
		if (mDAO.showMember(member.getMemberId()) == null) {
			mDAO.inputMember(member);
		} else {
			System.out.println("같은아이디로 등록된 회원이 있습니다. 다른 아이디를 입력해주세요.");
		}
	}

	public Member inputInfo() {
		Member member = new Member();
		System.out.println("아이디>");
		member.setMemberId(sc.nextLine());
		System.out.println("비밀번호>");
		member.setMemberPwd(sc.nextLine());

		return member;
	}

	public LoginMember login() {
		LoginMember loginMember = null;
		Member member = inputInfo();
		if (mDAO.showMember(member.getMemberId()) == null) {
			System.out.println("등록된 id가 아닙니다.");
			// 입력받은 비밀번호와 객체에 있는 비밀번호가 다를때 0
		} else if (member.getMemberPwd().equals(mDAO.showMember(member.getMemberId()).getMemberPwd())) {
			System.out.println("로그인 성공");
			loginMember = LoginMember.getInstance();
			loginMember.setMemberId(member.getMemberId());
			loginMember.setMemberPwd(member.getMemberPwd());
			loginMember.setExit(0);
			if (member.getMemberId().equals("admin")) {
				loginMember.setRole(0);
				AdminManagement adminManagement = new AdminManagement();
				adminManagement.run();
			} else {
				loginMember.setRole(1);
				UserManagement userManagement = new UserManagement();
				userManagement.run();
			}

		} else {
			System.out.println("비밀번호가 일치하지 않습니다.");
		}

		return loginMember;
	}
	
	public void logOut() {
		System.out.println("로그아웃합니다.");
		LoginMember loginMember = LoginMember.getInstance();
		// loginMember의 객체를 가지고 와서 초기화시켜야 한다.
		loginMember.setMemberId(null);
		loginMember.setMemberPwd(null);
	}
}
