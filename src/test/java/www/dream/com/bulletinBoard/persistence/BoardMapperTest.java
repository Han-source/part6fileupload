package www.dream.com.bulletinBoard.persistence;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.party.persistence.PartyMapper;
@RunWith(SpringJUnit4ClassRunner.class)
//해당 위치에 있는 데이터를 바탕으로 데이터를 만들어라
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BoardMapperTest {   
   @Autowired
   private BoardMapper boardMapper;
   
   @Test
   public void testGetList() {
      assertNotNull(boardMapper);
      try {
         boardMapper.getList().forEach(board->{System.out.println(board);});
         
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   @Test
   public void testGetBoard() {
      assertNotNull(boardMapper);
      try {
        System.out.println(boardMapper.getBoard(2));
         
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
}