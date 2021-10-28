package project.tuthree.ApiController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.dto.post.PostfaqDTO;
import project.tuthree.dto.post.PostnoticeDTO;
import project.tuthree.exception.NotFoundRequestData;
import project.tuthree.repository.PostFaqRepository;
import project.tuthree.repository.PostNoticeRepository;
import project.tuthree.service.NoticeService;
import project.tuthree.service.PostFaqService;
import project.tuthree.service.PostNoticeService;
import project.tuthree.service.PostTestPaperService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PostAdminApiController {

    private final PostFaqService postFaqService;
    private final PostFaqRepository postFaqRepository;
    private final PostNoticeService postNoticeService;
    private final PostNoticeRepository postNoticeRepository;
    private final PostTestPaperService postTestPaperService;
    private final NoticeService noticeService;

    /** faq  작성 */
    @PostMapping("/faq/admin/write")
    public NotExistDataResultResponse faqWrite(@RequestBody @Valid PostfaqDTO postfaqDTO) {
        Long id = postFaqService.writeFaq(postfaqDTO);
        log.debug("\n---- 관리자 FAQ 작성 [ID : " + id + " ] ----\n");
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "번 FAQ가 작성되었습니다.");
    }

    /** faq 삭제 */
    @DeleteMapping("/faq/admin/id/{faq_id}")
    public NotExistDataResultResponse faqDelete(@PathVariable("faq_id") Long id) throws NotFoundRequestData {
        Long deleteId = postFaqRepository.deleteFaq(id);
        log.debug("\n---- 관리자 FAQ 삭제 [ID : " + deleteId + " ] ----\n");
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), deleteId + "번 FAQ가 삭제되었습니다.");
    }

    /**  faq 수정 */
    @PutMapping("/faq/admin/id/{faq_id}")
    public NotExistDataResultResponse FaqpUpdate(@PathVariable("faq_id") Long id, @RequestBody @Valid PostfaqDTO postfaqDTO) {
        Long updatedId = postFaqService.updateFaq(id, postfaqDTO);
        log.debug("\n---- 관리자 FAQ 수정 [ID : " + updatedId + " ] ----\n");
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), updatedId + "번 FAQ가 수정되었습니다.");
    }

    /** 관리자 공지사항 작성 */
    @PostMapping("/notice/admin/write")
    public NotExistDataResultResponse NoticeWrite(@RequestBody @Valid PostnoticeDTO postnoticeDTO) {
        Long id = postNoticeService.writeNotice(postnoticeDTO);
//        noticeService.PostAdminNotice(id);
        log.debug("\n---- 관리자 공지사항 작성 [ID : " + id + " ] ----\n");
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "번 공지사항이 작성되었습니다.");
    }

    /** 공지사항 삭제 */
    @DeleteMapping("/notice/admin/id/{notice_id}")
    public NotExistDataResultResponse NoticeDelete(@PathVariable("notice_id") Long id) {
        Long deleteId = postNoticeRepository.deleteNotice(id);
        log.debug("\n---- 관리자 공지사항 삭제 [ID : " + deleteId + " ] ----\n");
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), deleteId + "번 공지사항이 삭제되었습니다.");
    }

    /** 공지사항 수정 */
    @PutMapping("/notice/admin/id/{notice_id}")
    public NotExistDataResultResponse NoticeUpdate(@PathVariable("notice_id") Long id, @RequestBody @Valid PostnoticeDTO postnoticeDTO) {
        Long updatedId = postNoticeService.updateNotice(id, postnoticeDTO);
        log.debug("\n---- 관리자 공지사항 수정 [ID : " + updatedId +" ] ----\n");
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), updatedId + "번 공지사항이 수정되었습니다.");
    }

    // TEST PAPER ////////////////////////////////

    /** 커뮤니티 게시글 삭제 */
    @DeleteMapping("/community/admin/id/{post_id}")
    public NotExistDataResultResponse CommunityAdminDelete(@PathVariable("post_id") Long id) {
        Long deletedId = postTestPaperService.deleteCommunity(id);
        log.debug("\n---- 관리자 커뮤니티 글 삭제 [ID : " + deletedId + "] ----\n");
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), deletedId + "번 게시글이 삭제되었습니다.");
    }
}
