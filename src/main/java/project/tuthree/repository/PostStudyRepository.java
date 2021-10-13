package project.tuthree.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostStudy;
import project.tuthree.domain.post.QPostStudy;
import project.tuthree.domain.room.StudyRoom;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static project.tuthree.domain.post.QPostStudy.postStudy;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostStudyRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    /** 스터디룸 보고서 전체 조회 */
    public List<PostStudy> findPostByStudyRoom(StudyRoom studyRoom) {
        return jpaQueryFactory.selectFrom(postStudy)
                .where(postStudy.studyRoomId.eq(studyRoom))
                .fetch();
    }

    /** 수업 보고서 등록하기 */
    public Long registerPost(PostStudy postStudy) {
        em.persist(postStudy);
        return postStudy.getId();
    }

    /** 특정 수업 보고서 조회 */
    public PostStudy findPost(Long id) {
        return em.find(PostStudy.class, id);
    }

    /** 수업 보고서 수정 */
    public Long updatePost(Long id, PostStudy postStudy) {
        PostStudy post = em.find(PostStudy.class, id);
        post.updatePostStudy(postStudy);
        return post.getId();
    }

    /** 수업 보고서 삭제 */
    public Long deletePost(Long id) {
        em.remove(em.find(PostStudy.class, id));
        return id;
    }

    @Getter
    @AllArgsConstructor
    public static class StudyListDTO {
        Long id;
        Date dateAt;
        Long number;
        String start;
        String end;
        String detail;
    }

}
