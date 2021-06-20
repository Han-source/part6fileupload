package www.dream.com.hashTag.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HashtagVO {
	private int id; //몇번째로 등록되는 단어인지
	private String hashtag;	// 어떤 단어인가
	private String description; //단어에 대한 설명
	
	private int occurCnt;
	/** id : 몇번째로 등록되는 단어인가. hashtag : 어떤단어인가*/
	public HashtagVO(int id, String hashtag) {
		this.id = id;
		this.hashtag = hashtag;
	}
	
}
