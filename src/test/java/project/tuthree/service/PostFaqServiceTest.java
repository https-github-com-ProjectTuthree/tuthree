package project.tuthree.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.FaqType;
import project.tuthree.domain.user.Admin;
import project.tuthree.dto.post.PostfaqDTO;
import project.tuthree.repository.PostFaqRepository;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class PostFaqServiceTest {

    @Autowired
    private PostFaqService postFaqService;
    @Autowired
    private PostFaqRepository postFaqRepository;
    @Autowired
    EntityManager em;

    /**
     * faq 작성
     */
    @Test
    public void faq작성() {
        Admin admin = new Admin("admin2", "admin2");
        em.persist(admin);
        PostfaqDTO postfaqDTO = new PostfaqDTO(null, admin, FaqType.MATCHING, "title12", "content12", Status.OPEN);
        Long id = postFaqService.writeFaq(postfaqDTO);

        Assert.assertNotNull(id);
    }

    @Test
    public void faq작성_관리자가없을때() {
        Admin admin = new Admin("admin2", "admin2");
        PostfaqDTO postfaqDTO = new PostfaqDTO(null, admin, FaqType.ETC, "title12", "content12", Status.OPEN);
        Long id = postFaqService.writeFaq(postfaqDTO);
        //InvalidDataAccessApiUsageException
    }
//
//    @Test
//    public void faq삭제() {
//        Long id = 5L;
//        Long code = postFaqRepository.deleteFaq(id);
//        assertEquals(code, id);
//
//    }
//
//    @Test
//    @Rollback(false)
//    public void fap삭제_해당하는아이디의게시글이없을때() {
//        Long id = 17L;
//        Long code = postFaqRepository.deleteFaq(id);
//
//        //assertEquals(code, id);
//        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
//                postFaqRepository.deleteFaq(id));
//
//        assertEquals(e.getClass(), IllegalArgumentException.class);
//
//        //IllegalArgumentException
//    }
    //

    @Test
    public void faq수정() {
        Admin admin = new Admin("admin_api", "admin_test");
        em.persist(admin);
        PostfaqDTO postfaqDTO1 = new PostfaqDTO(null, admin, FaqType.CERTIFY, "title_udpate", "content_update",Status.CLOSE);
        Long id = 5L;
        Long res = postFaqService.updateFaq(id, postfaqDTO1);
        assertEquals(res, id);
    }

}