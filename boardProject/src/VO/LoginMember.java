package VO;

public class LoginMember {
	String memberId;
	String memberPwd;
	int role;
	int exit;
	
	public String getMemberId() {
		return memberId;
	}
	public int getExit() {
		return exit;
	}
	public void setExit(int exit) {
		this.exit = exit;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPwd() {
		return memberPwd;
	}
	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}

}
