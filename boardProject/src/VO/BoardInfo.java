package VO;

public class BoardInfo {
	String boardName;
	int boardNum;
	int usAble;
	
	
	public int getUsAble() {
		return usAble;
	}
	public void setUsAble(int usAble) {
		this.usAble = usAble;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	
	@Override
	public String toString() {
		String str = "";
		str += "게시판 번호 : " + boardNum + "게시판 이름 : " + boardName;
		if (usAble == 0) {
			str += " 사용가능";
		} else {
			str += " 사용불가능";
		}
		return str;
	}
	
}
