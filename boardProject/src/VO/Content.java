package VO;

public class Content {
	private int textNum;
	private String memberId;
	private String title;

	private String content;
	private String tableName;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public int getTextNum() {
		return textNum;
	}
	public void setTextNum(int textNum) {
		this.textNum = textNum;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "게시글 번호 : " + textNum + "작성자 : " + memberId +  "제목 : " + title + "내용 : " + content;
		
	}
}
