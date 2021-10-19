package project.tuthree.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.QPostTestPaper;
import project.tuthree.dto.post.PostExamDTO;
import project.tuthree.dto.post.PostExamDTO.ProblemDTO;

import javax.persistence.EntityManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class PostTestPaperRepositoryTest {
    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    EntityManager em;

    @Autowired
    UserFileRepository userFileRepository;

    @Test
    public void querydeslTest() {
        Long id = (Long) em.createQuery("select count(p) from PostTestPaper p").getSingleResult();
        Long id1 = jpaQueryFactory.selectFrom(QPostTestPaper.postTestPaper).fetchCount();
        Assertions.assertThat(id).isEqualTo(id1);
    }

    @Test
    public void 시험지json저장() throws NoSuchAlgorithmException, IOException {

        List<ProblemDTO> list = new ArrayList<>();
        ProblemDTO dto = new ProblemDTO(1L, "num", "5", false);
        ProblemDTO dto2 = new ProblemDTO(1L, "num", "5", false);
        list.add(dto);
        list.add(dto2);

        PostExamDTO postExamDTO = new PostExamDTO(1L, list);

        userFileRepository.saveJsonFile(postExamDTO, "answer.json", UserFileRepository.FileType.POSTPAPER);

    }

}