package www.dream.com.party.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 사장
 * @author "Wook"
 *
 */
@Data
@NoArgsConstructor
public class Admin extends Party {
	
	public Admin(String userId) {
	super(userId);
	
}
	@Override
	public String toString() {
		return "Admin [toString()=" + super.toString() + "]";
	}


}
