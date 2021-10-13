package project.tuthree.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import project.tuthree.dto.post.PostExamDTO;
import project.tuthree.dto.post.PostreviewDTO;
import project.tuthree.dto.room.StudyroomDTO;
import project.tuthree.dto.room.StudyroomInfoDTO;
import project.tuthree.mapper.PostReviewMapper;
import project.tuthree.mapper.StudyRoomMapper;
import project.tuthree.mapper.StudyroomInfoMapper;
import project.tuthree.repository.PostFindRepository;
import project.tuthree.repository.StudyRoomRepository;
import project.tuthree.repository.UserEntityRepository;

import java.io.File;
import java.io.IOException;
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
        String cost;
        String day;
        String start;
        String end;
        String detail;
        Date checkDate;
    }

    @Getter
    @AllArgsConstructor
    public static class RoomListDTO {
        String teacherId;
        String studentId;
        Status status;
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
    public void findStudyRoomInfo(String teacherId, String studentId) {
//        StudyRoomInfo info = studyRoomRepository.findStudyRoomInfo(teacherId, studentId);
//        return new InfoListDTO(info.getSubject(), info.getCost(), info.getDay(),
//                info.getStart(), info.getEnd(), info.getDetail(), info.getCheckDate());
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
    public String writeReviewByStudyRoom(PostreviewDTO postreviewDTO) {
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(postreviewDTO.getId().getTeacherId().getId(), postreviewDTO.getId().getStudentId().getId());
        postreviewDTO.updateId(studyRoom);
        return studyRoomRepository.writeReview(postReviewMapper.toEntity(postreviewDTO));
    }

    /** 아이디 하나로 스터디룸 찾기 */
    public List<StudyroomDTO> findStudyRoomByOneId(String id, Status status) {
        List<StudyRoom> studyRoomList = studyRoomRepository.findStudyRoomByOneId(id, status);
        return studyRoomList.stream()
                .map(m -> studyRoomMapper.toDto(m))
                .collect(Collectors.toList());
    }

    /** 특정 스터디룸 찾기 */
    public StudyroomDTO findStudyRoomByIds(String teacherId, String studentId) {
        StudyRoom studyRoomDTO = studyRoomRepository.findStudyRoomById(teacherId, studentId);
        return studyRoomMapper.toDto(studyRoomDTO);
    }

    /** 시험지 등록 */

    /** 시험지 답안 등록 - 아래 함수랑 합치기 */

    /** 시험지 학생 답안 등록 */
    public void saveStudentResponse(PostExamDTO postExamDTO) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("/home/seojaehui/tuthree/var/file"), postExamDTO);

        //시험지 파일 이름 들고오기
    }

    /** 정답 비교 확인 -> json 둘다 객체로 바꿔서 값 비교하고 o, x json으로 변환해서 내보내기 */
}
