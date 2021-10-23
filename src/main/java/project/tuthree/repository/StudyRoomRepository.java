package project.tuthree.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQuery;
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
import project.tuthree.dto.post.PostAnswerDTO;
import project.tuthree.dto.post.PostAnswerDTO.AnswerDTO;
import project.tuthree.dto.post.PostAnswerDTO.answerType;
import project.tuthree.dto.post.PostExamDTO;
import project.tuthree.dto.post.PostExamDTO.ProblemDTO;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
    private final UserFileRepository userFileRepository;

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
        if(studyRoom == null) return null;
        Boolean bool = jpaQueryFactory.select(studyRoomInfo.status)
                .from(studyRoomInfo)
                .where(studyRoomInfo.id.eq(studyRoom))
                .fetchOne();
        if(bool == true) {
            return studyRoom;
        }
        return null;
    }

    /** 아이디 하나로 전체 스터디룸 시간 찾기 - 일단 선생님만 */
    public List<StudyRoomInfo> findStudyRoomSchedule(String id) {
        return jpaQueryFactory.selectFrom(studyRoomInfo)
                .where(studyRoomInfo.id.teacherId.id.eq(id))
                .fetch();
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
    public List<PostReview> findReviewByTeacher(String teacherId, String sort) {
        JPAQuery<PostReview> query = jpaQueryFactory.selectFrom(postReview)
                .where(postReview.id.teacherId.id.eq(teacherId));
        switch (sort){
            case "latest" :
                query.orderBy(postReview.writeAt.desc());
                break;
            case "high" :
                query.orderBy(postReview.star.desc());
                break;
            case "low" :
                query.orderBy(postReview.star.asc());
                break;
            default:
                break;
        }
        return query.fetch();
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
                .where(userFile.studyRoomId.eq(studyRoom)
                        .and(userFile.realTitle.endsWith(".pdf")))
                .fetch();
    }

    /**
     * 정답 비교 확인 -> json 둘다 객체로 바꿔서 값 비교하고 o, x json으로 변환해서 내보내기
     * @param id 문제 번호
     * @return
     */
    public PostAnswerDTO scoreTestPaper(Long id) throws IOException {
        UserFile userFile = userFileRepository.userFileFindByFileId(id);
        String name = userFile.getRealTitle();

        if(name.contains(".pdf")) {
            name = name.split("\\.pdf")[0];
        }

        PostExamDTO dto = findExamByIdnName(userFile.getStudyRoomId(), name + "_teacher_answer.json");

        List<ProblemDTO> teacher = dto.getProblem();
        List<ProblemDTO> student = findExamByIdnName(userFile.getStudyRoomId(), name + "_student_answer.json").getProblem();
        List<AnswerDTO> answer = new ArrayList<>();

        for (int i = 0; i < teacher.size(); i++) {
            if (teacher.get(i).isAuto() == true) {
                answer.add((teacher.get(i).getAns().equals(student.get(i).getAns())) ?
                        (new AnswerDTO(teacher.get(i).getQuestion(), answerType.RIGHT, teacher.get(i).getAns(), student.get(i).getAns())) :
                        (new AnswerDTO(teacher.get(i).getQuestion(), answerType.WRONG, teacher.get(i).getAns(), student.get(i).getAns())));
            }else{
                answer.add(new AnswerDTO(teacher.get(i).getQuestion(), answerType.NONE, teacher.get(i).getAns(), student.get(i).getAns()));
            }
        }

        return new PostAnswerDTO(Long.valueOf(teacher.size()), dto.getDueDate(), answer);
    }

    /** 스터디룸 아이디와 파일 이름으로 답안지 찾기 - (json -> 객체) 반환까지 완료 */
    public PostExamDTO findExamByIdnName(StudyRoom studyRoom, String name) throws IOException {
        String path = jpaQueryFactory.select(userFile.saveTitle).from(userFile)
                .where(userFile.studyRoomId.eq(studyRoom)
                        .and(userFile.realTitle.eq(name)))
                .fetchOne();
        Object object = userFileRepository.changeJsonFile(path);
        ObjectMapper mapper = new ObjectMapper();
        PostExamDTO postExamDTO = mapper.convertValue(object, PostExamDTO.class);
        return postExamDTO;
    }

    /** 스터디룸 아이디으로 파일 이름 겹치는 지 확인 */
    public String findSameNameTestPaper(StudyRoom studyRoom, String name){
        Long count = jpaQueryFactory.selectFrom(userFile)
                .where(userFile.studyRoomId.eq(studyRoom)
                        .and(userFile.realTitle.contains(name))).fetchCount();
        return (count > 0) ? name + "(" + count + ")" : name;
    }
}