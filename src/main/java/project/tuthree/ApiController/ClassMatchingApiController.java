package project.tuthree.ApiController;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.ExistListDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.controller.JwtController;
import project.tuthree.domain.user.Grade;
import project.tuthree.dto.room.StudyroomInfoDTO;
import project.tuthree.repository.PostFindRepository;
import project.tuthree.repository.PostFindRepository.PostFindSearchCondition;
import project.tuthree.repository.StudyRoomRepository;
import project.tuthree.service.PostFindService;
import project.tuthree.service.PostFindService.PostFindStudentCountListDTO;
import project.tuthree.service.PostFindService.PostFindStudentDTO;
import project.tuthree.service.PostFindService.PostFindTeacherCountListDTO;
import project.tuthree.service.PostFindService.PostFindTeacherDTO;
import project.tuthree.service.StudyRoomService;
import project.tuthree.service.StudyRoomService.InfoListDTO;
import project.tuthree.service.StudyRoomService.ReviewListDTO;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static project.tuthree.configuration.Utils.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClassMatchingApiController {

    private final PostFindService postFindService;
    private final PostFindRepository postFindRepository;
    private final StudyRoomService studyRoomService;
    private final StudyRoomRepository studyRoomRepository;
    private final JwtController jwtController;

    /**
     * 선생님 과외 구하는 글 목록 조회
     * @param page
     * @param region 지역 필터
     * @param subject 과목 필터
     * @param start 급여 시작 단위
     * @param end 급여 끝 단위
     * @param sort hprice, lprice, hstar, lstar, latest, old
     * @return
     * @throws IOException
     */
    @GetMapping("/tutor/list/{page}")
    @ResponseStatus(HttpStatus.OK)
    public ExistListDataSuccessResponse findTutorList(@PathVariable("page") int page, @RequestParam(value = "region", required = false) ArrayList<String> region,
                                                      @RequestParam(value = "subject", required = false) ArrayList<String> subject, @RequestParam(value = "start", required = false) String start,
                                                      @RequestParam(value = "end", required = false) String end, @RequestParam(value = "sort", defaultValue = "latest",required = false) String sort) throws IOException {
        log.debug("\n---- TUTOR 목록 조회 [PAGE : " + page + "] ----\n");
        PostFindSearchCondition condition = new PostFindSearchCondition(region, subject, start, end, sort);
        PostFindTeacherCountListDTO list = postFindService.findTeacherList(page, condition);
        return new ExistListDataSuccessResponse(HttpStatus.OK.value(), page + "페이지의 선생님 목록을 조회했습니다.",
                list.getCount(), list.getList());
    }

    /**
     * 학생 과외 구하는 글 목록 조회
     * @param page
     * @param region 지역 필터
     * @param subject 과목 필터
     * @param start 급여 시작 단위
     * @param end 급여 끝 단위
     * @param sort hprice, lprice, latest, old
     * @return
     * @throws IOException
     */
    @GetMapping("/tutee/list/{page}")
    @ResponseStatus(HttpStatus.OK)
    public ExistListDataSuccessResponse findTuteeList(@PathVariable("page") int page, @RequestParam(value = "region", required = false) ArrayList<String> region,
                                                      @RequestParam(value = "subject", required = false) ArrayList<String> subject, @RequestParam(value = "start", required = false) String start,
                                                      @RequestParam(value = "end", required = false) String end, @RequestParam(value = "sort", required = false) String sort) throws IOException {
        log.debug("\n---- TUTEE 목록 조회 [PAGE : " + page + "] ----\n");
        PostFindSearchCondition condition = new PostFindSearchCondition(region, subject, start, end, sort);
        PostFindStudentCountListDTO list = postFindService.findStudentList(page, condition);
        return new ExistListDataSuccessResponse(HttpStatus.OK.value(), page + "페이지의 학생 목록을 조회했습니다.",
                list.getCount(), list.getList());
    }

    /** -특정 선생님 조회 */
    @GetMapping("/tutor/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    public ExistDataSuccessResponse findTutor(@PathVariable("post_id") Long id) throws IOException {
        log.debug("\n---- TUTOR 조회 [POST ID : " + id + "] ----\n");
        PostFindTeacherDTO teacherDTO = postFindService.findTeacher(id);
        return new ExistDataSuccessResponse(HttpStatus.OK.value(),
                teacherDTO.getName() + " 선생님을 조회했습니다.", teacherDTO);
    }

    /**
     * 특정 선생님 리뷰 조회
     * 정렬
     * @param sort
     * high(별점 높은 순), low(별점 낮은 순), latest(최신순)
     */
    @GetMapping("/tutor/review/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    public ExistDataSuccessResponse findTutorReviewList(@PathVariable("post_id") Long id, @RequestParam(value = "sort", required = false, defaultValue = "latest") String sort) {
        log.debug("\n---- TUTOR 리뷰 조회 [POST ID : " + id + "] ----\n");
        List<ReviewListDTO> review = studyRoomService.findReviewByPostId(id, sort);
        return new ExistDataSuccessResponse(HttpStatus.OK.value(),
                id + "번 게시글의 리뷰가 조회되었습니다.", review);
    }

    /** -특정 학생 조회 */
    @GetMapping("/tutee/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    public ExistDataSuccessResponse findTutee(@PathVariable("post_id") Long id) throws IOException {
        log.debug("\n---- TUTEE 조회 [POST ID : " + id + "] ----\n");
        PostFindStudentDTO studentDTO = postFindService.findStudent(id);
        return new ExistDataSuccessResponse(HttpStatus.OK.value(), studentDTO.getName() + " 학생을 조회했습니다.", studentDTO);
    }

    /** 선생 -수업 계획서 등록하기 */
    @PostMapping("/room/create")
    @ResponseStatus(HttpStatus.CREATED)
    public NotExistDataResultResponse createStudyRoomInfo(@RequestParam("teacherId") String teacherId,
                                                                           @RequestParam("studentId") String studentId, @RequestBody @Valid StudyroomInfoDTO studyroomInfoDTO) throws JsonProcessingException {

        log.debug("\n---- 수업 계획서 등록 [TEACHER ID : " + teacherId + "] [STUDENT ID : " + studentId + "] ----\n");
        studyRoomService.roomRegister(teacherId, studentId, studyroomInfoDTO);
        return new NotExistDataResultResponse(HttpStatus.CREATED.value(), "스터디룸 생성과 계획서 작성이 완료되었습니다.");
    }

    /** 선생 -수업 계획서 수정하기 */
    @PutMapping("/room/alter")
    @ResponseStatus(HttpStatus.CREATED)
    public NotExistDataResultResponse updateStudyRoomInfo(@RequestParam("teacherId") String teacherId,
                                                          @RequestParam("studentId") String studentId, @RequestBody @Valid StudyroomInfoDTO studyroomInfoDTO) throws JsonProcessingException {
        log.debug("\n---- 수업 계획서 수정 [TEACHER ID : " + teacherId + "] [STUDENT ID : " + studentId + "] ----\n");
        studyRoomService.roomUpdate(teacherId, studentId, studyroomInfoDTO);
        return new NotExistDataResultResponse(HttpStatus.CREATED.value(), "스터디룸 계획서 수정이 완료되었습니다.");
    }

    /** - 학생 - 수락하기 누르면 수업 계획서 불러오기 */
    @GetMapping("/room/info")
    @ResponseStatus(HttpStatus.OK)
    public ExistDataSuccessResponse findStudyRoomInfo(@RequestParam("teacherId") String teacherId, @RequestParam("studentId") String studentId) throws JsonProcessingException {
        log.debug("\n---- 수업 계획서 조회 [TEACHER ID : " + teacherId + "] [STUDENT ID : " + studentId + "] ----\n");
        InfoListDTO infoListDTO = studyRoomService.findStudyRoomInfo(teacherId, studentId);
        return new ExistDataSuccessResponse(HttpStatus.OK.value(), "수업 계획서를 불러왔습니다.", infoListDTO);
    }

    /** - 학생, 선생 - 최종 수락 하기 / 최종 수락 여부 */
    @GetMapping("/room/info/accept")
    public Object StudyRoomAccept(@RequestParam("teacherId") String teacherId,
                                                      @RequestParam("studentId") String studentId, @RequestParam("grade") String grade) {
        log.debug("\n---- 수업 계획서 수락하기/수락 여부 확인 [TEACHER ID : " + teacherId + "] [STUDENT ID : " + studentId + "] [GRADE : " + grade + "] ----\n");
        return studyRoomService.studyRoomIsAccept(teacherId, studentId, grade);
    }

    /** 선생, 학생 - 수업 종료하기 */
    @GetMapping("/room/close")
    @ResponseStatus(HttpStatus.CREATED)
    public NotExistDataResultResponse StudyRoomClose(@RequestParam("teacherId") String teacherId, @RequestParam("studentId") String studentId) {
        log.debug("\n---- 수업 종료 [TEACHER ID : " + teacherId + "] [STUDENT ID : " + studentId + "] ----\n");
        studyRoomRepository.closeStudyRoom(teacherId, studentId);
        return new NotExistDataResultResponse(HttpStatus.CREATED.value(), "수업이 종료되었습니다.");
    }

    /** 북마크 추가 */
    @GetMapping("/bookmark/add")
    @ResponseStatus(HttpStatus.CREATED)
    public NotExistDataResultResponse AddBookMark(@RequestParam("from") String user1, @RequestParam("to") String user2) {
        log.debug("\n---- 북마크 추가 [FROM : " + user1 + "] [TO : " + user2 + "] ----\n");
        Long addId = postFindService.addBookMark(user1, user2);
        return new NotExistDataResultResponse(HttpStatus.CREATED.value(), addId + "번 북마크가 설정되었습니다.");
    }

    /** 북마크 삭제 */
    @DeleteMapping("/bookmark/{mark_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public NotExistDataResultResponse DeleteBookMark(@PathVariable("mark_id") Long id) {
        log.debug("\n---- 북마크 삭제 [ID : " + id + "] ----\n");
        Long deletedId = postFindRepository.deleteBookMark(id);
        return new NotExistDataResultResponse(HttpStatus.CREATED.value(), deletedId + "번 북마크가 삭제되었습니다.");
    }

    /** 북마크 목록 불러오기 */
    @GetMapping("/bookmark")
    @ResponseStatus(HttpStatus.OK)
    public ExistDataSuccessResponse ListBookMark(@RequestHeader(AUTHORIZATION) String token) {
        String userId = jwtController.parseValueFromJwtToken(token, CLAIMUSERID);
        String grade = jwtController.parseValueFromJwtToken(token, CLAIMGRADE);
        log.debug("\n---- 북마크 조회 [USER ID : " + userId + "] ----\n");
        Object bookmarkDTO = new Object();
        if (grade.equals(Grade.TEACHER.getStrType())) {
            bookmarkDTO = postFindService.teacherListBookMark(userId);
        } else if (grade.equals(Grade.STUDENT.getStrType()) || grade.equals(Grade.PARENT.getStrType())) {
            bookmarkDTO = postFindService.studentListBookMark(userId);
        }
        return new ExistDataSuccessResponse(HttpStatus.OK.value(), "북마크 목록을 불러왔습니다.", bookmarkDTO);
    }

}
