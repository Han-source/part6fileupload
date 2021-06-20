package www.dream.com.party.persistence;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.party.model.ContactPoint;
import www.dream.com.party.model.User;
@RunWith(SpringJUnit4ClassRunner.class)
//해당 위치에 있는 데이터를 바탕으로 데이터를 만들어라
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PartyMapperTest {   
   @Autowired
   private PartyMapper partyMapper;
   
   @Test
   public void testGetList() {
      assertNotNull(partyMapper);
      try {
         partyMapper.getList().forEach(p->{System.out.println(p);});
         
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
}