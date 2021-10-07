package project.tuthree.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.PostReview;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.domain.room.StudyRoomInfo;
import project.tuthree.domain.user.Student;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.post.PostreviewDTO;
import project.tuthree.dto.room.StudyroomDTO;
import project.tuthree.dto.room.StudyroomInfoDTO;
import project.tuthree.mapper.PostReviewMapper;
import project.tuthree.mapper.StudyRoomMapper;
import project.tuthree.mapper.StudyroomInfoMapper;
import project.tuthree.repository.PostFindRepository;
import project.tuthree.repository.StudyRoomRepository;
import project.tuthree.repository.UserEntityRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyRoomService {
    private final StudyRoomRepository studyRoomRepository;
    private final StudyroomInfoMapper studyroomInfoMapper;
    private final UserEntityRepository userEntityRepository;
    private final StudyRoomMapper studyRoomMapper;
    private final PostReviewMapper postReviewMapper;
    private final PostFindRepository postFindRepository;

    /** 스터디룸, 수업 계획서, 리뷰 */

    @Getter
    @AllArgsConstructor
    public static class ReviewListDTO {
        //선생님 아이디 외의 정보를 노출 시키지 않기 위한 클래스
        int star;
        String content;
        Date writeAt;
    }

    @Getter
    @AllArgsConstructor
    public static class InfoListDTO {
        String subject;
        int cost;
        String day;
        String start;
        String end;
        String detail;
        Date checkDate;
    }

    /** 스터디룸 개설하기 && 수업 계획서 등록하기 */
    public void roomRegister(String teacherId, String studentId, StudyroomInfoDTO studyroomInfoDTO) {

        Teacher teacher = userEntityRepository.teacherFindById(teacherId);
        Student student = userEntityRepository.studentFindById(studentId);

        StudyroomDTO studyroomDTO = new StudyroomDTO(teacher, student, Status.CLOSE);

        StudyRoom studyRoom = studyRoomRepository.roomRegister(studyRoomMapper.toEntity(studyroomDTO));
        studyroomInfoDTO.updateId(studyRoom);
        studyRoomRepository.infoRegister(studyroomInfoMapper.toEntity(studyroomInfoDTO));

    }
    /** 수업 계획서 수정하기 */
    public void roomUpdate(String teacherId, String studentId, StudyroomInfoDTO studyroomInfoDTO) {
        /** teacherid, studentid로 스터디룸 찾기 */
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId);
        studyroomInfoDTO.updateId(studyRoom);
        studyRoomRepository.infoUpdate(studyroomInfoMapper.toEntity(studyroomInfoDTO));
    }

    /** 수업 계획서 조회하기 */
    public InfoListDTO findStudyRoomInfo(String teacherId, String studentId) {
        StudyRoomInfo info = studyRoomRepository.findStudyRoomInfo(teacherId, studentId);
        return new InfoListDTO(info.getSubject(), info.getCost(), info.getDay(),
                info.getStart(), info.getEnd(), info.getDetail(), info.getCheckDate());
    }

    /** 선생님 - 수업 리뷰 조회하기 */
    public List<ReviewListDTO> findReviewByPostId(Long postId) {
        String teacherId = postFindRepository.findTeacherById(postId);
        List<PostReview> list = studyRoomRepository.findReviewByTeacher(teacherId);
        return list.stream()
                .map(m -> new ReviewListDTO(m.getStar(), m.getContent(), m.getWriteAt()))
                .collect(Collectors.toList());
    }

    /** 학생 - 수업 리뷰 작성하기 */
    public String writeReviewByStudyRoom(String teacherId, String studentId, PostreviewDTO postreviewDTO) {
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId);
        postreviewDTO.updateId(studyRoom);
        return studyRoomRepository.writeReview(postReviewMapper.toEntity(postreviewDTO));
    }
}
