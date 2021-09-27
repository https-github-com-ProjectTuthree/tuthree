package project.tuthree.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.domain.post.PostNotice;
import project.tuthree.dto.PostnoticeDTO;
import project.tuthree.mapper.PostNoticeMapper;

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

    private final EntityManager em;
    private final PostNoticeMapper postNoticeMapper;

    /** 공지사항 페이지 목록 조회 */
    public List<PostNotice> findByPage(int page) {
        int setpage = 10;
        return em.createQuery("select p from PostNotice p order by p.id desc", PostNotice.class)
                .setFirstResult(setpage * (page - 1))
                .setMaxResults(setpage)
                .getResultList();
    }

    /**
     * 공지사항 id로 특정 글 조회하기
     */
    public PostNotice findById(Long id) {
        return em.createQuery("select p from PostNotice p where p.id = :id", PostNotice.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    /**
     * 공지사항 작성
     */
    public Long writeNotice(PostNotice postNotice) {
        em.persist(postNotice);
        return postNotice.getId();
    }

    /**
     * 공지사항 삭제
     */
    public int deleteNotice(Long id) {
        try {
            em.remove(em.find(PostNotice.class, id));
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }


}
