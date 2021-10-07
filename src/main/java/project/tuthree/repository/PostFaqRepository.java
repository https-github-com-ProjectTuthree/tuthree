package project.tuthree.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.ApiController.EmbeddedResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.ApiController.StatusCode;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.exception.NotFoundRequestData;
import project.tuthree.mapper.PostFaqMapper;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostFaqRepository {
    /** faq_repository */
    private final int setPage =  10;
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    /** faq 목록 출력 */
    public List<PostFaq> findByPage(int page) {
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
    public Long deleteFaq(Long id) throws NotFoundRequestData{
        try {
            em.remove(em.find(PostFaq.class, id));
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundRequestData();
        }
    }

    /**
     * faq 수정 : faqtype, title, content,secret, -  alterAt 넣어주기
     */
    public Long updateFaq(Long id, PostFaq postFaq) {

        PostFaq faq = em.find(PostFaq.class, id);
        faq.updateFaq(postFaq);
        return id;
    }

    public Long faqHasRow() {
        return (Long) em.createQuery("select count(p) from PostFaq p").getSingleResult();
    }
}
