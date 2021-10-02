package project.tuthree.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.PostNotice;
import project.tuthree.domain.post.PostTestPaper;
import project.tuthree.dto.PostTestPaperDTO;
import project.tuthree.mapper.PostTestPaperMapper;
import project.tuthree.service.PostTestPaperService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class PostTestPaperRepository {

    private final EntityManager em;
//    private final PostTestPaperMapper testPaperMapper;
//    private final PostTestPaperService testPaperService;

    /** 커뮤니티 페이지 목록 조회 *///하 몰라..
    public List<PostTestPaper> findByPage(int page) {
        int setpage = 10;
        return em.createQuery("select p from PostTestPaper p order by p.id desc", PostTestPaper.class)
                .setFirstResult(setpage * (page - 1))
                .setMaxResults(setpage)
                .getResultList();
    }

    /** 커뮤니티 id로 특정 글 조회하기 *///-------------------------
    public PostTestPaper findById(Long id) {
        PostTestPaper postTestPaper = em.find(PostTestPaper.class, id);
        postTestPaper.updateView();
        return postTestPaper;
    }


    /** 커뮤니티 작성 */

    /** 커뮤니티 수정 */
    public int updateTestPaper(Long id, PostTestPaper postTestPaper) {
        PostTestPaper post = em.find(PostTestPaper.class, id);
        postTestPaper.updateTestPaper(post);
        return 0;
    }

}
