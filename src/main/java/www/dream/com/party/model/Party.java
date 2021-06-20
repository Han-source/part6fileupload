package www.dream.com.party.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.common.model.CommonMngVO;
import www.dream.com.framework.langPosAnalyzer.HashTarget;
import www.dream.com.framework.printer.ClassPrintTarget;
import www.dream.com.framework.printer.PrintTarget;

/**
 * 모든 행위자 정보의 공통적인 상위 정보를 담고 있는 추상적인 클래스
 * 
 * @author "Wook"
 *
 */
@Data
@NoArgsConstructor
@ClassPrintTarget
public abstract class Party extends CommonMngVO {
	private String userId; // 로그인 ID
	private String userPwd; // 암호, 암호화는 나중에 처리함
	@HashTarget  
	@PrintTarget(order=250, caption="작성자")
	private String name;
	private Date birthDate; // 생일 년월일
	private boolean male;
	private boolean enabled; // 가입여부중 가입은 true 탈퇴는 false

	// 1:N 관계에 대한 속성
	@HashTarget
	private List<ContactPoint> listContactPoint = new ArrayList<>();
	
	public Party(String userId) {
		this.userId = userId;
	}
	public void addContactPoint(ContactPoint cp) {
		listContactPoint.add(cp);
	}
	
	@Override
	public String toString() {
		return "Party [userId=" + userId + ", userPwd=" + userPwd + ", name=" + name + ", birthDate=" + birthDate
				+ ", isMale=" + male + ", enabled=" + enabled + ", listContactPoint=" + listContactPoint
				+ ", toString=" + super.toString() + "]";
	}



}