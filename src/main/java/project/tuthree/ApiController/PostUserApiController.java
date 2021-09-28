package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.tuthree.dto.PostfaqDTO;
import project.tuthree.dto.PostnoticeDTO;
import project.tuthree.repository.PostFaqRepository;
import project.tuthree.repository.PostNoticeRepository;
import project.tuthree.service.PostFaqService;
import project.tuthree.service.PostNoticeService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostUserApiController {
    /** responsebody 처리하기 valid */

    private final PostFaqService postFaqService;
    private final PostNoticeService postNoticeService;

    @ResponseBody
    @GetMapping("/faq/{page}")
    public List<PostfaqDTO> FaqList(@PathVariable("page") int page) {
        return postFaqService.faqFindByPage(page);
    }

    @ResponseBody
    @GetMapping("/faq/id/{faq_id}")
    public PostfaqDTO FaqFind(@PathVariable("faq_id") Long id){
        return postFaqService.faqFindById(id);
    }

    @ResponseBody
    @GetMapping("/notice/{page}")
    public List<PostnoticeDTO> NoticeList(@PathVariable("page") int page) {
        return postNoticeService.noticeByPage(page);
    }

    @ResponseBody
    @GetMapping("/notice/id/{notice_id}")
    public PostnoticeDTO NoticeFind(@PathVariable("notice_id") Long id) {
        return postNoticeService.noticeById(id);
    }

}
