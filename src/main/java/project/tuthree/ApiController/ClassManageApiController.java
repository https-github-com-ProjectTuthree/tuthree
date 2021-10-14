package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.ExistDoubleDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.domain.Status;
import project.tuthree.dto.post.PostExamDTO;
import project.tuthree.dto.post.PostreviewDTO;
import project.tuthree.dto.post.PoststudyDTO;
import project.tuthree.dto.room.CalendarDTO;
import project.tuthree.dto.room.StudyroomDTO;
import project.tuthree.dto.room.StudyroomInfoDTO;
import project.tuthree.repository.CalendarRepository;
import project.tuthree.repository.PostStudyRepository;
import project.tuthree.repository.PostStudyRepository.StudyListDTO;
import project.tuthree.service.CalendarService;
import project.tuthree.service.CalendarService.CalendarListDTO;
import project.tuthree.service.PostStudyService;
import project.tuthree.service.StudyRoomService;
import project.tuthree.testlogic.ArrayToJson;

import javax.validation.Valid;
import java.io.IOException;
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

    /** 404 not found가 뜨는 에러 - 동작은 잘하고 터미널에도 오류가 안 뜬다.. */
    @PostMapping("/room/review")
    public NotExistDataResultResponse WriteStudyRoomReview(@RequestBody @Valid PostreviewDTO postreviewDTO) {
        String teacherName = studyRoomService.writeReviewByStudyRoom(postreviewDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(),
                teacherName + " 선생님에 대한 리뷰가 작성되었습니다.");
    }

    /** 계정별 스터디룸 찾기 - status : open, close */
    @GetMapping("/room")
    public ExistDataSuccessResponse FindStudyRoomByOne(@RequestParam("id") String id, @RequestParam("status") Status status) {
        List<StudyroomDTO> studyroomDTOList = studyRoomService.findStudyRoomByOneId(id, status);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                "회원님의 수업 목록을 조회했습니다.", studyroomDTOList);
    }

    @GetMapping("/room/find")
    /** 특정 스터디룸 찾기 */
    public ExistDataSuccessResponse FindStudyRoomByIds(@RequestParam("teacherId") String teacherId, @RequestParam("studentId") String studentId) {
        StudyroomDTO studyroomDTO = studyRoomService.findStudyRoomByIds(teacherId, studentId);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                "스터디룸을 조회했습니다.", studyroomDTO);
    }

    /** 캘린더 전체 보기 (수업 보고서랑 전부 포함) -> 데이터 두개 반환하기 */
    @GetMapping("/room/calendar")
    public ExistDoubleDataSuccessResponse ListCalendarByStudyRoom(@RequestParam("teacherId") String teacherId, @RequestParam("studentId") String studentId) {
        //일정
        List<CalendarDTO> calendarDTOList = calendarService.findByStudyroomIds(teacherId, studentId);
        //보고서
        List<StudyListDTO> postStudy = postStudyService.findPostByStudyRoom(teacherId, studentId);
        return new ExistDoubleDataSuccessResponse(StatusCode.OK.getCode(),
                "해당 스터디룸의 일정을 조회했습니다.", calendarDTOList, postStudy);
    }

    /** 특정 날짜 일정 전체 보기 */
    @GetMapping("/room/calendar/date")
    public ExistDataSuccessResponse ListCalendarByDate(@RequestParam("teacherId") String teacherId,
                                                       @RequestParam("studentId") String studentId,@RequestParam("date") String date) throws ParseException {

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date to = transFormat.parse(date);
        List<CalendarListDTO> dateList = calendarService.findByDate(teacherId, studentId, to);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                date + "의 일정을 조회했습니다.", dateList);
    }

    /**
     * 일정 등록
     */
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

    /** 시험지 등록 */ //form-data
    @PostMapping("/room/exam")
    public void RegisterExamByStudyRoom(@RequestParam("teacherId") String teacherId,
                                        @RequestParam("studentId") String studentId, @ModelAttribute MultipartFile file) {

    }

    /** 시험지 답안 등록 */
    /** 시험지 학생 답안 등록 */
    @PostMapping("/room/exam/{post_id}")
    public void RegisterAnswerById(@PathVariable("post_id") Long id, @RequestParam("grade") String grade,  @RequestBody PostExamDTO postExamDTO) {

    }

    /** 정답 비교 확인 */
    @GetMapping("/room/exam/{post_id}")
    public void CheckAnswer(@PathVariable("post_id") Long id) {

    }


}
