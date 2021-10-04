package project.tuthree.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.QPostTestPaper;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostTestPaperRepositoryTest {
    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    EntityManager em;

    @Test
    public void querydeslTest() {
        Long id = (Long) em.createQuery("select count(p) from PostTestPaper p").getSingleResult();
        Long id1 = jpaQueryFactory.selectFrom(QPostTestPaper.postTestPaper).fetchCount();
        Assertions.assertThat(id).isEqualTo(id1);
    }

}