package project.tuthree.ApiController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.dto.PostfaqDTO;
import project.tuthree.dto.PostnoticeDTO;
import project.tuthree.repository.PostFaqRepository;
import project.tuthree.repository.PostNoticeRepository;
import project.tuthree.service.PostFaqService;
import project.tuthree.service.PostNoticeService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostAdminApiController {

    private final PostFaqService postFaqService;
    private final PostFaqRepository postFaqRepository;
    private final PostNoticeService postNoticeService;
    private final PostNoticeRepository postNoticeRepository;

    /** faq 페이지 목록 조회 */
    @GetMapping("/faq/admin/{page}")
    public Map<String,Object> FaqList(@PathVariable("page") int page) {
        Map<String, Object> map = new HashMap<>();
        List<PostfaqDTO> dtoList = postFaqService.faqFindByPage(page);
        Long list = postFaqRepository.faqHasRow();
        map.put("Success", true);
        map.put("StatusCode", StatusCode.OK.getCode());
        map.put("Message", page + "페이지의 FAQ가 조회되었습니다.");
        map.put("list", list);
        map.put("data", dtoList);
        log.debug("\n---- 관리자 FAQ 페이지 " + page + "리스트 조회 ----\n");
        return map;
    }

    /** faq 특정 글 조회 */
    @GetMapping("/faq/admin/id/{faq_id}")
    public ExistDataSuccessResponse FaqFind(@PathVariable("faq_id") Long id){
        PostfaqDTO postfaqDTO = postFaqService.faqFindById(id);
        log.debug("\n---- 관리자 FAQ 게시글 조회 [ID : " + id + " ] ----\n");
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                postfaqDTO.getId() + "번 FAQ가 조회되었습니다.", postfaqDTO);
    }

    /** faq  작성 */
    @PostMapping("/faq/admin/write")
    public NotExistDataResultResponse faqWrite(@RequestBody @Valid PostfaqDTO postfaqDTO) {
        Long id = postFaqService.writeFaq(postfaqDTO);
        log.debug("\n---- 관리자 FAQ 작성 [ID : " + id + " ] ----\n");
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "번 FAQ가 작성되었습니다.");
    }

    /** faq 삭제 */
    @DeleteMapping("/faq/admin/id/{faq_id}")
    public NotExistDataResultResponse faqDelete(@PathVariable("faq_id") Long id) {
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
    //////////////////////////////////////////////////////////////////

    /** 공지사항 페이지 목록 조회 */
    @GetMapping("/notice/admin/{page}")
    public Map<String, Object> NoticeList(@PathVariable("page") int page) {
        Map<String, Object> map = new HashMap<>();
        List<PostnoticeDTO> dtoList = postNoticeService.noticeByPage(page);
        Long list = postNoticeRepository.noticeHasRow();
        map.put("Success", true);
        map.put("StatusCode", StatusCode.OK);
        map.put("Message", page + "페이지의 공지사항이 조회되었습니다.");
        map.put("list", list);
        map.put("data", dtoList);
        log.debug("\n---- 관리자 공지사항 " + page + "페이지 리스트 조회 ----\n");
        return map;
    }

    /** 공지사항 특정 글 조회 */
    @GetMapping("/notice/admin/id/{notice_id}")
    public ExistDataSuccessResponse NoticeFind(@PathVariable("notice_id") Long id) {
        PostnoticeDTO postnoticeDTO = postNoticeService.noticeById(id);
        log.debug("\n---- 관리자 공지사항 게시글 조회 [ID : " + id + "] ----\n");
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                postnoticeDTO.getId() + "번 공지사항이 조회되었습니다.", postnoticeDTO);
    }

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
}
