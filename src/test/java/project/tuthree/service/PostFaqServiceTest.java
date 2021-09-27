package project.tuthree.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.FaqType;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.domain.user.Admin;
import project.tuthree.dto.PostfaqDTO;
import project.tuthree.repository.PostFaqRepository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
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
        PostfaqDTO postfaqDTO= new PostfaqDTO(null, new Admin("admin_test", "admin_test"), FaqType.act1, "title12", "content12",0L, new Date(), null, Status.OPEN);
        Long id = postFaqService.writeFaq(postfaqDTO);

        Assert.assertNotNull(id);
    }

    @Test
    public void faq삭제() {
        Long id = 5L;
        int code = postFaqRepository.deleteFaq(id);
        Assertions.assertEquals(code, 0);

    }

}