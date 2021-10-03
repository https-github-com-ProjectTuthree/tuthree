package project.tuthree.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostTestPaper;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class PostTestPaperRepository {

    private final EntityManager em;

    /** 커뮤니티 페이지 목록 조회 */
    public List<PostTestPaper> findByPage(int page) {
        int setpage = 10;
        return em.createQuery("select p from PostTestPaper p order by p.id desc", PostTestPaper.class)
                .setFirstResult(setpage * (page - 1))
                .setMaxResults(setpage)
                .getResultList();
    }

    /** 커뮤니티 id로 특정 글 조회하기 */
    public PostTestPaper findById(Long id) {
        PostTestPaper postTestPaper = em.find(PostTestPaper.class, id);
        postTestPaper.updateView();
        return postTestPaper;
    }

    /**
     * 커뮤니티 작성 - posttestpaper, userfile 같이 다루기
     */
    public PostTestPaper writeTestPaper(PostTestPaper postTestPaper) {
        em.persist(postTestPaper);
        return postTestPaper;
    }

    /** 커뮤니티 수정 */
    public Long updateTestPaper(Long id, PostTestPaper postTestPaper) {
        PostTestPaper post = em.find(PostTestPaper.class, id);
        post.updateTestPaper(postTestPaper);
        return id;
    }

    /**
     * 커뮤니티 글 삭제
     */
    public Long deleteTestPaper(Long id) {
        em.remove(em.find(PostTestPaper.class, id));
        return id;
    }

    public Long testpaperHasRow() {
        Long list = (Long) em.createQuery("select count(p) from PostTestPaper p").getSingleResult();
        return list;
    }

}
