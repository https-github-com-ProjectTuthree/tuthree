package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import lombok.Value;
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

@RestController
@RequiredArgsConstructor
public class PostAdminApiController {

    /** responsebody 처리하기 valid */

    private final PostFaqService postFaqService;
    private final PostFaqRepository postFaqRepository;
    private final PostNoticeService postNoticeService;
    private final PostNoticeRepository postNoticeRepository;

    //FAQ 글 목록 조회
    @ResponseBody
    @GetMapping("/faq/admin/{page}")
    public List<PostfaqDTO> FaqList(@PathVariable("page") int page) {
        return postFaqService.faqFindByPage(page);
    }

    @ResponseBody
    @GetMapping("/faq/admin/id/{faq_id}")
    public PostfaqDTO FaqFind(@PathVariable("faq_id") Long id){
        return postFaqService.faqFindById(id);
    }

    /**
     * faq  작성
     */
    @ResponseBody
    @PostMapping("/faq/admin/write")
    public Long faqWrite(@RequestBody PostfaqDTO postfaqDTO, HttpServletResponse res) {
        res.setHeader("Authorization", "tokenid");
        int code = res.getStatus();
        res.setHeader("code", String.valueOf(code));
        return postFaqService.writeFaq(postfaqDTO);
    }

    /**
     * faq 삭제
     */
    @ResponseBody
    @DeleteMapping("/faq/admin/id/{faq_id}")
    public int faqDelete(@PathVariable("faq_id") Long id, HttpServletResponse response) {
        postFaqRepository.deleteFaq(id);
        return response.getStatus();
    }

    @ResponseBody
    @GetMapping("/notice/admin/{page}")
    public List<PostnoticeDTO> NoticeList(@PathVariable("page") int page) {
        return postNoticeService.noticeByPage(page);
    }

    @ResponseBody
    @GetMapping("/notice/admin/id/{notice_id}")
    public PostnoticeDTO NoticeFind(@PathVariable("notice_id") Long id) {
        return postNoticeService.noticeById(id);
    }

    /**
     * 관리자 공지사항 작성
     */
    @ResponseBody
    @PostMapping("/notice/admin/write")
    public Long NoticeWrite(@RequestBody PostnoticeDTO postnoticeDTO, HttpServletResponse res) {

        res.setHeader("Authorization", "tokenid");
        int code = res.getStatus();
        res.setHeader("code", String.valueOf(code));
        return postNoticeService.writeNotice(postnoticeDTO);
    }

    /**
     * 공지사항 삭제
     */
    @ResponseBody
    @DeleteMapping("/notice/admin/id/{notice_id}")
    public int NoticeDelete(@PathVariable("notice_id") Long id, HttpServletResponse response) {
        postNoticeRepository.deleteNotice(id);
        return response.getStatus();
    }
}
