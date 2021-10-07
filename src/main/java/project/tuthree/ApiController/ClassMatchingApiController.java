package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.ExistListDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.dto.room.StudyroomInfoDTO;
import project.tuthree.repository.PostFindRepository;
import project.tuthree.service.PostFindService;
import project.tuthree.service.PostFindService.PostFindStudentDTO;
import project.tuthree.service.PostFindService.PostFindStudentListDTO;
import project.tuthree.service.PostFindService.PostFindTeacherDTO;
import project.tuthree.service.PostFindService.PostFindTeacherListDTO;
import project.tuthree.service.StudyRoomService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClassMatchingApiController {

    private final PostFindService postFindService;
    private final PostFindRepository postFindRepository;
    private final StudyRoomService studyRoomService;

    /** 선생님 과외 구하는 글 목록 조회 */
    @GetMapping("/tutor/list/{page}")
    public ExistListDataSuccessResponse findTutorList(@PathVariable("page") int page) throws IOException {
        List<PostFindTeacherListDTO> list = postFindService.findTeacherList(page);

        return new ExistListDataSuccessResponse(StatusCode.OK.getCode(), page + "페이지의 선생님 목록을 조회했습니다.",
                postFindRepository.teacherHasRow(), list);
    }

    /** 학생 과외 구하는 글 목록 조회 */
    @GetMapping("/tutee/list/{page}")
    public ExistListDataSuccessResponse findTuteeList(@PathVariable("page") int page) throws IOException {
        List<PostFindStudentListDTO> list = postFindService.findStudentList(page);
        return new ExistListDataSuccessResponse(StatusCode.OK.getCode(), page + "페이지의 학생 목록을 조회했습니다.",
                postFindRepository.studentHasRow(), list);
    }

    /** 특정 선생님 조회 */
    @GetMapping("/tutor/{post_id}")
    public ExistDataSuccessResponse findTutor(@PathVariable("post_id") Long id) throws IOException {
        PostFindTeacherDTO teacherDTO = postFindService.findTeacher(id);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                teacherDTO.getName() + " 선생님을 조회했습니다.", teacherDTO);
    }

    /** 특정 학생 조회 */
    @GetMapping("/tutee/{post_id}")
    public ExistDataSuccessResponse findTutee(@PathVariable("post_id") Long id) throws IOException {
        PostFindStudentDTO studentDTO = postFindService.findStudent(id);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                studentDTO.getName() + " 학생을 조회했습니다.", studentDTO);
    }

    /**
     * 수업 계획서 등록하기
     */
    @PostMapping("/room")
    public NotExistDataResultResponse createStudyRoomInfo(@RequestParam("teacherId") String teacherId,
                                                                           @RequestParam("studentId") String studentId,
                                                                           @RequestBody @Valid StudyroomInfoDTO studyroomInfoDTO) {
        /** 반환값 다시 생각해보기 */
        //방 만들기
        studyRoomService.roomRegister(teacherId, studentId);
        //방에 대한 info 넣기
        studyRoomService.infoRegister(studyroomInfoDTO);

        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), "스터디룸 생성과 계획서 작성이 완료되었습니다.");
    }


}
