package www.dream.com.party.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 유저입장
 * @author "Wook"
 *
 */
@Data
@NoArgsConstructor
public class User extends Party {

	public User(String userId) {
		super(userId);
		
	}
}
