package www.dream.com.bulletinBoard.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.common.model.CommonMngVO;

/**
 * 게시판
 * 
 * @author "j222_sang"
 *
 */

@Data
@NoArgsConstructor
public class BoardVO extends CommonMngVO {
	private int id;	 // 아이디
	private String  name;		// 이름	
	private String  description;	 // 설명
	
	public BoardVO(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "BoardVO [id=" + id + ", name=" + name + ", description=" + description + ", "
				+ super.toString() + "]";
	}
	
	
	
}
