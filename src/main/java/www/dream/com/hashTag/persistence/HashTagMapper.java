package www.dream.com.hashTag.persistence;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import www.dream.com.hashTag.model.HashtagVO;

public interface HashTagMapper {
	/**
	 * setHashTag로 받은 단어 중 에 기존에 관리하고 있는 단어 집합 찾기
	 * @param setHashTag isEmpty() false일때만 불러주세요
	 * @return
	 */
	public Set<HashtagVO> findExisting(@Param("setHashTag") Set<String> setHashTag);

	/**
	 * Hashtag와 Post 사이의 관계 정보 다중 입력하기(고성능)
	 * @param setExisting
	 * @param id
	 * @return
	 */
	public int insertMapBetweenPost(@Param("setExisting") Set<HashtagVO> setExisting, @Param("postId") String id);
	/**
	 * HashtagVO의 id를 지정한 개수만큼 Sequence를 통하여 한번에 왕창 만들기. 성능
	 * @param cnt
	 * @return
	 */
	public String getIds(int cnt);
	public int createHashTag(@Param("setNewHashtag") Set<HashtagVO> newHashtag);
}
