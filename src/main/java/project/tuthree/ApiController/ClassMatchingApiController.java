package project.tuthree.ApiController;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.ExistListDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.dto.BookmarkDTO;
import project.tuthree.dto.post.PostExamDTO;
import project.tuthree.dto.room.StudyroomInfoDTO;
import project.tuthree.repository.PostFindRepository;
import project.tuthree.repository.StudyRoomRepository;
import project.tuthree.service.PostFindService;
import project.tuthree.service.PostFindService.PostFindStudentDTO;
import project.tuthree.service.PostFindService.PostFindStudentListDTO;
import project.tuthree.service.PostFindService.PostFindTeacherDTO;
import project.tuthree.service.PostFindService.PostFindTeacherListDTO;
import project.tuthree.service.StudyRoomService;
import project.tuthree.service.StudyRoomService.InfoListDTO;
import project.tuthree.service.StudyRoomService.ReviewListDTO;
import project.tuthree.testlogic.ArrayToJson;

import javax.validation.Valid;
import java.io.IOException;
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

    /** -선생님 과외 구하는 글 목록 조회 */
    @GetMapping("/tutor/list/{page}")
    public ExistListDataSuccessResponse findTutorList(@PathVariable("page") int page) throws IOException {
        List<PostFindTeacherListDTO> list = postFindService.findTeacherList(page);

        return new ExistListDataSuccessResponse(StatusCode.OK.getCode(), page + "페이지의 선생님 목록을 조회했습니다.",
                postFindRepository.teacherHasRow(), list);
    }

    /** -학생 과외 구하는 글 목록 조회 */
    @GetMapping("/tutee/list/{page}")
    public ExistListDataSuccessResponse findTuteeList(@PathVariable("page") int page) throws IOException {
        List<PostFindStudentListDTO> list = postFindService.findStudentList(page);
        return new ExistListDataSuccessResponse(StatusCode.OK.getCode(), page + "페이지의 학생 목록을 조회했습니다.",
                postFindRepository.studentHasRow(), list);
    }

    /** -특정 선생님 조회 */
    @GetMapping("/tutor/{post_id}")
    public ExistDataSuccessResponse findTutor(@PathVariable("post_id") Long id) throws IOException {
        PostFindTeacherDTO teacherDTO = postFindService.findTeacher(id);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                teacherDTO.getName() + " 선생님을 조회했습니다.", teacherDTO);
    }

    /** 특정 선생님 리뷰 조회 */
    @GetMapping("/tutor/review/{post_id}")
    public ExistDataSuccessResponse findTutorReviewList(@PathVariable("post_id") Long id) {
        List<ReviewListDTO> review = studyRoomService.findReviewByPostId(id);
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


    @GetMapping("/test/roominfo")
    public Object test_info() {
        List<String> subject = new ArrayList<>();
        subject.add("math");
        subject.add("kor");
        subject.add("eng");
        //list

        Map<String, Map<String, String>> map = new HashMap<>();
        Map<String, String> time1 = new HashMap<>();
        //hash<hash>
        time1.put("start", "17:00");
        time1.put("end", "20:00");
        map.put("mon", time1);
        Map<String, String> time2 = new HashMap<>();

        time2.put("start", "20:00");
        time2.put("end", "24:00");
        map.put("tue", time2);
        Map<String, Object> info = new HashMap<>();

        info.put("subject", subject);
        info.put("schedule", map);

        return info;
    }

    @PostMapping("/test/roomtest")
    public Object test_test(@RequestBody PostExamDTO postExamDTO) throws IOException {
//        ArrayToJson arrayToJson = new ArrayToJson();
//
//        Map<Object, Object> info = new HashMap<>();
//        info.put("subject", studyroomInfoDTO.getSubject());
//        info.put("schedule", studyroomInfoDTO.getSchedule());
//
//        byte[] bytes = studyRoomService.objectToByte(info);
//        return studyRoomService.byteToObject(bytes);

        return studyRoomService.saveStudentResponse(postExamDTO);

    }
}
