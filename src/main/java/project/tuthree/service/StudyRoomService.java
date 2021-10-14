package project.tuthree.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.ApiController.EmbeddedResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistBadDataResultResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.ApiController.StatusCode;
import project.tuthree.domain.Status;
import project.tuthree.domain.file.UserFile;
import project.tuthree.domain.post.PostReview;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.domain.room.StudyRoomInfo;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.Student;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.file.UserfileDTO;
import project.tuthree.dto.post.PostExamDTO;
import project.tuthree.dto.post.PostreviewDTO;
import project.tuthree.dto.room.StudyroomDTO;
import project.tuthree.dto.room.StudyroomInfoDTO;
import project.tuthree.mapper.PostReviewMapper;
import project.tuthree.mapper.StudyRoomMapper;
import project.tuthree.mapper.StudyroomInfoMapper;
import project.tuthree.mapper.UserFileMapper;
import project.tuthree.repository.PostFindRepository;
import project.tuthree.repository.StudyRoomRepository;
import project.tuthree.repository.UserEntityRepository;
import project.tuthree.repository.UserFileRepository;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.*;
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
    private final ObjectMapper objectMapper;
    private final UserFileRepository userFileRepository;
    private final UserFileMapper userFileMapper;

    /** 스터디룸, 수업 계획서, 리뷰 */

    @Getter
    @AllArgsConstructor
    public static class ReviewListDTO {
        //선생님 아이디 외의 정보를 노출 시키지 않기 위한 클래스
        String userId;
        int star;
        String content;
        Date writeAt;
    }

    @Getter
    @AllArgsConstructor
    public static class InfoListDTO {
        String cost;
        Object info;
        String detail;
        Date checkDate;
    }

    @Getter
    @AllArgsConstructor
    public static class StudyRoomListDTO {
        String teacherId;
        String studentId;
        Date date;
        List<String> subject;
        Status status;
    }

    @Getter
    @AllArgsConstructor
    public static class examListDTO {
        Long id;
        String title;
        byte[] file;
    }

    /** 스터디룸 개설하기 && 수업 계획서 등록하기 */
    public void roomRegister(String teacherId, String studentId, StudyroomInfoDTO studyroomInfoDTO) throws JsonProcessingException {

        Teacher teacher = userEntityRepository.teacherFindById(teacherId);
        Student student = userEntityRepository.studentFindById(studentId);
        log.info("===========" + teacherId + " " + studentId);

        StudyroomDTO studyroomDTO = new StudyroomDTO(teacher, student, Status.CLOSE);
        /** dto subejct, day, start, end json으로 변경하여 byte 변경해서 넣기 */

        Map<String, Object> info = new HashMap<>();
        info.put("subject", studyroomInfoDTO.getSubject());
        info.put("schedule", studyroomInfoDTO.getSchedule());
        studyroomInfoDTO.insertBlob(objectToByte(info));

        StudyRoom studyRoom = studyRoomRepository.roomRegister(studyRoomMapper.toEntity(studyroomDTO));

        studyroomInfoDTO.updateId(studyRoom);
        studyRoomRepository.infoRegister(studyroomInfoMapper.toEntity(studyroomInfoDTO));

    }

    public byte[] objectToByte(Object map) throws JsonProcessingException {
        String value = objectMapper.writeValueAsString(map);
        return value.getBytes(StandardCharsets.UTF_8);
    }

    public Map<String,Object> byteToObject(byte[] value) throws JsonProcessingException {
        String str = new String(value);
        return objectMapper.readValue(str, HashMap.class);
    }

    /** 수업 계획서 수정하기 */
    public void roomUpdate(String teacherId, String studentId, StudyroomInfoDTO studyroomInfoDTO) throws JsonProcessingException {
        /** teacherid, studentid로 스터디룸 찾기 */
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId);
        studyroomInfoDTO.updateId(studyRoom);

        Map<String, Object> info = new HashMap<>();
        info.put("subject", studyroomInfoDTO.getSubject());
        info.put("schedule", studyroomInfoDTO.getSchedule());
        studyroomInfoDTO.insertBlob(objectToByte(info));

        studyRoomRepository.infoUpdate(studyroomInfoMapper.toEntity(studyroomInfoDTO));
    }

    /** 수업 계획서 조회하기 */
    public InfoListDTO findStudyRoomInfo(String teacherId, String studentId) throws JsonProcessingException {
        StudyRoomInfo info = studyRoomRepository.findStudyRoomInfo(teacherId, studentId);
        Map<String, Object> map = byteToObject(info.getInfo());
        return new InfoListDTO(info.getCost(), map, info.getDetail(), info.getCheckDate());
    }

    /** 선생님 - 수업 리뷰 조회하기 */
    public List<ReviewListDTO> findReviewByPostId(Long postId) {
        String teacherId = postFindRepository.findTeacherById(postId);
        List<PostReview> list = studyRoomRepository.findReviewByTeacher(teacherId);
        return list.stream()
                .map(m -> new ReviewListDTO(m.getId().getStudentId().getId(), m.getStar(), m.getContent(), m.getWriteAt()))
                .collect(Collectors.toList());
    }

    /** 학생 - 수업 리뷰 작성하기 */
    public String writeReviewByStudyRoom(String teacherId, String studentId, PostreviewDTO postreviewDTO) {
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId);
        postreviewDTO.updateId(studyRoom);
        return studyRoomRepository.writeReview(postReviewMapper.toEntity(postreviewDTO));
    }

    /** 아이디 하나로 스터디룸 찾기 */
    public List<StudyRoomListDTO> findStudyRoomByOneId(String id, Status status) throws JsonProcessingException {
        List<StudyRoom> studyRoomList = studyRoomRepository.findStudyRoomByOneId(id, status);
        List<StudyRoomListDTO> list = new ArrayList<>();

        for(StudyRoom s : studyRoomList) {
            StudyRoomInfo studyRoomInfo = studyRoomRepository.findStudyRoomInfo(s.getTeacherId().getId(), s.getStudentId().getId());
            Object object = byteToObject(studyRoomInfo.getInfo()).get("subject");
            List<String> subject = objectMapper.convertValue(object, List.class);
            StudyRoomListDTO dto = new StudyRoomListDTO(s.getTeacherId().getId(), s.getStudentId().getId(),
                    studyRoomInfo.getCheckDate(), subject, s.getStatus());
            list.add(dto);
        }

        return list;
    }

    /** 스터디룸 승낙하기 & 승낙 확인하기 & 스터디룸 개설하기 */
    public Object studyRoomIsAccept(String teacherId, String studentId, String grade) {
        if (grade.toLowerCase(Locale.ROOT).equals(Grade.TEACHER.getStrType())) {

            if(studyRoomRepository.isAcceptInfo(teacherId, studentId) == true){
                return new NotExistDataResultResponse(StatusCode.OK.getCode(), "수업이 성사되었습니다.");
            }
            return new NotExistBadDataResultResponse(StatusCode.FORBIDDEN.getCode(), "수업이 성사되지 않았습니다.");
        } else if(studyRoomRepository.acceptInfo(teacherId, studentId) == false){
            return new NotExistDataResultResponse(StatusCode.FORBIDDEN.getCode(), "이미 성사된 수업입니다.");
        }
        studyRoomRepository.openStudyRoom(teacherId, studentId);
        return new NotExistDataResultResponse(StatusCode.OK.getCode(), "수업이 성사되었습니다.");
    }

    /** 특정 스터디룸 찾기 */
    public StudyroomDTO findStudyRoomByIds(String teacherId, String studentId) {
        StudyRoom studyRoomDTO = studyRoomRepository.findStudyRoomById(teacherId, studentId);
        if(studyRoomDTO == null){
            throw new NullPointerException();
        }
        return studyRoomMapper.toDto(studyRoomDTO);
    }

    /** 시험지 목록 불러오기 */
    public List<examListDTO> listTestPaper(String teacherId, String studentId) throws IOException {
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId);
        List<UserFile> fileList = studyRoomRepository.listTestPaper(studyRoom);
        List<examListDTO> list = new ArrayList<>();
        for (UserFile l : fileList) {
            byte[] bytes = userFileRepository.transferUserFile(l.getSaveTitle());
            examListDTO dto = new examListDTO(l.getId(), l.getRealTitle(), bytes);
            list.add(dto);
        }
        return list;
    }

    /** 시험지 등록 */
    public Long saveTestPaper(String teacherId, String studentId, MultipartFile file) throws NoSuchAlgorithmException, IOException {
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId);
        String save_title = userFileRepository.saveFile(file, UserFileRepository.FileType.POSTPAPER);
        UserfileDTO userfileDTO = new UserfileDTO(null, studyRoom, null, save_title, file.getOriginalFilename());
        return userFileRepository.userFileSave(userFileMapper.toEntity(userfileDTO));
    }

    /** 시험지 답안 등록 - 아래 함수랑 합치기 */
    public void saveRealAnswer(Long id, PostExamDTO postExamDTO) throws NoSuchAlgorithmException, IOException {
        //id에 해당하는 파일 이름 찾기
        UserFile userFile = userFileRepository.userFileFindById(id);
        String save = userFile.getRealTitle();
        //파일 이름_answer로 파일 이름 저장하기
        String saved = userFileRepository.saveJsonFile(postExamDTO, save + "_answer", UserFileRepository.FileType.POSTPAPER);
        UserfileDTO userfileDTO = new UserfileDTO(null, userFile.getStudyRoomId(), null, saved,save+"_answer");
        //user_file 테이블에 저장하기
        userFileRepository.userFileSave(userFileMapper.toEntity(userfileDTO));
    }

    /** 시험지 학생 답안 등록 */
    public Object saveStudentResponse(PostExamDTO postExamDTO) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("/home/seojaehui/tuthree/var/file"), postExamDTO);

        //시험지 파일 이름 들고오기
        return postExamDTO;
    }

    /** 정답 비교 확인 -> json 둘다 객체로 바꿔서 값 비교하고 o, x json으로 변환해서 내보내기 */
}
