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

	// 메뉴프린트
	public static void menuPrint() {
		System.out.println("=============================================================");
		System.out.println("1.회원가입     2. 로그인    3. 익명유저   4. 로그 아웃     9. 프로그램종료");
		System.out.println("==============================================================");
	}
	
	// 메뉴선택
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

	// 프로그램종료
	public void exit() {
		System.out.println("프로그램을 종료합니다.");
	}

	// 입력오류
	public void showInputError() {
		System.out.println("메뉴에 있는 숫자를 입력하세요");
	}

	// 회원가입
	// 만약에 기존의 멤버 id가 존재하면 회원가입 못하게 막기
	public void memberShip() {
		System.out.println("회원가입 정보를 입력해 주세요");
		Member member = inputInfo();
		if (mDAO.showMember(member.getMemberId()) == null) {
			mDAO.inputMember(member);
		} else {
			System.out.println("같은아이디로 등록된 회원이 있습니다. 다른 아이디를 입력해주세요.");
		}
	}

	
	// 로그인
	public LoginMember login() {
		LoginMember loginMember = null;
		// 아이디, 비밀번호 입력받기
		Member member = inputInfo();
		// 입력받은 ID로 멤버조회했을 떄 없을 경우 등록된 ID가 아니라고 띄우기
		if (mDAO.showMember(member.getMemberId()) == null) {
			System.out.println("등록된 id가 아닙니다.");
			// 아이디를 바탕으로 멤버를 조회해서 등록된 비번과 입력한 비번이 같은지 비교
		} else if (member.getMemberPwd().equals(mDAO.showMember(member.getMemberId()).getMemberPwd())) {
			// 같은 경우 로그인 성공시키고
			// loginMember 객체에 정보 담기
			System.out.println("로그인 성공");
			loginMember = LoginMember.getInstance();
			loginMember.setMemberId(member.getMemberId());
			loginMember.setMemberPwd(member.getMemberPwd());
			loginMember.setExit(0);
			// exit는 로그인 중이면 0 로그인 중 아니면 1
			if (member.getMemberId().equals("admin")) {
				// 아이디가 관리자인 경우 role 0, 일반 유저인 경우 1
				loginMember.setRole(0);
				AdminManagement adminManagement = new AdminManagement();
				adminManagement.run();
			} else {
				loginMember.setRole(1);
				UserManagement userManagement = new UserManagement();
				userManagement.run();
			}

		} else {
			// 입력받은 비밀번호와 객체에 있는 비밀번호가 다를경우
			System.out.println("비밀번호가 일치하지 않습니다.");
		}

		return loginMember;
	}
	
	// 회원가입, 로그인 시 정보 입력
		public Member inputInfo() {
			Member member = new Member();
			System.out.println("아이디>");
			member.setMemberId(sc.nextLine());
			System.out.println("비밀번호>");
			member.setMemberPwd(sc.nextLine());

			return member;
		}
	
	public void logOut() {
		System.out.println("로그아웃합니다.");
		LoginMember loginMember = LoginMember.getInstance();
		// loginMember의 객체를 가지고 와서 초기화시켜야 한다.
		loginMember.setMemberId(null);
		loginMember.setMemberPwd(null);
	}
}
