package www.dream.com.hashTag.persistence;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.framework.util.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
//해당 위치에 있는 데이터를 바탕으로 데이터를 만들어라
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HashtagMapperTest {
   @Autowired
   private HashTagMapper hashTagMapper;

   @Test
   public void test010SelectMultipleId() {

      try {
    	  // ,72,73,74 => int[]
    	 System.out.println(StringUtil.convertCommaSepString2IntArr(hashTagMapper.getIds(3)));
         
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}