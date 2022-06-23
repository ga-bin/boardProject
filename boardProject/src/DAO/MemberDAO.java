package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import VO.Member;

public class MemberDAO extends DAO {
	
	// 싱글톤
	private static MemberDAO mDAO = null;
	
	private MemberDAO(){
		
	}
	
	public static MemberDAO getInstance() {
		if (mDAO == null) {
			mDAO = new MemberDAO();
		}
		return mDAO;
	}
	
	
	// 회원가입
	public void inputMember(Member member) {
		try {
			connect();
			String sql = "INSERT INTO member (member_id, member_pwd, exit) VALUES (?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setInt(3, 0); 
			
			int result = pstmt.executeUpdate();
			
			if (result > 0) {
				System.out.println("정상적으로 회원가입되었습니다.");
			} else {
				System.out.println("회원가입되지 않았습니다.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		// management에서
		// 만약에 기존의 멤버 id가 존재하면 회원가입 못하게 막기
		// 만약 pwd가 12자리 이하면 회원가입 못하게 막기
		// 
	}
	
	
	public Member showMember(String memberId) {
		Member member = null;
		try {
			connect();
			String sql = "SELECT * FROM member WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new Member();
				member.setMemberId(rs.getString("member_id"));
				member.setMemberPwd(rs.getString("member_pwd"));
				member.setRole(rs.getInt("member_role"));
				member.setExit(rs.getInt("exit"));
			
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		return member;
	}
	
	public List<Member> showAllMember() {
		List<Member> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT * FROM member WHERE exit = ? ORDER BY member_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0);
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Member member = new Member();
				member.setMemberId(rs.getString("member_id"));
				member.setMemberPwd(rs.getString("member_pwd"));
				member.setRole(rs.getInt("member_role"));
				member.setExit(rs.getInt("exit"));
				
				list.add(member);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	public List<Member> showHistory() {
		List<Member> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT * FROM member ORDER BY member_id";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Member member = new Member();
				member.setMemberId(rs.getString("member_id"));
				member.setMemberPwd(rs.getString("member_pwd"));
				member.setRole(rs.getInt("member_role"));
				member.setExit(rs.getInt("exit"));
				
				list.add(member);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	// 회원 정보 수정
	// management에서 아이디, 패스워드 일치하는지 확인
	// 일치하는 경우 기존 아이디를 인자로 받아와서 where절에 셋팅
	// 외부에서 새로 값을 입력받아서 새로운 member객체 생성하고 인자로 넣기
	public void updateInfo(Member member, String memberId) {
		try {
			connect();
			String sql = "UPDATE member id = ?, member_pwd = ? WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setString(3, memberId);
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("정상적으로 업데이트 되었습니다.");
			} else {
				System.out.println("정상적으로 업데이트 되지 않았습니다.");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	// 회원 정보 삭제(탈퇴)
	// management에서 아이디 비번 입력받아서 일치하면
	// delete문 실행
	public void deleteInfo(String memberId) {
		try {
			connect();
			String sql = "DELETE FROM member WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("정상적으로 삭제되었습니다.");
			} else {
				System.out.println("정상적으로 삭제되지 않았습니다.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	
}
