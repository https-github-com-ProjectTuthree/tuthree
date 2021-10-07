package project.tuthree.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostFind;
import project.tuthree.domain.post.QPostFind;
import project.tuthree.domain.user.QStudent;
import project.tuthree.domain.user.Teacher;

import javax.persistence.EntityManager;
import java.util.List;

import static project.tuthree.domain.post.QPostFind.postFind;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostFindRepository {

    private final int setPage = 10;
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    /** 선생님 목록 조회 */
    public List<Teacher> findByPage(int page) {
        return em.createQuery("select t from Teacher t", Teacher.class)
                .setFirstResult(setPage * (page - 1))
                .setMaxResults(setPage)
                .getResultList();
    }

    /** id로 특정 게시글 조회 */
    public PostFind findById(Long postId) {
        return em.find(PostFind.class, postId);
    }

    /** id로 선생님 id 찾기 */
    public String findTeacherById(Long postId) {
        String teacherId = jpaQueryFactory.select(postFind.teacherId.id).from(postFind)
                .where(postFind.id.eq(postId))
                .fetchOne();
        return teacherId;
    }

    /** 선생님 게시글 갯수 */
    public Long teacherHasRow() {
        return jpaQueryFactory.selectFrom(postFind)
                .where(JPAExpressions.select(postFind.teacherId).from(postFind).exists())
                .fetchCount();
    }

    /** 학생 게시글 갯수 */
    public Long studentHasRow() {
        return jpaQueryFactory.selectFrom(QStudent.student)
                .where(JPAExpressions.select(postFind.studentId).from(postFind).exists())
                .fetchCount();
    }

    /**
     * 과외 구하는 글 올리기
     * 학생 : 회원가입 시, 글 올림
     * 선생 : 증명서 확인 완료 시, 글 올림
     */
    public void postRegister(PostFind postFind) {
        em.persist(postFind);
    }
}
