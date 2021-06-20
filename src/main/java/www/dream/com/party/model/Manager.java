package www.dream.com.party.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 관리자 관점
 */
@Data
@NoArgsConstructor
public class Manager extends Party {
	
	public Manager(String userId) {
		super(userId);
		
	}
}
