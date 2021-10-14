package project.tuthree.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.Status;
import project.tuthree.domain.file.QUserFile;
import project.tuthree.domain.file.UserFile;
import project.tuthree.domain.post.PostReview;
import project.tuthree.domain.post.QPostReview;
import project.tuthree.domain.room.QStudyRoom;
import project.tuthree.domain.room.QStudyRoomInfo;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.domain.room.StudyRoomInfo;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.post.PostExamDTO;

import javax.persistence.EntityManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static project.tuthree.domain.file.QUserFile.userFile;
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

    /**
     * 수업 계획서 수정하기 */
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
        Boolean bool = jpaQueryFactory.select(studyRoomInfo.status)
                .from(studyRoomInfo)
                .where(studyRoomInfo.id.eq(studyRoom))
                .fetchOne();
        if(bool == true) {
            return studyRoom;
        }
        return null;
    }

    /** 아이디 하나로 스터디룸 찾기 */
    public List<StudyRoom> findStudyRoomByOneId(String id, Status status) {
        return jpaQueryFactory.selectFrom(studyRoom)
                .where((studyRoom.teacherId.id.eq(id)
                        .or(studyRoom.studentId.id.eq(id)))
                        .and(studyRoom.Status.eq(status))
                )
                .fetch();
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
        /** 선생님 리뷰 수정하기!!! */
        em.persist(postReview);
        Teacher teacher = em.find(Teacher.class, postReview.getId().getTeacherId().getId());
        Long num = jpaQueryFactory.selectFrom(QPostReview.postReview)
                .where(QPostReview.postReview.id.teacherId.id.eq(postReview.getId().getTeacherId().getId()))
                .fetchCount();
        teacher.updateStar(num, postReview.getStar());
        return teacher.getName();
    }

    /** 과외 정보 승낙하기 */
    public boolean acceptInfo(String teacherId, String studentId) {
        StudyRoomInfo studyRoomInfo = findStudyRoomInfo(teacherId, studentId);
        if(studyRoomInfo.isStatus() == true) {
            return false;
        }
        return studyRoomInfo.acceptInfo();
    }

    /** 과외 정보 승낙 여부 확인 */
    public boolean isAcceptInfo(String teacherId, String studentId) {
        StudyRoomInfo studyRoomInfo = findStudyRoomInfo(teacherId, studentId);
        return studyRoomInfo.isStatus();
    }

    /** 수업 관리창 시작하기 */
    public boolean openStudyRoom(String teacherId, String studentId) {
        StudyRoom studyRoom = findStudyRoomById(teacherId, studentId);
        studyRoom.openStudyRoom();
        return true;
    }

    /** 수업 종료하기 */
    public void closeStudyRoom(String teacherId, String studentId) {
        StudyRoom studyRoom = findStudyRoomById(teacherId, studentId);
        studyRoom.closeStudyRoom();
    }

    /** 시험지 목록 불러오기 */
    public List<UserFile> listTestPaper(StudyRoom studyRoom) {
        return jpaQueryFactory.selectFrom(userFile)
                .where(userFile.studyRoomId.eq(studyRoom))
                .fetch();
    }

    /** 시험지 학생 답안 등록 */

    /** 정답 비교 확인 -> json 둘다 객체로 바꿔서 값 비교하고 o, x json으로 변환해서 내보내기 */
}