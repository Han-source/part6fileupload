package www.dream.com.selectKey.persistence;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import www.dream.com.selectKey.model.DetailVO;
import www.dream.com.selectKey.model.MasterVO;

@RunWith(SpringJUnit4ClassRunner.class)

@WebAppConfiguration // 브라우저 역할을 대신함
@ContextConfiguration({ "file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml",
      "file:src\\main\\webapp\\WEB-INF\\spring\\appServlet\\servlet-context.xml" })
public class MasterDetailTest {
   @Autowired
   private MasterDetail masterDetailMapper;

   @Test
   public void testInsertMaster() {
      try {
         MasterVO newBie = new MasterVO();
         newBie.setName("newBie");

         Date now = Calendar.getInstance().getTime();
         newBie.setReg_dt(now);

         masterDetailMapper.insertMaster(newBie);
         int idOfNew = masterDetailMapper.findByName("newBie", now);

         DetailVO addr = new DetailVO();
         addr.setInfo("address");
         masterDetailMapper.insertDetail(idOfNew, addr);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   @Test
   public void testInsertMasterBySelectKey() {
      try {
         MasterVO newBie = new MasterVO();
         newBie.setName("newBie");
         masterDetailMapper.insertMasterBySelectKey(newBie);

         DetailVO addr = new DetailVO();
         addr.setInfo("address");
         masterDetailMapper.insertDetail(newBie.getId(), addr);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}