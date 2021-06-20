package www.dream.com.selectKey.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MasterVO {
	private int id;
	private String name;
	private Date reg_dt;
	
	private List<DetailVO> listDetail;
}
