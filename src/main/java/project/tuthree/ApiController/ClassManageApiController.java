package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.dto.post.PostreviewDTO;
import project.tuthree.service.StudyRoomService;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ClassManageApiController {

    private final StudyRoomService studyRoomService;

    /** 404 not found가 뜨는 에러 - 동작은 잘하고 터미널에도 오류가 안 뜬다.. */
    @PostMapping("/room/review")
    public NotExistDataResultResponse WriteStudyRoomReview(@RequestParam("teacherId") String teacherId,
                                                                            @RequestParam("studentId") String studentId, @RequestBody @Valid PostreviewDTO postreviewDTO) {
        String teacherName = studyRoomService.writeReviewByStudyRoom(teacherId, studentId, postreviewDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(),
                teacherName + " 선생님에 대한 리뷰가 작성되었습니다.");
    }
}
