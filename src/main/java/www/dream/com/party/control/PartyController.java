package www.dream.com.party.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import www.dream.com.party.service.PartyService;

@Controller
@RequestMapping("/party/*")
public class PartyController {
	@Autowired
	private PartyService partyService;
	
	@GetMapping(value = "list")
	public void getList(Model model) {
		model.addAttribute("listParty", partyService.getList());
	}
}
