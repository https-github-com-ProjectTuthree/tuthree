package project.tuthree.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.PostTestPaper;
import project.tuthree.mapper.PostTestPaperMapper;
import project.tuthree.service.PostTestPaperService;

import javax.persistence.EntityManager;
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

        return em.createQuery("select p.id, p.title from PostTestPaper p where p.secret = :secret order by p.id desc", PostTestPaper.class)
                .setParameter("secret", Status.OPEN)
                .setFirstResult(setpage * (page - 1))
                .setMaxResults(setpage)
                .getResultList();
    }

    /** 커뮤니티 id로 특정 글 조회하기 *///-------------------------
    public PostTestPaper findById(Long id) {
        Object singleResult = em.createNativeQuery("select * from PostTestPaper where id = ?", PostTestPaper.class)
                .setParameter(1, id)
                .getSingleResult();
//        PostTestPaper postTestPaper = em.find(PostTestPaper.class, id);
//        postTestPaper.updateView();
        return singleResult;
    }
//
//    public void nativeQuery() {
//        String sql = "SELECT MEMBER_JPQL_ID,USERAGE,USERNAME,TEAM_ID FROM MEMBER_JPQL WHERE USERAGE> ?";
//
//        Query nativeQuery = em.createNativeQuery(sql,MemberJPQL.class).setParameter(1, 35);
//
//        List<MemberJPQL> resultList = nativeQuery.getResultList();
//
//        System.out.println("====================nativeQuery=====================");
//
//        for(MemberJPQL m : resultList) {
//            System.out.println(m.toString());
//        }
//
//        System.out.println("====================nativeQuery=====================");
//    }
//
//
//    출처: https://coding-start.tistory.com/101 [코딩스타트]

    /** 커뮤니티 작성 */

    /** 커뮤니티 수정 */
    public int updateTestPaper(Long id, PostTestPaper postTestPaper) {
        PostTestPaper post = em.find(PostTestPaper.class, id);
        postTestPaper.updateTestPaper(post);
        return 0;
    }

}
