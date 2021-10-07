package project.tuthree.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.PostTestPaper;

import javax.persistence.EntityManager;
import java.util.List;

import static project.tuthree.domain.post.QPostTestPaper.postTestPaper;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class PostTestPaperRepository {

    private final int setPage = 10;
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    /** 커뮤니티 페이지 목록 조회 */
    public List<PostTestPaper> findByPage(int page) {
        return em.createQuery("select p from PostTestPaper p order by p.id desc", PostTestPaper.class)
                .setFirstResult(setPage * (page - 1))
                .setMaxResults(setPage)
                .getResultList();
    }

    /** 커뮤니티 id로 특정 글 조회하기 */
    public PostTestPaper findById(Long id) {
        PostTestPaper postTestPaper = em.find(PostTestPaper.class, id);
        postTestPaper.updateView();
        return postTestPaper;
    }

    /**
     * 커뮤니티 글 검색
     */
    public List<PostTestPaper> findByKeyword(String keyword, int page) {
        List<PostTestPaper> list = jpaQueryFactory.selectFrom(postTestPaper)
                .where(postTestPaper.title.contains(keyword)
                        .and(postTestPaper.secret.eq(Status.OPEN)))
                .orderBy(postTestPaper.id.desc())
                .offset(setPage * (page - 1))
                .limit(setPage)
                .fetch();
        return list;
    }

    /**
     * 커뮤니티 검색 전체 글 수
     */
    public Long testpaperHasRowByKeyword(String keyword) {
        return jpaQueryFactory.selectFrom(postTestPaper)
                .where(postTestPaper.title.contains(keyword)
                        .and(postTestPaper.secret.eq(Status.OPEN)))
                .fetchCount();
    }

    /**
     * 커뮤니티 작성 - posttestpaper, userfile 같이 다루기
     */
    public PostTestPaper writeTestPaper(PostTestPaper postTestPaper) {
        em.persist(postTestPaper);
        return postTestPaper;
    }

    /** 커뮤니티 수정 */
    public PostTestPaper updateTestPaper(Long id, PostTestPaper postTestPaper) {
        PostTestPaper post = em.find(PostTestPaper.class, id);
        post.updateTestPaper(postTestPaper);
        return post;
    }

    /**
     * 커뮤니티 글 삭제
     */
    public Long deleteTestPaper(Long id) {
        em.remove(em.find(PostTestPaper.class, id));
        return id;
    }

    /** 커뮤니티 글 갯수 */
    public Long testpaperHasRow() {
//        Long list = (Long) em.createQuery("select count(p) from PostTestPaper p").getSingleResult();
        Long list = jpaQueryFactory.selectFrom(postTestPaper).fetchCount();
        return list;
    }


}
