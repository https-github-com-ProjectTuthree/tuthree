package project.tuthree.service;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
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

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostNoticeServiceTest {

    @Autowired
    PostNoticeService postNoticeService;

    @Test
    @Rollback
    public void 공지사항작성() {
        Admin admin = new Admin("admin1", "admin1");
        PostnoticeDTO postnoticeDTO = PostnoticeDTO.builder()
                .adminId(admin)
                .type(NoticeType.act1)
                .title("titleApi")
                .content("contentApi")
                .writeAt(new Date())
                .secret(Status.OPEN)
                .build();

        Long id = postNoticeService.writeNotice(postnoticeDTO);
        Assert.assertNotNull(id);

    }

}