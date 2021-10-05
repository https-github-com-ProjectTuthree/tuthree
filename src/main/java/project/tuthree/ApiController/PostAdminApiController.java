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

    // FAQ ////////////////////////////////

//    /** faq 페이지 목록 조회 */
//    @GetMapping("/faq/admin/{page}")
//    public ExistListDataSuccessResponse FaqList(@PathVariable("page") int page) {
//        List<PostListDTO> dtoList = postFaqService.faqFindByPage(page);
//        log.debug("\n---- 관리자 FAQ 페이지 " + page + "리스트 조회 ----\n");
//        return new ExistListDataSuccessResponse(StatusCode.OK.getCode(),
//                page + " 페이지 FAQ가 조회되었습니다.", postFaqRepository.faqHasRow(), dtoList);
//    }
//
//    /** faq 특정 글 조회 */
//    @GetMapping("/faq/admin/id/{faq_id}")
//    public ExistDataSuccessResponse FaqFind(@PathVariable("faq_id") Long id){
//        PostListDTO postListDTO = postFaqService.faqFindById(id);
//        log.debug("\n---- 관리자 FAQ 게시글 조회 [ID : " + id + " ] ----\n");
//        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
//                postListDTO.getId() + "번 FAQ가 조회되었습니다.", postListDTO);
//    }

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


    // NOTICE ////////////////////////////////

//    /** 공지사항 페이지 목록 조회 */
//    @GetMapping("/notice/admin/{page}")
//    public ExistListDataSuccessResponse NoticeList(@PathVariable("page") int page) {
//        List<PostListDTO> dtoList = postNoticeService.noticeByPage(page);
//        log.debug("\n---- 관리자 공지사항 " + page + "페이지 리스트 조회 ----\n");
//        return new ExistListDataSuccessResponse(StatusCode.OK.getCode(),
//                page + "페이지의 공지사항이 조회되었습니다.", postNoticeRepository.noticeHasRow(), dtoList);
//    }
//
//    /** 공지사항 특정 글 조회 */
//    @GetMapping("/notice/admin/id/{notice_id}")
//    public ExistDataSuccessResponse NoticeFind(@PathVariable("notice_id") Long id) {
//        PostListDTO postListDTO = postNoticeService.noticeById(id);
//        log.debug("\n---- 관리자 공지사항 게시글 조회 [ID : " + id + "] ----\n");
//        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
//                postListDTO.getId() + "번 공지사항이 조회되었습니다.", postListDTO);
//    }

    /** 관리자 공지사항 작성 */
    @PostMapping("/notice/admin/write")
    public NotExistDataResultResponse NoticeWrite(@RequestBody @Valid PostnoticeDTO postnoticeDTO) {
        Long id = postNoticeService.writeNotice(postnoticeDTO);
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

    /**
     * 커뮤니티 게시글 삭제
     */
    @DeleteMapping("/community/admin/id/{post_id}")
    public NotExistDataResultResponse CommunityAdminDelete(@PathVariable("post_id") Long id) {
        Long deletedId = postTestPaperService.deleteCommunity(id);
        log.debug("\n---- 관리자 커뮤니티 글 삭제 [ID : " + deletedId + "] ----\n");
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), deletedId + "번 게시글이 삭제되었습니다.");
    }
}
