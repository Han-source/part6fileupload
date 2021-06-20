package www.dream.com.bulletinBoard.persistence;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.model.ReplyVO;
import www.dream.com.common.dto.Criteria;
import www.dream.com.party.model.Admin;
@RunWith(SpringJUnit4ClassRunner.class)
//해당 위치에 있는 데이터를 바탕으로 데이터를 만들어라
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReplyMapperTest4Post {   
   @Autowired
   private ReplyMapper postMapper;
   
  // @Test
   public void test000InsertPost() {
      try {
    
         BoardVO commNotice = new BoardVO(1);
         PostVO post = new PostVO("아름다운 강산2", "제제젲제제333333시절시절", new Admin("admin"));
         postMapper.insert(commNotice, post);
         System.out.println("지금 만든 객체의 아이디는" + post.getId() + "책 191쪽 참조");
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
 
  // @Test
   public void test010DeleteById() {
     
      try {
         System.out.println(postMapper.deleteReplyById("00003"));
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   
  // @Test
   public void test040GetList() {
      assertNotNull(postMapper);
      try {
         postMapper.getList(1, new Criteria()).forEach(post->{System.out.println(post);});
         
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   

   //@Test
   public void test020UpdateById() {
     
      try {
         PostVO post = (PostVO) postMapper.findReplyById("00004");
         post.setTitle(post.getTitle() + "1");
         postMapper.updatePost(post);
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   @Test
   public void test030findById() {
     
      try {
         ReplyVO post = postMapper.findReplyById("0000V");
         System.out.println(post);
         
      }catch(Exception e) {
         e.printStackTrace();
      }
   }
}