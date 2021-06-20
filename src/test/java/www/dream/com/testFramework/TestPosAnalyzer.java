package www.dream.com.testFramework;

import java.util.Map;

import org.junit.Test;

import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.framework.langPosAnalyzer.PosAnalyzer;
import www.dream.com.party.model.Admin;
import www.dream.com.party.model.ContactPoint;
import www.dream.com.party.model.Party;

public class TestPosAnalyzer {

	@Test
	public void test() {
		PostVO post = new PostVO();
		post.setTitle("질문");
		post.setContent("안녕하세요 컴퓨터 지갑 컴퓨터 마우스 키보드 안녕");
		Admin writer = new Admin();
		writer.setName("홍길동");
		post.setWriter(writer);
		
		ContactPoint cp = new ContactPoint();
		cp.setInfo("사자 고양이 코끼리");
		writer.addContactPoint(cp);
		cp = new ContactPoint();
		cp.setInfo("wltkd96@naver.com");
		writer.addContactPoint(cp);
		
		Map<String, Integer> map = PosAnalyzer.getHashTags(post);
		for (String k : map.keySet()) {
			System.out.println(k + " : " + map.get(k));
		}
	}
}
