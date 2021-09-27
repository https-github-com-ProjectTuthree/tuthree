package project.tuthree.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.NoticeType;
import project.tuthree.domain.post.PostNotice;
import project.tuthree.domain.user.Admin;
import project.tuthree.dto.PostnoticeDTO;
import project.tuthree.repository.PostNoticeRepository;

import javax.persistence.EntityManager;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class PostNoticeServiceTest {

    @Autowired
    PostNoticeService postNoticeService;
    @Autowired
    PostNoticeRepository postNoticeRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 공지사항작성() {
        Admin admin = new Admin("admin2", "admin2");
        em.persist(admin);
        PostnoticeDTO postnoticeDTO = PostnoticeDTO.builder()
                .adminId(admin)
                .type(NoticeType.act1)
                .title("titleApi")
                .content("contentApi")
                .writeAt(new Date())
                .secret(Status.OPEN)
                .view(0L)
                .build();

        Long id = postNoticeService.writeNotice(postnoticeDTO);
        Assert.assertNotNull(id);
    }

    @Test
    public void 공지사항삭제() {
        Long id = 4L;
        int code = postNoticeRepository.deleteNotice(id);
        Assertions.assertEquals(code, 0);
    }

}