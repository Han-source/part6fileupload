package www.dream.com.bulletinBoard.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.model.ReplyVO;
import www.dream.com.bulletinBoard.persistence.ReplyMapper;
import www.dream.com.common.dto.Criteria;
import www.dream.com.framework.langPosAnalyzer.PosAnalyzer;
import www.dream.com.framework.util.StringUtil;
import www.dream.com.hashTag.model.HashtagVO;
import www.dream.com.hashTag.persistence.HashTagMapper;
/**
 * 이는 ReplyVO와 PostVO의 클래스 설계도를 기반으로
 * 해당 Table을 Top 전략으로 통합하여 만들었기에
 * ReplyMapper는 통합
 * PostService를 ReplyService와 분리 
 * @author j222_sang
 *
 */
@Service
public class PostService {

	@Autowired
	private ReplyMapper postMapper;

	@Autowired
	private HashTagMapper hashTagMapper;

	// LRCUD
	/** 페이지가 몇개 있는지 를 실행해주는 함수 */
	public long getSearchTotalCount(int boardId, Criteria cri) {
		//검색어가 있는지 조건 처리
		if (cri.hasSearching()) {
			//검색어가 있다면 getSearchTotalCount함수를 실행
			return postMapper.getSearchTotalCount(boardId, cri);
		}else {
			return postMapper.getTotalCount(boardId, PostVO.DESCRIM4POST);
		}
	}
	
	/* 리스트를 띄워주는 역할 */
	public List<PostVO> getListByHashTag(int boardId, Criteria cri){
		if (cri.hasSearching()) {
			return postMapper.getListByHashTag(boardId, cri);
		}else {
			return postMapper.getList(boardId, cri);
		}
	}

	/** id 값으로 Post 객체 조회 */
	public PostVO findPostById(String id) {
		return (PostVO)postMapper.findReplyById(id);
	}
	
	/** id 값으로 Post 객체 조회한 게시글의 조회수 + 1 */
	public PostVO plusReadCnt(String id) {
		return (PostVO)postMapper.plusReadCnt(id);
	}

	/**
	 * 
	 * @param board
	 * @param post  화면에서 사용자가 입력한 변수
	 * @return
	 */
	public int insert(BoardVO board, PostVO post) {
		//영향받은 행 개수 : 몇건을 처리를 했는가
		int affectedRows = postMapper.insert(board, post);
		// mapOccur : 단어의 출현빈도구하기,
		Map<String, Integer> mapOccur = PosAnalyzer.getHashTags(post);
		// 출현빈도를 바탕으로 단어만 추출
		Set<String> setHashTag = mapOccur.keySet();
		// 단어가 비어있지 않다면
		createHashTagAndMapping(post, mapOccur, setHashTag);
		return affectedRows;
	}

	private void createHashTagAndMapping(PostVO post, Map<String, Integer> mapOccur, Set<String> setHashTag) {
		if (setHashTag.isEmpty()) {
			// 게시글에서 단어가 나타나지 않으면 처리할 것이 없다
			return;
		}
		// 기존 사전에 등록되어있는 hashTag(단어)를찾음. setExisting에 출현빈도를 기록시킴
		Set<HashtagVO> setExisting = hashTagMapper.findExisting(setHashTag);
		// 기존에 있는 것들과 짝 지어 주어야한다.

		for (HashtagVO hashtag : setExisting) {
			// setExisting집합에서 단어의 개수를 헤아림
			hashtag.setOccurCnt(mapOccur.get(hashtag.getHashtag()));
		}

		// setHashTag에 남은 것들은 신규 처리해야할 것들.
		for (HashtagVO hashtag : setExisting) {
			setHashTag.remove(hashtag.getHashtag());
		}

		Set<String> setNewHashTag = setHashTag;
		if (!setNewHashTag.isEmpty()) {
			// 새로운 단어를 HashTag 테이블에 등록해주기.
			// 한번에 여러개의 단어를 만들기. ids : where절에 들어올 단어들의 집합
			int[] ids = StringUtil.convertCommaSepString2IntArr(hashTagMapper.getIds(setNewHashTag.size()));
			// Set의 index번호
			int idx = 0;
			// 새롭게 생성한 단어셋 : setHT
			Set<HashtagVO> setHT = new HashSet<>();
			// 새로운 단어집합에서 id를 꺼내서 id의 몇번째꺼와 연결해주고 목록에 한번에 집어넣는다
			for (String hashTag : setNewHashTag) {
				// 배열이니까 단어의 idx번호를 증가시키면서 넣고, 단어를 넣는다
				HashtagVO newHashtag = new HashtagVO(ids[idx++], hashTag);
				// mapOccur에 데이터 등록
				newHashtag.setOccurCnt(mapOccur.get(hashTag));
				setHT.add(newHashtag);
			}
			hashTagMapper.createHashTag(setHT);
			// 새 단어를 단어집에 넣었으니 기존 단어가 된것
			setExisting.addAll(setHT);
		}

		// 한번에 insert를 해주는 고성능의 기능
		// 기존 단어 및 신규 단어와 짝짓기
		hashTagMapper.insertMapBetweenPost(setExisting, post.getId());
	}

	/** 게시글 수정 처리 */
	public boolean updatePost(PostVO post) {
		return postMapper.updatePost(post) == 1;
	}

	/** id 값으로 Post 객체 삭제 */

	public boolean deletePostById(String id) {
		return postMapper.deleteReplyById(id) == 1;
	}

}