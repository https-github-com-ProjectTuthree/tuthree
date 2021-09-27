package project.tuthree.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.tuthree.domain.post.PostAdmin;
import project.tuthree.domain.post.PostFaq;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostFaqRepository {

    private final EntityManager em;

    /** faq 목록 출력 */
    public List<PostFaq> findByPage(int page) {
        int setPage = 10;
        return em.createQuery("select p from PostFaq p order by p.id desc", PostFaq.class)
                .setFirstResult(setPage*(page-1))
                .setMaxResults(setPage)
                .getResultList();
    }

    /**
     * faq 특정 글 조회
     */
    public PostFaq findById(Long id) {
        return em.createQuery("select p from PostFaq p where p.id = :id", PostFaq.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
