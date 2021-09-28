package project.tuthree.ApiController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.tuthree.dto.PostfaqDTO;
import project.tuthree.dto.PostnoticeDTO;
import project.tuthree.repository.PostFaqRepository;
import project.tuthree.repository.PostNoticeRepository;
import project.tuthree.service.PostFaqService;
import project.tuthree.service.PostNoticeService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostAdminApiController {

    private final PostFaqService postFaqService;
    private final PostFaqRepository postFaqRepository;
    private final PostNoticeService postNoticeService;
    private final PostNoticeRepository postNoticeRepository;
    private final HttpServletResponse response;

    @Getter
    @AllArgsConstructor
    class ExistDataSuccessResponse<T>{
        private final Boolean Success = true;
        private final int StatusCode = response.getStatus();
        String Message;
        T data;
    }

    @Getter
    @AllArgsConstructor
    class NotExistDataResultResponse<T>{
        private final Boolean Success = true;
        private final int StatusCode = response.getStatus();
        String Message;
    }

    /** faq 페이지 목록 조회 */
    @GetMapping("/faq/admin/{page}")
    public ExistDataSuccessResponse FaqList(@PathVariable("page") int page) {
        List<PostfaqDTO> dtoList = postFaqService.faqFindByPage(page);
        log.debug("\n---- 관리자 FAQ 페이지 리스트 조회 ----\n");
        return new ExistDataSuccessResponse(
                page + " 페이지의 FAQ가 조회되었습니다.", dtoList);
    }

    /** faq 특정 글 조회 */
    @GetMapping("/faq/admin/id/{faq_id}")
    public ExistDataSuccessResponse FaqFind(@PathVariable("faq_id") Long id){
        PostfaqDTO postfaqDTO = postFaqService.faqFindById(id);
        log.debug("\n---- 관리자 FAQ 게시글 조회 [ID : " + id + " ] ----\n");
        return new ExistDataSuccessResponse(
                postfaqDTO.getId() + "번 FAQ가 조회되었습니다.", postfaqDTO);
    }

    /** faq  작성 */
    @PostMapping("/faq/admin/write")
    public NotExistDataResultResponse faqWrite(@RequestBody @Valid PostfaqDTO postfaqDTO) {
        //res.setHeader("Authorization", "tokenid");
        //int code = res.getStatus();
        //res.setHeader("code", String.valueOf(code));
        Long id = postFaqService.writeFaq(postfaqDTO);
        log.debug("\n---- 관리자 FAQ 작성 [ID : " + id + " ] ----\n");
        return new NotExistDataResultResponse(id + "번 FAQ가 작성되었습니다.");
    }

    /** faq 삭제 */
    @DeleteMapping("/faq/admin/id/{faq_id}")
    public NotExistDataResultResponse faqDelete(@PathVariable("faq_id") Long id) {
        Long deleteId = postFaqRepository.deleteFaq(id);
        log.debug("\n---- 관리자 FAQ 삭제 [ID : " + deleteId + " ] ----\n");
        return new NotExistDataResultResponse(deleteId + "번 FAQ가 삭제되었습니다.");
    }

    /**  faq 수정 */
    @PutMapping("/faq/admin/id/{faq_id}")
    public NotExistDataResultResponse FaqpUpdate(@PathVariable("faq_id") Long id, @RequestBody @Valid PostfaqDTO postfaqDTO) {
        Long updatedId = postFaqService.updateFaq(id, postfaqDTO);
        log.debug("\n---- 관리자 FAQ 수정 [ID : " + updatedId + " ] ----\n");
        return new NotExistDataResultResponse(updatedId + "번 FAQ가 수정되었습니다.");
    }
    //////////////////////////////////////////////////////////////////

    /** 공지사항 페이지 목록 조회 */
    @GetMapping("/notice/admin/{page}")
    public ExistDataSuccessResponse NoticeList(@PathVariable("page") int page) {
        List<PostnoticeDTO> dtoList = postNoticeService.noticeByPage(page);
        log.debug("\n---- NOTICE 페이지 리스트 조회 ----\n");
        return new ExistDataSuccessResponse(
                page + " 페이지의 공지사항이 조회되었습니다.", dtoList);
    }

    /** 공지사항 특정 글 조회 */
    @GetMapping("/notice/admin/id/{notice_id}")
    public ExistDataSuccessResponse NoticeFind(@PathVariable("notice_id") Long id) {
        PostnoticeDTO postnoticeDTO = postNoticeService.noticeById(id);
        log.debug("\n---- 관리자 공지사항 게시글 조회 [ID : " + id + "] ----\n");
        return new ExistDataSuccessResponse(
                postnoticeDTO.getId() + "번 FAQ가 조회되었습니다.", postnoticeDTO);
    }

    /** 관리자 공지사항 작성 */
    @PostMapping("/notice/admin/write")
    public NotExistDataResultResponse NoticeWrite(@RequestBody @Valid PostnoticeDTO postnoticeDTO) {

        Long id = postNoticeService.writeNotice(postnoticeDTO);
        log.debug("\n---- 관리자 공지사항 작성 [ID : " + id + " ] ----\n");
        return new NotExistDataResultResponse(id + "번 공지사항이 작성되었습니다.");
    }

    /** 공지사항 삭제 */
    @DeleteMapping("/notice/admin/id/{notice_id}")
    public NotExistDataResultResponse NoticeDelete(@PathVariable("notice_id") Long id) {
        Long deleteId = postNoticeRepository.deleteNotice(id);
        log.debug("\n---- 관리자 FAQ 삭제 [ID : " + deleteId + " ] ----\n");
        return new NotExistDataResultResponse(deleteId + "번 공지사항이 삭제되었습니다.");
    }

    /** 공지사항 수정 */
    @PutMapping("/notice/admin/id/{notice_id}")
    public NotExistDataResultResponse NoticeUpdate(@PathVariable("notice_id") Long id, @RequestBody @Valid PostnoticeDTO postnoticeDTO) {
        Long updatedId = postNoticeService.updateNotice(id, postnoticeDTO);
        log.debug("\n---- 관리자 FAQ 수정 [ID : " + updatedId +" ] ----\n");

        return new NotExistDataResultResponse(updatedId + "번 공지사항이 수정되었습니다.");
    }
}
