package DAO;

import java.util.List;

import VO.Comment;
import VO.Content;

public interface BoardDAO {
	public void creatContent();
	public Content showContentByNum();
	public List<Content> showContentByCon();
	public List<Content> showContentByTitle();
	public void updateTitle();
	public void updateContent();
	public void deleteContent();
	
	public void createComment();
	public List<Comment> showComment();
	public void updateComment();
	public void deleteComment();
	}
