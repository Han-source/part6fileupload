package www.dream.com.bulletinBoard.control;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import www.dream.com.bulletinBoard.model.ReplyVO;
import www.dream.com.bulletinBoard.service.ReplyService;
import www.dream.com.common.dto.Criteria;
import www.dream.com.framework.util.ComparablePair;
import www.dream.com.party.model.Party;
import www.dream.com.party.model.User;

@RestController
@RequestMapping("/replies/*")
public class ReplyController {
	@Autowired
	private ReplyService replyService;
	
	//LRCUD
	/**
	 * 특정 게시물의 댓글 목록 확인
	 * @param boardId
	 * @param id
	 * @return
	 */
	
	@GetMapping(value = "count/{replyId}")
	public ResponseEntity<Integer> getCountOfReply(@PathVariable ("replyId") String replyId) {
		
		return new ResponseEntity<>(replyService.getCountOfReply(replyId), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "pages/{originalId}/{page}")
	public ResponseEntity<ComparablePair<Criteria, List<ReplyVO>>> getReplyListWithPaging(
			@PathVariable ("originalId") String originalId, 
			@PathVariable("page") int page) {
		Criteria cri =new Criteria();
		cri.setPageNumber(page);
		ComparablePair<Criteria, List<ReplyVO>> listReplyWithCount =
				replyService.getReplyListWithPaging(originalId, cri);
		return new ResponseEntity<>(listReplyWithCount, HttpStatus.OK);
	}
	
	@GetMapping(value = "pages/{replyId}")
	public ResponseEntity <List<ReplyVO>> getReplyListOfReply(
			@PathVariable("replyId") String replyId){					
		return new ResponseEntity<>(replyService.getReplyListOfReply(replyId), HttpStatus.OK);
	}
	
	/**
	 * 댓글 조회
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<ReplyVO> findReplyById(
			@PathVariable ("id") String id) {
		return new ResponseEntity<>(replyService.findReplyById(id), HttpStatus.OK);
	}
	
	/**
	 * 댓글작성
	 * @param reply
	 * @param originalId
	 * @return
	 */
	@PostMapping(value = "new/{originalId}",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> insertReply(
			@RequestBody ReplyVO reply
			,@PathVariable ("originalId") String originalId){

		Party writer = new User("ghost");
		reply.setWriter(writer);
		int insertCount = replyService.insertReply(originalId, reply);
		if (insertCount == 1) {
			return new ResponseEntity<>(reply.getId(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(
			method = {RequestMethod.PUT, RequestMethod.PATCH},
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> updateReply(
			@RequestBody ReplyVO reply){
		int updateCount = replyService.updateReply(reply);
		if (updateCount == 1) {
			return new ResponseEntity<>("수정성공", HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value = "{id}",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> deleteReplyById(
			@PathVariable ("id") String id){
		int deleteCount = replyService.deleteReplyById(id);
		if (deleteCount > 0) {
			return new ResponseEntity<>("삭제처리가 성공", HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}