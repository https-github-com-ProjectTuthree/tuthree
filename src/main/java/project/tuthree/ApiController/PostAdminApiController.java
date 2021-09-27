package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;
import project.tuthree.dto.PostfaqDTO;
import project.tuthree.dto.PostnoticeDTO;
import project.tuthree.service.PostFaqService;
import project.tuthree.service.PostNoticeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostAdminApiController {

    /** responsebody 처리하기 */

    private final PostFaqService postFaqService;
    private final PostNoticeService postNoticeService;

    //FAQ 글 목록 조회
    @GetMapping("/faq/admin/{page}")
    public List<PostfaqDTO> FaqList(@PathVariable("page") int page) {
        return postFaqService.faqFindByPage(page);
    }

    @GetMapping("/faq/admin/id/{faq_id}")
    public PostfaqDTO FaqFind(@PathVariable("faq_id") Long id){
        return postFaqService.faqFindById(id);
    }


    @GetMapping("/notice/admin/{page}")
    public List<PostnoticeDTO> NoticeList(@PathVariable("page") int page) {
        return postNoticeService.noticeByPage(page);
    }

    @GetMapping("/notice/admin/id/{notice_id}")
    public PostnoticeDTO NoticeFind(@PathVariable("notice_id") Long id) {
        return postNoticeService.noticeById(id);
    }

    /**
     * 관리자 공지사항 작성
     * valid 사용법 확인하기!!
     */
    @PostMapping("/notice/admin/write")
    public Long NoticeWrite(@RequestBody @Valid PostnoticeDTO postnoticeDTO) {
        return postNoticeService.writeNotice(postnoticeDTO);
    }
}
