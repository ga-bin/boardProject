package VO;

public class Comment {
	private int textNum;
	private int commentNum;
	private String memberId;
	private String comment;

	
	public int getTextNum() {
		return textNum;
	}
	public void setTextNum(int textNum) {
		this.textNum = textNum;
	}
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "게시글번호 : " + textNum + ", 댓글 번호 : " + commentNum + ", id : " + memberId + ", 내용 : "
				+ comment;
	}
	
	
}
