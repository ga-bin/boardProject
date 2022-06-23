package VO;

public class Member {
	String memberId;
	String memberPwd;
	int role;
	int exit;
	public String getMemberId() {
		return memberId;
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
	public int getExit() {
		return exit;
	}
	public void setExit(int exit) {
		this.exit = exit;
	}
	@Override
	public String toString() {
		String comment = "";
		comment += "id : " + memberId + "password : " + memberPwd + "등급";
		comment += (role == 0)? "관리자" : "유저";
		comment += (exit == 0)? "회원" : "탈퇴";
		return comment;
	}
}
