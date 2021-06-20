package www.dream.com.party.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import www.dream.com.party.model.Party;
import www.dream.com.party.persistence.PartyMapper;

@Service
@AllArgsConstructor
public class PartyService {
	@NonNull
	private PartyMapper partyMapper;
	
	public List<Party> getList(){
		return partyMapper.getList();
	}
}
