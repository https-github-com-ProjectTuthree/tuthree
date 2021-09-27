package project.tuthree.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.dto.PostfaqDTO;
import project.tuthree.repository.PostFaqRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostFaqServiceTest {

    @Autowired
    private PostFaqService postFaqService;
    @Autowired
    private PostFaqRepository postFaqRepository;



    @Test
    @Rollback
    public void faq페이지목록조회() throws Exception {
        //given
        int page = 3;
//        when
        List<PostfaqDTO> list = postFaqService.faqFindByPage(3);

        //then
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
    }
}