package www.dream.com.party.control;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)

@WebAppConfiguration //브라우저 역할을 대신함
@ContextConfiguration({"file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml",
	"file:src\\main\\webapp\\WEB-INF\\spring\\appServlet\\servlet-context.xml"})
public class PartyControllerTest {
	@Autowired
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	@Before //준비 단계
	public void setup() {
		//Mock 가상의, 가짜, 대행자
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	/**
	 * mockMvc를 통하여 테스트 함으로써 back-end 개발자의 할일은 끝남.
	 * front-end 개발자는 EL이나 JSTL을 활용하여 listParty에 들어 있는 정보를 HTML로 나타내 준다.
	 * 
	 */
	@Test
	public void testPartyList() {
		try {
			//get 방법으로 web Server에게 /party/list 이 url로 요청을 보낸다.
			ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get("/party/list"));
			MvcResult mvcResult = ra.andReturn();
			ModelAndView modelAndView = mvcResult.getModelAndView();
			//PartyController의 getList(Model model)에서 model.addAttribute("listParty",~)에 담은
			//정보를 꺼낸다.
			Map model = modelAndView.getModel();
			((List)(model.get("listParty"))).forEach(p->{System.out.println(p);});
		} catch (Exception e) {		
			e.printStackTrace();
		}
	}
}
