package www.dream.com.bulletinBoard.persistence;

import java.util.List;

import www.dream.com.bulletinBoard.model.BoardVO;

public interface BoardMapper {
	//LRCUD
	/** 전체 게시판 목록을 조회 */
	public List<BoardVO> getList();
	
	/** 특정 게시판 목록을 조회 */
	public BoardVO getBoard(int id);
	
	
}
