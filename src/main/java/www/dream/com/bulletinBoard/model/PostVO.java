package www.dream.com.bulletinBoard.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.framework.langPosAnalyzer.HashTarget;
import www.dream.com.framework.printer.ClassPrintTarget;
import www.dream.com.framework.printer.PrintTarget;
import www.dream.com.framework.util.ToStringSuperHelp;
import www.dream.com.party.model.Party;

/**
 * 게시글
 * @author "j222_sang"
 */
@Data
@NoArgsConstructor
@ClassPrintTarget
public class PostVO extends ReplyVO {
	public static final String DESCRIM4POST = "post";
	
	@HashTarget
	private String title;

	@PrintTarget(order=300, caption="조회수")
	private int readCnt;
	private int likeCnt; // 좋아요
	private int dislikeCnt; // 싫어요

	public PostVO(String title, String content, Party writer) {
		super(content, writer);
		this.title = title;
	}
	
	@PrintTarget(order=100, caption="제목", withAnchor = true)
	public String getTitleWithCnt() {
		return title + "[" + super.replyCnt + "]";
	}
	
	@Override
	public String toString() {
		return "PostVO [" + ToStringSuperHelp.trimSuperString(super.toString()) 
				+ ", title=" + title + ", readCnt="
				+ readCnt + ", likeCnt=" + likeCnt 
				+ ", dislikeCnt=" + dislikeCnt + "]";
	}

}
