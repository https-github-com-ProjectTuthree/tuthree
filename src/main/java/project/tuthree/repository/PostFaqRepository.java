package project.tuthree.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostAdmin;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.dto.PostfaqDTO;
import project.tuthree.mapper.PostFaqMapper;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostFaqRepository {

    private final EntityManager em;
    private final PostFaqMapper postFaqMapper;

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

    /**
     * FAQ 작성
     */
    public Long writeFaq(PostFaq postFaq) {
        em.persist(postFaq);
        return postFaq.getId();
    }

    /**
     * faq 삭제
     */
    public int deleteFaq(Long id) {
        try {
            em.remove(em.find(PostFaq.class, id));
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

}
