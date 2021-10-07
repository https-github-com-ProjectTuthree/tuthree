package project.tuthree.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostReview;
import project.tuthree.domain.room.QStudyRoom;
import project.tuthree.domain.room.QStudyRoomInfo;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.domain.room.StudyRoomInfo;

import javax.persistence.EntityManager;

import java.util.List;

import static project.tuthree.domain.post.QPostReview.postReview;
import static project.tuthree.domain.room.QStudyRoom.studyRoom;
import static project.tuthree.domain.room.QStudyRoomInfo.studyRoomInfo;


@Getter
@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class StudyRoomRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    /** 스터디룸 개설하기 */
    public StudyRoom roomRegister(StudyRoom studyRoom) {
        em.persist(studyRoom);
        return studyRoom;
    }

    /** 수업 계획서 등록하기 */
    public String infoRegister(StudyRoomInfo studyRoomInfo) {
        em.persist(studyRoomInfo);
        return studyRoomInfo.getId().getTeacherId().getId();
    }

    /** 수업 계획서 수정하기 */
    public void infoUpdate(StudyRoomInfo studyRoomInfo) {

        log.info("===============" + studyRoomInfo.getId().getStudentId().getId());
        StudyRoomInfo info = em.find(StudyRoomInfo.class, studyRoomInfo.getId());
        info.infoUpdate(studyRoomInfo);
    }

    /** 수업 계획서 조회하기 */
    public StudyRoomInfo findStudyRoomInfo(String teacherId, String studentId) {
        StudyRoomInfo studyRoomInfo = jpaQueryFactory.selectFrom(QStudyRoomInfo.studyRoomInfo)
                .where(QStudyRoomInfo.studyRoomInfo.id.teacherId.id.eq(teacherId)
                        .and(QStudyRoomInfo.studyRoomInfo.id.studentId.id.eq(studentId)))
                .fetchOne();
        return studyRoomInfo;
    }

    /** 선생님 아이디, 학생 아이디로 스터디룸 찾기 */
    public StudyRoom findStudyRoomById(String teacherId, String studentId) {
        StudyRoom studyRoom = jpaQueryFactory.selectFrom(QStudyRoom.studyRoom)
                .where(QStudyRoom.studyRoom.teacherId.id.eq(teacherId)
                        .and(QStudyRoom.studyRoom.studentId.id.eq(studentId)))
                .fetchOne();
        return studyRoom;
    }

    /** 선생님 아이디로 리뷰 조회 */
    public List<PostReview> findReviewByTeacher(String teacherId) {
        List<PostReview> postReviewList = jpaQueryFactory.selectFrom(postReview)
                .where(postReview.id.teacherId.id.eq(teacherId))
                .fetch();
        return postReviewList;
    }

    /** 학생 리뷰 작성하기 */
    public String writeReview(PostReview postReview) {
        em.persist(postReview);
        return postReview.getId().getTeacherId().getName();
    }

    /** 과외 정보 승낙하기 */
    public void acceptInfo(String teacherId, String studentId) {
        StudyRoomInfo studyRoomInfo = findStudyRoomInfo(teacherId, studentId);
        studyRoomInfo.acceptInfo();
    }

    /** 수업 관리창 시작하기 */
    public void openStudyRoom(String teacherId, String studentId) {
        StudyRoom studyRoom = findStudyRoomById(teacherId, studentId);
        studyRoom.openStudyRoom();
    }

    /** 수업 종료하기 */
    public void clostStudyRoom(String teacherId, String studentId) {
        StudyRoom studyRoom = findStudyRoomById(teacherId, studentId);
        studyRoom.closeStudyRoom();
    }
}