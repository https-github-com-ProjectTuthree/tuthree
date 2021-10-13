package project.tuthree.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.BookMark;
import project.tuthree.domain.QBookMark;
import project.tuthree.domain.post.PostFind;
import project.tuthree.domain.post.QPostFind;
import project.tuthree.domain.user.QStudent;
import project.tuthree.domain.user.Teacher;

import javax.persistence.EntityManager;
import java.util.List;

import static project.tuthree.domain.QBookMark.bookMark;
import static project.tuthree.domain.post.QPostFind.postFind;
import static project.tuthree.domain.user.QStudent.student;
import static project.tuthree.domain.user.QTeacher.teacher;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostFindRepository {

    private final int setPage = 12;

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    /** 선생님 post find 목록 찾기 */
    public List<PostFind> findTeacherFindList(int page){
        return jpaQueryFactory.selectFrom(postFind)
                .join(postFind.teacherId, teacher).orderBy(teacher.create_date.desc())
                .offset(setPage * (page - 1)).limit(setPage)
                .fetch();
    }

    /** 선생님 post find 목록 찾기 */
    public List<PostFind> findStudentFindList(int page){
        return jpaQueryFactory.selectFrom(postFind)
                .join(postFind.studentId, student).orderBy(student.create_date.desc())
                .offset(setPage * (page - 1)).limit(setPage)
                .fetch();
    }

    /** id로 특정 게시글 조회 */
    public PostFind findById(Long postId) {
        return em.find(PostFind.class, postId);
    }

    /** post id로 선생님 id 찾기 */
    public String findTeacherById(Long postId) {
        String teacherId = jpaQueryFactory.select(postFind.teacherId.id).from(postFind)
                .where(postFind.id.eq(postId))
                .fetchOne();
        return teacherId;
    }

    /** post id로 학생 id 찾기 */
    public String findStudentById(Long postId) {
        String userId = jpaQueryFactory.select(postFind.studentId.id).from(postFind)
                .where(postFind.id.eq(postId)).fetchOne();
        return userId;
    }

    /** 선생님 게시글 갯수 */
    public Long teacherHasRow() {
        return jpaQueryFactory.selectFrom(postFind)
                .where(postFind.teacherId.isNotNull())
                .fetchCount();
    }

    /** 학생 게시글 갯수 */
    public Long studentHasRow() {
        return jpaQueryFactory.selectFrom(postFind)
                .where(postFind.studentId.isNotNull())
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

    /** 북마크 기능 */
    public Long addBookMark(BookMark bookMark) {
        em.persist(bookMark);
        return bookMark.getId();
    }

    /** 북마크 삭제 */
    public Long deleteBookMark(Long id) {
        em.remove(em.find(BookMark.class, id));
        return id;
    }

    /** 북마크 리스트 불러오기 */
    public List<BookMark> listBookMark(String userId) {
        return jpaQueryFactory.selectFrom(bookMark)
                .where(bookMark.user1.eq(userId))
                .fetch();
    }
}
