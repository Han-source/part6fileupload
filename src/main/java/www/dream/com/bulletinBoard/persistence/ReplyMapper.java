package www.dream.com.bulletinBoard.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.model.ReplyVO;
import www.dream.com.common.dto.Criteria;

public interface ReplyMapper {
	//LRCUD
	/**------------게시글 처리 함수 정의 영역--------------*/
	public long getTotalCount(@Param("boardId") int boardId, @Param("descrim") String descrim);
	public long getSearchTotalCount(@Param("boardId") int boardId, @Param("cri") Criteria cri);
	/* mapper 함수의 인자 개수가 여러개 일때는 필수적으로 @Param을 넣어야 한다.
	 * 이를 실수하지 않기 위하여 한개여도 그냥 명시적으로 넣어 주세요*/
	/* 초기화면 띄울때 활용 : 최종적으로는 개인화 서비스에서 초기화면으로 활용 */
	public List<PostVO> getList(@Param("boardId") int boardId, @Param("cri") Criteria cri);
	public List<PostVO> getListByHashTag(@Param("boardId") int boardId, @Param("cri") Criteria cri);
	
	/** id 값으로 Post 및 RePly of Reply 객체 조회*/
	public ReplyVO findReplyById(String id);
	
	/** id 값으로 Post 및 RePly of Reply 객체 조회한 게시물에 조회수를 1올리기*/
	public ReplyVO plusReadCnt(String id);
	
	/** 게시글 등록 */
	public int insert(@Param("board") BoardVO board, @Param("post") PostVO post);
	
	/** 게시글 수정 처리*/
	public int updatePost(PostVO post);
	
	/** id 값으로 Post 객체 삭제*/
	public int deleteReplyById(String id);
	
	/**------------댓글 처리 함수 정의 영역--------------*/
	
	public int getReplyCount(@Param("originalId") String originalId, 
			@Param("idLength") int idLength);
	
	public List<ReplyVO> getReplyListWithPaging(@Param("originalId") String originalId, 
			@Param("idLength") int idLength, @Param("cri") Criteria cri);
	/** 특정 댓글의 모든 후손 대댓글을 작성 순서에 따라 조회해준다. */
	public List<ReplyVO> getReplyListOfReply(@Param("originalId") String originalId, @Param("idLength") int idLength);
	/** id 값으로 Post객체 조회*/
	public int insertReply(@Param("originalId") String originalId, @Param("reply") ReplyVO reply);
	/** 댓글 수정처리 */
	public int updateReply(ReplyVO reply);
	public int getAllReplyCount(@Param("replyId") String replyId,  @Param("idLength") int idLength);
}
