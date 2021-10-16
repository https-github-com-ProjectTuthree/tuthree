package project.tuthree.ApiController;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.ExistListDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.dto.BookmarkDTO;
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
import project.tuthree.testlogic.ArrayToJson;

import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClassMatchingApiController {

    private final PostFindService postFindService;
    private final PostFindRepository postFindRepository;
    private final StudyRoomService studyRoomService;
    private final StudyRoomRepository studyRoomRepository;
    private final ArrayToJson arrayToJson;

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
    public ExistListDataSuccessResponse findTutorList(@PathVariable("page") int page, @RequestParam(value = "region", required = false) ArrayList<String> region,
                                                      @RequestParam(value = "subject", required = false) ArrayList<String> subject, @RequestParam(value = "start", required = false) String start,
                                                      @RequestParam(value = "end", required = false) String end, @RequestParam(value = "sort", required = false) String sort) throws IOException {
        PostFindSearchCondition condition = new PostFindSearchCondition(region, subject, start, end, sort);
        PostFindTeacherCountListDTO list = postFindService.findTeacherList(page, condition);

        return new ExistListDataSuccessResponse(StatusCode.OK.getCode(), page + "페이지의 선생님 목록을 조회했습니다.",
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
    public ExistListDataSuccessResponse findTuteeList(@PathVariable("page") int page, @RequestParam(value = "region", required = false) ArrayList<String> region,
                                                      @RequestParam(value = "subject", required = false) ArrayList<String> subject, @RequestParam(value = "start", required = false) String start,
                                                      @RequestParam(value = "end", required = false) String end, @RequestParam(value = "sort", required = false) String sort) throws IOException {
        PostFindSearchCondition condition = new PostFindSearchCondition(region, subject, start, end, sort);
        PostFindStudentCountListDTO list = postFindService.findStudentList(page, condition);
        return new ExistListDataSuccessResponse(StatusCode.OK.getCode(), page + "페이지의 학생 목록을 조회했습니다.",
                list.getCount(), list.getList());
    }

    /** -특정 선생님 조회 */
    @GetMapping("/tutor/{post_id}")
    public ExistDataSuccessResponse findTutor(@PathVariable("post_id") Long id) throws IOException {
        PostFindTeacherDTO teacherDTO = postFindService.findTeacher(id);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                teacherDTO.getName() + " 선생님을 조회했습니다.", teacherDTO);
    }

    /**
     * 특정 선생님 리뷰 조회
     * 정렬
     * @param sort
     * high(별점 높은 순), low(별점 낮은 순), latest(최신순)
     */
    @GetMapping("/tutor/review/{post_id}")
    public ExistDataSuccessResponse findTutorReviewList(@PathVariable("post_id") Long id, @RequestParam(value = "sort", required = false, defaultValue = "latest") String sort) {
        List<ReviewListDTO> review = studyRoomService.findReviewByPostId(id, sort);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                id + "번 게시글의 리뷰가 조회되었습니다.", review);
    }

    /** -특정 학생 조회 */
    @GetMapping("/tutee/{post_id}")
    public ExistDataSuccessResponse findTutee(@PathVariable("post_id") Long id) throws IOException {
        PostFindStudentDTO studentDTO = postFindService.findStudent(id);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                studentDTO.getName() + " 학생을 조회했습니다.", studentDTO);
    }

    /** -수업 계획서 등록하기 */
    @PostMapping("/room/create")
    public NotExistDataResultResponse createStudyRoomInfo(@RequestParam("teacherId") String teacherId,
                                                                           @RequestParam("studentId") String studentId, @RequestBody @Valid StudyroomInfoDTO studyroomInfoDTO) throws JsonProcessingException {
        studyRoomService.roomRegister(teacherId, studentId, studyroomInfoDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), "스터디룸 생성과 계획서 작성이 완료되었습니다.");
    }

    /** -수업 계획서 수정하기 */
    @PutMapping("/room/alter")
    public NotExistDataResultResponse alterStudyRoomInfo(@RequestParam("teacherId") String teacherId,
                                   @RequestParam("studentId") String studentId, @RequestBody @Valid StudyroomInfoDTO studyroomInfoDTO) throws JsonProcessingException {
        studyRoomService.roomUpdate(teacherId, studentId, studyroomInfoDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), "스터디룸 계획서 수정이 완료되었습니다.");
    }

    /** - 학생 - 수락하기 누르면 수업 계획서 불러오기 */
    @GetMapping("/room/info")
    public ExistDataSuccessResponse findStudyRoomInfo(@RequestParam("teacherId") String teacherId, @RequestParam("studentId") String studentId) throws JsonProcessingException {
        InfoListDTO infoListDTO = studyRoomService.findStudyRoomInfo(teacherId, studentId);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(), "수업 계획서를 불러왔습니다.", infoListDTO);
    }

    /** - 학생 - 최종 수락 */
    @GetMapping("/room/info/accept")
    public Object StudyRoomAccept(@RequestParam("teacherId") String teacherId,
                                                      @RequestParam("studentId") String studentId, @RequestParam("grade") String grade) {
        Object res = studyRoomService.studyRoomIsAccept(teacherId, studentId, grade);
        return res;
    }

    /** 수업 종료하기 */
    @GetMapping("/room/close")
    public NotExistDataResultResponse StudyRoomClose(@RequestParam("teacherId") String teacherId, @RequestParam("studentId") String studentId) {
        studyRoomRepository.closeStudyRoom(teacherId, studentId);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), "수업이 종료되었습니다.");
    }

    /** 북마크 추가 */
    @GetMapping("/bookmark/add")
    public NotExistDataResultResponse AddBookMark(@RequestParam("from") String user1, @RequestParam("to") String user2) {
        Long addId = postFindService.addBookMark(user1, user2);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), addId + "번 북마크가 설정되었습니다.");
    }

    /** 북마크 삭제 */
    @DeleteMapping("/bookmark/{mark_id}")
    public NotExistDataResultResponse DeleteBookMark(@PathVariable("mark_id") Long id) {
        Long deletedId = postFindRepository.deleteBookMark(id);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), deletedId + "번 북마크가 삭제되었습니다.");
    }

    /** 북마크 목록 불러오기 */
    @GetMapping("/bookmark")
    public ExistDataSuccessResponse ListBookMark(@RequestParam("userId") String userId) {
        List<BookmarkDTO> bookmarkDTO = postFindService.listBookMark(userId);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                "북마크 목록을 불러왔습니다.", bookmarkDTO);
    }

}
