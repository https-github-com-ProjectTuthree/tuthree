package project.tuthree.ApiController;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.ExistDoubleDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.domain.Status;
import project.tuthree.dto.post.PostAnswerDTO;
import project.tuthree.dto.post.PostExamDTO;
import project.tuthree.dto.post.PostreviewDTO;
import project.tuthree.dto.post.PoststudyDTO;
import project.tuthree.dto.room.CalendarDTO;
import project.tuthree.dto.room.StudyroomDTO;
import project.tuthree.dto.room.StudyroomInfoDTO;
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
import project.tuthree.testlogic.ArrayToJson;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


    @PostMapping("/room/review")
    public NotExistDataResultResponse WriteStudyRoomReview(@RequestParam("teacherId") String teacherId,
                                                           @RequestParam("studentId") String studentId, @RequestBody @Valid PostreviewDTO postreviewDTO) {
        String teacherName = studyRoomService.writeReviewByStudyRoom(teacherId, studentId, postreviewDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(),
                teacherName + " 선생님에 대한 리뷰가 작성되었습니다.");
    }

    /** 계정별 스터디룸 찾기 - status : open, close */
    @GetMapping("/room")
    public ExistDataSuccessResponse FindStudyRoomByOne(@RequestParam("id") String id, @RequestParam("status") String status) throws JsonProcessingException {
        List<StudyRoomListDTO> studyroomDTOList = studyRoomService.findStudyRoomByOneId(id, Status.valueOf(status));
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                "회원님의 수업 목록을 조회했습니다.", studyroomDTOList);
    }

    /** 특정 스터디룸 찾기 */
    @GetMapping("/room/find")
    public ExistDataSuccessResponse FindStudyRoomByIds(@RequestParam("teacherId") String teacherId, @RequestParam("studentId") String studentId) {
        StudyroomDTO studyroomDTO = studyRoomService.findStudyRoomByIds(teacherId, studentId);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(), "스터디룸을 조회했습니다.", studyroomDTO);
    }

    /** 캘린더 전체 보기 (수업 보고서랑 전부 포함) -> 데이터 두개 반환하기 */
    @GetMapping("/room/calendar")
    public ExistDataSuccessResponse ListCalendarByStudyRoom(@RequestParam("teacherId") String teacherId, @RequestParam("studentId") String studentId) throws ParseException {
        //일정
        List<calendarFullListDTO> listDTO = calendarService.findByStudyroomIds(teacherId, studentId);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),"해당 스터디룸의 일정을 조회했습니다.", listDTO);
    }

    /** 특정 날짜 일정 전체 보기 */
    @GetMapping("/room/calendar/date")
    public ExistDataSuccessResponse ListCalendarByDate(@RequestParam("teacherId") String teacherId,
                                                       @RequestParam("studentId") String studentId,@RequestParam("date") String date) throws ParseException {
        List<CalendarListDTO> dateList = calendarService.findByDate(teacherId, studentId, date);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),date + "의 일정을 조회했습니다.", dateList);
    }

    /** 일정 등록 */
    @PostMapping("/room/calendar")
    public NotExistDataResultResponse RegisterCalendarByStudyRoom(@RequestParam("teacherId") String teacherId,
                                                                  @RequestParam("studentId") String studentId, @RequestBody CalendarDTO calendarDTO) {
        Long registedId = calendarService.registerCalendar(teacherId, studentId, calendarDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(),registedId + "번 일정이 등록되었습니다.");
    }

    /** 일정 수정 */
    @PutMapping("/room/calendar/{cal_id}")
    public NotExistDataResultResponse UpdateCalendarById(@PathVariable("cal_id") Long id, @RequestBody CalendarDTO calendarDTO){
        Long updatedId = calendarService.updateCalendar(id, calendarDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), updatedId + "번 일정이 수정되었습니다");
    }

    /** 일정 삭제 */
    @DeleteMapping("/room/calendar/{cal_id}")
    public NotExistDataResultResponse DeleteCalendarById(@PathVariable("cal_id") Long id) {
        Long deletedId = calendarRepository.deleteCalendar(id);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), deletedId + "번 일정이 삭제되었습니다.");
    }

    /** 수업 보고서 등록 */
    @PostMapping("/room/post")
    public NotExistDataResultResponse RegisterPostStudyByStudyRoom(@RequestParam("teacherId") String teacherId,
                                                                   @RequestParam("studentId") String studentId, @RequestBody PoststudyDTO poststudyDTO) {
        Long registeredId = postStudyService.registerPost(teacherId, studentId, poststudyDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), registeredId + "번 보고서가 등록되었습니다.");
    }

    /** 특정 수업 보고서 조회 */
    @GetMapping("/room/post/{post_id}")
    public ExistDataSuccessResponse FindPostById(@PathVariable("post_id") Long id) {
        StudyListDTO postById = postStudyService.findPostById(id);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                postById.getId() + "번 보고서를 조회하였습니다.", postById);
    }

    /** 수업 보고서 수정 */
    @PutMapping("/room/post/{post_id}")
    public NotExistDataResultResponse UpdatePostById(@PathVariable("post_id") Long id, @RequestBody PoststudyDTO poststudyDTO) {
        Long updatedId = postStudyService.updatePost(id, poststudyDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(),updatedId + "번 보고서가 수정되었습니다.");
    }

    /** 수업 보고서 삭제 */
    @DeleteMapping("/room/post/{post_id}")
    public NotExistDataResultResponse DeletePostById(@PathVariable("post_id") Long id) {
        Long deletedId = postStudyRepository.deletePost(id);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), deletedId + "번 보고서가 삭제되었습니다.");
    }

    /** 시험지 목록 불러오기 */
    @GetMapping("/room/exam")
    public ExistDataSuccessResponse ListExamByStudyRoom(@RequestParam("teacherId") String teacherId, @RequestParam("studentId") String studentId) throws IOException {
        List<StudyRoomService.examListDTO> listDto = studyRoomService.listTestPaper(teacherId, studentId);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(), "스터디룸 시험지 목록을 불러왔습니다.", listDto);
    }

    /** 시험지 등록 */ //form-data
    @PostMapping("/room/exam")
    public NotExistDataResultResponse RegisterExamByStudyRoom(@RequestParam("teacherId") String teacherId,
                                        @RequestParam("studentId") String studentId, @ModelAttribute MultipartFile file) throws NoSuchAlgorithmException, IOException, HttpMediaTypeNotAcceptableException {
        Long id = studyRoomService.saveTestPaper(teacherId, studentId, file);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "번 문제지가 등록되었습니다. : ");
    }

    /** 시험지 삭제 */
    @DeleteMapping("/room/exam/{post_id}")
    public NotExistDataResultResponse DeleteTestPaper(@PathVariable("post_id") Long id) {
        Long deletedId = userFileRepository.deleteUserFile(id);
        /** 관련 답변 삭제하는 로직 추가하기 */
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), deletedId + "번 문제지가 삭제되었습니다.");
    }

    /**
     *
     * @param id 문제지 번호
     * @param grade teacher / student (대문자, 소문자 상관없음) (선생님 답안지 입력, 학생 답안지 입력)
     * @param postExamDTO 답안
     */
    @PostMapping(value = "/room/exam/{post_id}")
    public NotExistDataResultResponse RegisterAnswerById(@PathVariable("post_id") Long id, @RequestParam("grade") String grade,
                                                         @RequestBody PostExamDTO postExamDTO) throws NoSuchAlgorithmException, IOException {
        String fileName = studyRoomService.saveRealAnswer(id, grade, postExamDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "번 문제지에 대한 답안이 입력되었습니다. : " + fileName);
    }

    /** 정답 비교 확인 - repository에서 바로 처리 , service 붙이기에는 복잡해짐 */
    @GetMapping("/room/exam/{post_id}")
    public ExistDataSuccessResponse CheckAnswer(@PathVariable("post_id") Long id) throws IOException {
        PostAnswerDTO postAnswerDTO = studyRoomRepository.scoreTestPaper(id);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                id + "번 문제의 답안을 채점했습니다.", postAnswerDTO);
    }
}
