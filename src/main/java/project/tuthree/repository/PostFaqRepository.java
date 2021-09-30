package project.tuthree.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostAdmin;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.dto.PostfaqDTO;
import project.tuthree.mapper.PostFaqMapper;
import project.tuthree.service.PostFaqService;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostFaqRepository {
    /** faq_repository */
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
        PostFaq faq = em.find(PostFaq.class, id);
        faq.updateView();
        return faq;
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
    public Long deleteFaq(Long id) {
        try {
            em.remove(em.find(PostFaq.class, id));
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * faq 수정 : faqtype, title, content,secret, -  alterAt 넣어주기
     */
    public int updateFaq(Long id, PostFaq postFaq) {

        PostFaq faq = em.find(PostFaq.class, id);
        faq.updateFaq(postFaq);
        return 0;
    }

    public Long faqHasRow() {
        return (Long) em.createQuery("select count(p) from PostFaq p").getSingleResult();
    }
}
