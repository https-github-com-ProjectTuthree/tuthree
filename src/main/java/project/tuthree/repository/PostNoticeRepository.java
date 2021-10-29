package project.tuthree.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostNotice;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostNoticeRepository {

    /**
     * repostiroy : entity가 리턴되는 검색
     * service : entity를 dto로 바꾸는 작성
     * api : service 의 결과 dto 리스트를 반환
     *
     * repository : list 값을 이용해 entity에 저장
     * service : dto를 entity로 바꾸는 작업
     * api : 외부 form을 dto 리스트로 반환
     */

    private final int setPage = 10;
    private final EntityManager em;

    /** 공지사항 페이지 목록 조회 */
    public List<PostNotice> findByPage(int page) {
        return em.createQuery("select p from PostNotice p order by p.id desc", PostNotice.class)
                .setFirstResult(setPage * (page - 1))
                .setMaxResults(setPage)
                .getResultList();
    }

    /**
     * 공지사항 id로 특정 글 조회하기
     */
    public PostNotice findById(Long id) {
        PostNotice postNotice = em.find(PostNotice.class, id);
        postNotice.updateView();
        return postNotice;
    }

    /**
     * 공지사항 작성
     */
    public Long writeNotice(PostNotice postNotice) {
        em.persist(postNotice);
        return postNotice.getId();
    }

    /**
     * 공지사항 수정
     */
    public Long updateNotice(Long id, PostNotice postNotice) {
        PostNotice post = em.find(PostNotice.class, id);
        post.updateNotice(postNotice);
        return id;
    }

    /**
     * 공지사항 삭제
     */
    public Long deleteNotice(Long id) {
        try {
            em.remove(em.find(PostNotice.class, id));
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }


    public Long noticeHasRow() {
        return (Long) em.createQuery("select count(p) from PostNotice p").getSingleResult();
    }


}
