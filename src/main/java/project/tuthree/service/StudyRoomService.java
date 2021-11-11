package project.tuthree.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.Tuple;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.ApiController.EmbeddedResponse.NotExistBadDataResultResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.ApiController.StatusCode;
import project.tuthree.domain.Status;
import project.tuthree.domain.file.UserFile;
import project.tuthree.domain.post.PostReview;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.domain.room.StudyRoomInfo;
import project.tuthree.domain.user.*;
import project.tuthree.dto.file.UserfileDTO;
import project.tuthree.dto.post.PostAnswerDTO;
import project.tuthree.dto.post.PostExamDTO;
import project.tuthree.dto.post.PostreviewDTO;
import project.tuthree.dto.room.StudyroomDTO;
import project.tuthree.dto.room.StudyroomInfoDTO;
import project.tuthree.exception.ExceptionSupplierImpl;
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
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static project.tuthree.repository.UserFileRepository.FileType.POSTPAPER;

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

    /** 스터디룸 개설하기 && 수업 계획서 등록하기 */
    public void roomRegister(String teacherId, String studentId, StudyroomInfoDTO studyroomInfoDTO) throws JsonProcessingException {

        Teacher teacher = userEntityRepository.teacherFindById(teacherId);
        Student student = userEntityRepository.studentFindById(studentId);

        StudyroomDTO studyroomDTO = new StudyroomDTO(teacher, student, Status.CLOSE);

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
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId, true, true);
        studyroomInfoDTO.updateId(studyRoom);

        Map<String, Object> info = new HashMap<>();
        info.put("subject", studyroomInfoDTO.getSubject());
        info.put("schedule", studyroomInfoDTO.getSchedule());
        studyroomInfoDTO.insertBlob(objectToByte(info));

        studyRoomRepository.infoUpdate(studyroomInfoMapper.toEntity(studyroomInfoDTO));
    }

    /** 수업 계획서 조회하기 */
    public InfoListDTO findStudyRoomInfo(String teacherId, String studentId) throws JsonProcessingException {
        StudyRoomInfo info = studyRoomRepository.findStudyRoomInfo(teacherId, studentId, true, true);
        Map<String, Object> map = byteToObject(info.getInfo());
        return new InfoListDTO(info.getCost(), map, info.getDetail(), info.getCheckDate());
    }

    /** 스케쥴 조회하기 */
    public List<scheduleListDTO> findSchedule(String id, Grade grade) throws ParseException, JsonProcessingException {
        List<StudyRoomInfo> studyRoomSchedule = studyRoomRepository.findStudyRoomSchedule(id, grade);
        List<scheduleListDTO> list = new ArrayList<>();
        if(grade.equals(Grade.TEACHER)){
            for (StudyRoomInfo i : studyRoomSchedule) {
                list.add(new scheduleListDTO(i.getId().getStudentId().getName(),
                        byteToObject(i.getInfo()).get("schedule")));
            }
        } else if(grade.equals(Grade.STUDENT)) {
            for (StudyRoomInfo i : studyRoomSchedule) {
                list.add(new scheduleListDTO(i.getId().getTeacherId().getName(),
                        byteToObject(i.getInfo()).get("schedule")));
            }
        }
        return list;
    }

    /** 부모의 자녀 스케쥴 조회 */
    public List<childScheduleListDTO> findChildSchedule(String id) throws ParseException, JsonProcessingException {
        List<Tuple> tuples = userEntityRepository.userFindChildIdandName(id);
        List<childScheduleListDTO> list = new ArrayList<>();
        for(Tuple t : tuples) {
            log.info("================child :", t.get(0, String.class));
            List<scheduleListDTO> schedule = findSchedule(t.get(1,String.class), Grade.STUDENT);
            list.add(new childScheduleListDTO(t.get(0, String.class), schedule));
        }
        return list;
    }

    /** 선생님 - 수업 리뷰 조회하기 */
    public List<ReviewListDTO> findReviewByPostId(Long postId, String sort) {
        String teacherId = postFindRepository.findTeacherById(postId);
        List<PostReview> list = studyRoomRepository.findReviewByTeacher(teacherId, sort);
        return list.stream()
                .map(m -> ExceptionSupplierImpl.wrap(() -> new ReviewListDTO(m.getId().getStudentId().getId(), m.getStar(), m.getContent(),
                        userFileRepository.unixToDate(m.getWriteAt()))))
                .collect(Collectors.toList());
    }

    /** 학생 - 수업 리뷰 작성하기 */
    public String writeReviewByStudyRoom(String teacherId, String studentId, PostreviewDTO postreviewDTO) {
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId, true, false);
        postreviewDTO.updateId(studyRoom);
        return studyRoomRepository.writeReview(postReviewMapper.toEntity(postreviewDTO));
    }

    /** 아이디 하나로 스터디룸 찾기 */
    public List<StudyRoomListDTO> findStudyRoomByOneId(String id, Status status) throws JsonProcessingException, ParseException {
        //studyroom info는 무조건 true

        List<StudyRoom> studyRoomList = studyRoomRepository.findStudyRoomByOneId(id, status);
        List<StudyRoomListDTO> list = new ArrayList<>();

        for(StudyRoom s : studyRoomList) {
            StudyRoomInfo studyRoomInfo = studyRoomRepository.findStudyRoomInfo(s.getTeacherId().getId(), s.getStudentId().getId(), true, true);
            Object object = byteToObject(studyRoomInfo.getInfo()).get("subject");
            List<String> subject = objectMapper.convertValue(object, List.class);
            StudyRoomListDTO dto = new StudyRoomListDTO(s.getTeacherId().getId(), s.getTeacherId().getName(), s.getStudentId().getId(),
                    s.getStudentId().getName(), userFileRepository.unixToDate(studyRoomInfo.getCheckDate()), subject, s.getStatus());
            list.add(dto);
        }

        return list;
    }

    /** 학부모 - 자녀의 스터디룸 찾기 */
    public List<StudyRoomListDTO> findStudyRoomByParent(String id, Status status) throws JsonProcessingException {
        List<StudyRoomListDTO> list = new ArrayList<>();
        //자녀 아이디 목록
        List<String> childIds = userEntityRepository.userFindChild(id);
        //자녀 아이디별 스터디룸 목록
        for (String s : childIds) {
            try{
                List<StudyRoomListDTO> studyRoomByOneId = findStudyRoomByOneId(s, status);
                studyRoomByOneId.stream().forEach(m -> list.add(m));
            }catch (NullPointerException | ParseException e){
                e.printStackTrace();
            }
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
        StudyRoom studyRoomDTO = studyRoomRepository.findStudyRoomById(teacherId, studentId, true, false);
        if(studyRoomDTO == null){
            throw new NullPointerException();
        }
        return studyRoomMapper.toDto(studyRoomDTO);
    }

    /** 시험지 목록 불러오기 */
    public List<examListDTO> listTestPaper(String teacherId, String studentId) throws IOException {
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId, true, false);
        List<UserFile> fileList = studyRoomRepository.listTestPaper(studyRoom);
        List<examListDTO> list = new ArrayList<>();
        for (UserFile l : fileList) {
            byte[] bytes = userFileRepository.transferUserFile(l.getSaveTitle());
            examListDTO dto = new examListDTO(l.getId(), l.getRealTitle(), bytes);
            list.add(dto);
        }
        return list;
    }

    /** 시험지 등록 pdf 밖에 안들어옴 */
    public Long saveTestPaper(String teacherId, String studentId, MultipartFile file) throws NoSuchAlgorithmException, IOException, HttpMediaTypeNotAcceptableException {
        if(!file.getContentType().equals("application/pdf")){
            throw new HttpMediaTypeNotAcceptableException("pdf 형식의 파일을 입력해주세요.");
        }
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId, true, false);
        String[] names = file.getOriginalFilename().split("\\.pdf");

        Long count = studyRoomRepository.findSameNameTestPaper(studyRoom, names[0]);
        String real_title = (count > 0) ? names[0] + "(" + count + ")" : names[0];
        String save_title = userFileRepository.saveFile(file, POSTPAPER);
        UserfileDTO userfileDTO = new UserfileDTO(studyRoom, save_title, real_title + ".pdf");
        return userFileRepository.userFileSave(userFileMapper.toEntity(userfileDTO));
    }

    /** 시험지 답안 반환 */
    public PostExamDTO sendRealAnswer(Long id) throws IOException {
        UserFile userFile = userFileRepository.userFileFindByFileId(id);
        String name = userFile.getRealTitle();

        if(name.contains(".pdf")) {
            name = name.split("\\.pdf")[0];
        }
        return objectMapper.convertValue(studyRoomRepository.findExamByIdnName(userFile.getStudyRoomId(), name + "_teacher_answer.json"), PostExamDTO.class);
    }

    public PostAnswerDTO sendTestScore(Long id) throws IOException {
        UserFile userFile = userFileRepository.userFileFindByFileId(id);
        String name = userFile.getRealTitle();

        if(name.contains(".pdf")) {
            name = name.split("\\.pdf")[0];
        }
        return objectMapper.convertValue(studyRoomRepository.findExamByIdnName(userFile.getStudyRoomId(), name + "_answer.json"), PostAnswerDTO.class);
    }

    /** 시험지 답안 등록 */
    public String saveRealAnswer(Long id, String grade, PostExamDTO postExamDTO) throws NoSuchAlgorithmException, IOException {
        //id에 해당하는 파일 이름 찾기
        UserFile userFile = userFileRepository.userFileFindByFileId(id);
        String real = userFile.getRealTitle();
        if(real.contains(".pdf")){
            real = real.split("\\.pdf")[0];
        }
        //파일 이름_answer로 파일 이름 저장하기
        String saveName = "";
        if(Grade.valueOf(grade.toUpperCase(Locale.ROOT)).equals(Grade.TEACHER) || Grade.valueOf(grade.toUpperCase(Locale.ROOT)).equals(Grade.STUDENT)){
            saveName = real + "_" + grade.toLowerCase(Locale.ROOT) + "_answer.json";
        }
        boolean notExist = studyRoomRepository.findSameNameTestPaper(userFile.getStudyRoomId(), saveName) == 0;
        if(notExist){
            String saved = userFileRepository.saveJsonFile(postExamDTO, saveName, POSTPAPER);
            //user_file 테이블에 저장하기
            UserfileDTO userfileDTO = new UserfileDTO(userFile.getStudyRoomId(), saved, saveName);
            userFileRepository.userFileSave(userFileMapper.toEntity(userfileDTO));
            if (Grade.valueOf(grade.toUpperCase(Locale.ROOT)).equals(Grade.STUDENT)) {
                PostAnswerDTO postAnswerDTO = studyRoomRepository.scoreTestPaper(id);
                String answer = userFileRepository.saveJsonFile(postAnswerDTO, real + "_answer.json", POSTPAPER);
                UserfileDTO userfileDTO1 = new UserfileDTO(userFile.getStudyRoomId(), answer, real + "_answer.json");
                userFileRepository.userFileSave(userFileMapper.toEntity(userfileDTO1));
            }
            return saveName;
        }
        throw new IllegalArgumentException("이미 입력된 답안지가 존재합니다.");
    }

    public Long updateTestScore(Long id, PostAnswerDTO answerDTO) throws IOException {
        UserFile userFile = userFileRepository.userFileFindByFileId(id);
        String name = userFile.getRealTitle().split("\\.pdf")[0];
        UserFile userFile1 = userFileRepository.userFileFindByStudyroomnName(userFile.getStudyRoomId(), name + "_answer.json");
        userFileRepository.saveNewFile(userFile1.getSaveTitle(), answerDTO);
        return userFile1.getId();
    }


    @Getter
    @AllArgsConstructor
    public static class ReviewListDTO {
        //선생님 아이디 외의 정보를 노출 시키지 않기 위한 클래스
        String userId;
        int star;
        String content;
        String writeAt;
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
        String teacherName;
        String studentId;
        String studentName;
        String date;
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

    @Getter
    @AllArgsConstructor
    public static class scheduleListDTO {
        String name;
        Object schedule;
    }

    @Getter
    @AllArgsConstructor
    public static class childScheduleListDTO {
        String childName;
        List<scheduleListDTO> list;

    }

}
