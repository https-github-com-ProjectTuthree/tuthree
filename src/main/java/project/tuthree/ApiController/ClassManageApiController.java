package project.tuthree.ApiController;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.ApiController.ValidationGroups.PostExamTeacherGroup;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Grade;
import project.tuthree.dto.post.PostAnswerDTO;
import project.tuthree.dto.post.PostExamDTO;
import project.tuthree.dto.post.PostreviewDTO;
import project.tuthree.dto.post.PoststudyDTO;
import project.tuthree.dto.room.CalendarDTO;
import project.tuthree.dto.room.StudyroomDTO;
import project.tuthree.repository.CalendarRepository;
import project.tuthree.repository.PostStudyRepository;
import project.tuthree.repository.PostStudyRepository.StudyListDTO;
import project.tuthree.repository.StudyRoomRepository;
import project.tuthree.repository.UserFileRepository;
import project.tuthree.service.CalendarService;
import project.tuthree.service.CalendarService.CalendarListDTO;
import project.tuthree.service.CalendarService.calendarFullListDTO;
import project.tuthree.service.PostStudyService;
import project.tuthree.service.StudyRoomService;
import project.tuthree.service.StudyRoomService.StudyRoomListDTO;
import project.tuthree.service.StudyRoomService.childScheduleListDTO;
import project.tuthree.service.StudyRoomService.scheduleListDTO;

import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClassManageApiController {

    private final StudyRoomService studyRoomService;
    private final CalendarService calendarService;
    private final CalendarRepository calendarRepository;
    private final PostStudyService postStudyService;
    private final PostStudyRepository postStudyRepository;
    private final UserFileRepository userFileRepository;
    private final StudyRoomRepository studyRoomRepository;

    /** 학생, 선생, 자녀 스케쥴 조회 */
    @GetMapping("/users/schedule")
    public ExistDataSuccessResponse FindUserSchedule(@RequestParam("id") String id, @RequestParam("grade") String grade) throws JsonProcessingException, ParseException {
        log.debug("\n---- 스케쥴 조회 [USER ID : " + id+"] [GRADE : " + grade + "] ----\n");
        if (grade.toLowerCase(Locale.ROOT).equals(Grade.PARENT.getStrType())) {
            List<childScheduleListDTO> childSchedule = studyRoomService.findChildSchedule(id);
            return new ExistDataSuccessResponse(StatusCode.OK.getCode(), "자녀 일정이 조회되었습니다.", childSchedule);
        } else{
            List<scheduleListDTO> teacherSchedule = studyRoomService.findSchedule(id, Grade.valueOf(grade.toUpperCase(Locale.ROOT)));
            return new ExistDataSuccessResponse(StatusCode.OK.getCode(),"일정이 조회되었습니다.", teacherSchedule);
        }
    }


    /** 선생님에 대한 리뷰 작성 */
    @PostMapping("/room/review")
    public NotExistDataResultResponse WriteStudyRoomReview(@RequestParam("teacherId") String teacherId,
                                                           @RequestParam("studentId") String studentId, @RequestBody @Valid PostreviewDTO postreviewDTO) {
        log.debug("\n---- 학생 리뷰 작성 [USER ID : " + studentId + "] ----\n");
        String teacherName = studyRoomService.writeReviewByStudyRoom(teacherId.trim(), studentId.trim(), postreviewDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(),
                teacherName + " 선생님에 대한 리뷰가 작성되었습니다.");
    }

    /** 계정별 스터디룸 찾기 - status : open, close -> studyroom info는 무조건 true */
    @GetMapping("/room")
    public ExistDataSuccessResponse FindStudyRoomByOne(@RequestParam("id") String id, @RequestParam("status") String status) throws JsonProcessingException, ParseException {
        log.debug("\n---- 개인 스터디룸 조회 [USER ID : " + id + "] [STATUS : " + status + "] ----\n");
        List<StudyRoomListDTO> studyroomDTOList = studyRoomService.findStudyRoomByOneId(id.trim(), Status.valueOf(status));
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                "회원님의 수업 목록을 조회했습니다.", studyroomDTOList);
    }

    /** 학부모 - 자녀의 스터디룸 찾기 */
    @GetMapping("/room/child")
    public ExistDataSuccessResponse FindStudyRoomByChild(@RequestParam("id") String id, @RequestParam("status") String status) throws JsonProcessingException {
        log.debug("\n---- 자녀 스터디룸 조회 [USER ID : " + id + "] [STATUS : " + status + "] ----\n");
        List<StudyRoomListDTO> RoomList = studyRoomService.findStudyRoomByParent(id, Status.valueOf(status));
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                "회원님의 자녀 수업 목록을 조회했습니다.", RoomList);
    }

    /** 특정 스터디룸 찾기 */
    @GetMapping("/room/find")
    public ExistDataSuccessResponse FindStudyRoomByIds(@RequestParam("teacherId") String teacherId, @RequestParam("studentId") String studentId) {
        log.debug("\n---- 스터디룸 조회 [TEACHER ID : " + teacherId + "] [STUDENT ID : " + studentId + "] ----\n");
        StudyroomDTO studyroomDTO = studyRoomService.findStudyRoomByIds(teacherId.trim(), studentId.trim());
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(), "스터디룸을 조회했습니다.", studyroomDTO);
    }

    /** 캘린더 전체 보기 (수업 보고서랑 전부 포함) -> 데이터 두개 반환하기 */
    @GetMapping("/room/calendar")
    public ExistDataSuccessResponse ListCalendarByStudyRoom(@RequestParam("teacherId") String teacherId, @RequestParam("studentId") String studentId) throws ParseException {
        //일정
        log.debug("\n---- 스터디룸 전체 일정 조회 [TEACHER ID : " + teacherId + "] [STUDENT ID : " + studentId + "] ----\n");
        List<calendarFullListDTO> listDTO = calendarService.findByStudyroomIds(teacherId.trim(), studentId.trim());
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),"해당 스터디룸의 일정을 조회했습니다.", listDTO);
    }

    /** 특정 날짜 일정 전체 보기 */
    @GetMapping("/room/calendar/date")
    public ExistDataSuccessResponse ListCalendarByDate(@RequestParam("teacherId") String teacherId,
                                                       @RequestParam("studentId") String studentId,@RequestParam("date") String date) throws ParseException {
        log.debug("\n---- 캘린더 조회 [TEACHER ID : " + teacherId + "] [STUDENT ID : " + studentId + "] [DATE : " + date + "]e ----\n");
        List<CalendarListDTO> dateList = calendarService.findByDate(teacherId.trim(), studentId.trim(), date.trim());
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),date + "의 일정을 조회했습니다.", dateList);
    }

    /** 특정 날짜 보고서 전체 보기 */
    @GetMapping("/room/post/date")
    public ExistDataSuccessResponse ListPostByDate(@RequestParam("teacherId") String teacherId,
                                                   @RequestParam("studentId") String studentId,@RequestParam("date") String date) throws ParseException {
        log.debug("\n---- 수업 보고서 조회 [TEACHER ID : " + teacherId + "] [STUDENT ID : " + studentId + "] [DATE : " + date + "]e ----\n");
        List<PostStudyService.postStudyListDTO> postStudyByDate = postStudyService.findPostStudyByDate(teacherId, studentId, date);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(), date + "의 수업 보고서를 조회했습니다.", postStudyByDate);
    }

    /** 일정 등록 */
    @PostMapping("/room/calendar")
    public NotExistDataResultResponse RegisterCalendarByStudyRoom(@RequestParam("teacherId") String teacherId,
                                                                  @RequestParam("studentId") String studentId, @RequestBody @Valid CalendarDTO calendarDTO) {
        log.debug("\n---- 일정 등록 [TEACHER ID : " + teacherId + "] [STUDENT ID : " + studentId + "] ----\n");
        Long registedId = calendarService.registerCalendar(teacherId.trim(), studentId.trim(), calendarDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(),registedId + "번 일정이 등록되었습니다.");
    }

    /** 일정 수정 */
    @PutMapping("/room/calendar/{cal_id}")
    public NotExistDataResultResponse UpdateCalendarById(@PathVariable("cal_id") Long id, @RequestBody @Valid CalendarDTO calendarDTO){
        log.debug("\n---- 일정 수정 [CAL ID : " + id + "] ----\n");
        Long updatedId = calendarService.updateCalendar(id, calendarDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), updatedId + "번 일정이 수정되었습니다");
    }

    /** 일정 삭제 */
    @DeleteMapping("/room/calendar/{cal_id}")
    public NotExistDataResultResponse DeleteCalendarById(@PathVariable("cal_id") Long id) {
        log.debug("\n---- 일정 삭제 [CAL ID : " + id + "] ----\n");
        Long deletedId = calendarRepository.deleteCalendar(id);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), deletedId + "번 일정이 삭제되었습니다.");
    }

    /** 수업 보고서 등록 */
    @PostMapping("/room/post")
    public NotExistDataResultResponse RegisterPostStudyByStudyRoom(@RequestParam("teacherId") String teacherId,
                                                                   @RequestParam("studentId") String studentId, @RequestBody @Valid PoststudyDTO poststudyDTO) {
        log.debug("\n---- 수업 보고서 등록 [TEACHER ID : " + teacherId + "] [STUDENT ID : " + studentId + "] ----\n");
        Long registeredId = postStudyService.registerPost(teacherId.trim(), studentId.trim(), poststudyDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), registeredId + "번 보고서가 등록되었습니다.");
    }

    /** 특정 수업 보고서 조회 */
    @GetMapping("/room/post/{post_id}")
    public ExistDataSuccessResponse FindPostById(@PathVariable("post_id") Long id) throws ParseException {
        log.debug("\n---- 수업 보고서 조회 [POST ID : " + id + "] ----\n");
        StudyListDTO postById = postStudyService.findPostById(id);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                postById.getId() + "번 보고서를 조회하였습니다.", postById);
    }

    /** 수업 보고서 수정 */
    @PutMapping("/room/post/{post_id}")
    public NotExistDataResultResponse UpdatePostById(@PathVariable("post_id") Long id, @RequestBody @Valid PoststudyDTO poststudyDTO) {
        log.debug("\n---- 수업 보고서 수정 [POST ID : " + id + "] ----\n");
        Long updatedId = postStudyService.updatePost(id, poststudyDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(),updatedId + "번 보고서가 수정되었습니다.");
    }

    /** 수업 보고서 삭제 */
    @DeleteMapping("/room/post/{post_id}")
    public NotExistDataResultResponse DeletePostById(@PathVariable("post_id") Long id) {
        log.debug("\n---- 수업 보고서 삭제 [POST ID : " + id + "] ----\n");
        Long deletedId = postStudyRepository.deletePost(id);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), deletedId + "번 보고서가 삭제되었습니다.");
    }

    /** 시험지 목록 불러오기 */
    @GetMapping("/room/exam")
    public ExistDataSuccessResponse ListExamByStudyRoom(@RequestParam("teacherId") String teacherId, @RequestParam("studentId") String studentId) throws IOException {
        log.debug("\n---- 시험지 목록 조회 [TEACHER ID : " + teacherId + "] [STUDENT ID : " + studentId + "] ----\n");
        List<StudyRoomService.examListDTO> listDto = studyRoomService.listTestPaper(teacherId.trim(), studentId.trim());
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(), "스터디룸 시험지 목록을 불러왔습니다.", listDto);
    }

    /** 시험지 등록 */ //form-data
    @PostMapping("/room/exam")
    public NotExistDataResultResponse RegisterExamByStudyRoom(@RequestParam("teacherId") String teacherId,
                                        @RequestParam("studentId") String studentId, @ModelAttribute MultipartFile file) throws NoSuchAlgorithmException, IOException, HttpMediaTypeNotAcceptableException {
        log.debug("\n---- 시험지 등록 [TEACHER ID : " + teacherId + "] [STUDENT ID : " + studentId + "] ----\n");
        Long id = studyRoomService.saveTestPaper(teacherId.trim(), studentId.trim(), file);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "번 문제지가 등록되었습니다. : ");
    }

    /** 시험지 삭제 */
    @DeleteMapping("/room/exam/{post_id}")
    public NotExistDataResultResponse DeleteTestPaper(@PathVariable("post_id") Long id) {
        log.debug("\n---- 시험지 삭제 [POST ID : " + id + "] ----\n");
        Long deletedId = userFileRepository.deleteUserFile(id);
        /** 관련 답변 삭제하는 로직 추가하기 */
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), deletedId + "번 문제지가 삭제되었습니다.");
    }

    /**
     * 답안지 등록
     * @param id 문제지 번호
     * @param grade teacher / student (대문자, 소문자 상관없음) (선생님 답안지 입력, 학생 답안지 입력)
     * @param postExamDTO 답안
     */
    @PostMapping(value = "/room/exam/{post_id}")
    public NotExistDataResultResponse RegisterAnswerById(@PathVariable("post_id") Long id, @RequestParam("grade") String grade,
                                                         @RequestBody @Validated(PostExamTeacherGroup.class) PostExamDTO postExamDTO) throws NoSuchAlgorithmException, IOException {
        log.debug("\n---- 답안지 등록 [POST ID : " + id + "] [GRADE : " + grade + "] ----\n");
        String fileName = studyRoomService.saveRealAnswer(id, grade.trim(), postExamDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "번 문제지에 대한 답안이 입력되었습니다. : " + fileName);
    }

    /** 답안 수정하기 */

    /** 학생 답안지 등록을 위한 답안지 반환 */
    @GetMapping("/room/exam/answer/{post_id}")
    public ExistDataSuccessResponse SendRealAnswer(@PathVariable("post_id") Long id) throws IOException {
        log.debug("\n---- 답안지 조회 [POST ID : " + id + "] ----\n");
        PostExamDTO postExamDTO = studyRoomService.sendRealAnswer(id);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),id + "번 문제지의 답안이 조회되었습니다.", postExamDTO);
    }

    /** 정답 비교 확인 - repository에서 바로 처리 , service 붙이기에는 복잡해짐 */
    @GetMapping("/room/exam/{post_id}")
    public ExistDataSuccessResponse CheckRealAnswer(@PathVariable("post_id") Long id) throws IOException {
        log.debug("\n---- 답안지 채점 [POST ID : " + id + "] ----\n");
        PostAnswerDTO postAnswerDTO = studyRoomRepository.scoreTestPaper(id);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(), id + "번 문제의 답안을 채점했습니다.", postAnswerDTO);
    }
}
