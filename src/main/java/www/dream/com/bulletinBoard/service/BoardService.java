package www.dream.com.bulletinBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.persistence.BoardMapper;

@Service
public class BoardService {
	  @Autowired
	   private BoardMapper boardMapper;
	  
	  public List<BoardVO> getList(){
		  return boardMapper.getList();
	  }
	  
	  public BoardVO getBoard(int id){
		  return boardMapper.getBoard(id);
	  }
}
