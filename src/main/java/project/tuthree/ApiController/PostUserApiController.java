package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.dto.*;
import project.tuthree.dto.EmbeddedDTO.PostListDTO;
import project.tuthree.dto.EmbeddedDTO.PostListData;
import project.tuthree.repository.PostTestPaperRepository;
import project.tuthree.service.PostFaqService;
import project.tuthree.service.PostNoticeService;
import project.tuthree.service.PostTestPaperService;
import project.tuthree.service.PostTestPaperService.PaperForm;
import project.tuthree.service.TeacherService;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostUserApiController {
    /**
     * responsebody 처리하기 valid
     */

    private final PostFaqService postFaqService;
    private final PostNoticeService postNoticeService;
    private final PostTestPaperService postTestPaperService;
    private final PostTestPaperRepository postTestPaperRepository;
    private final TeacherService teacherService;

    // TEST PAPER ////////////////////////////////
    @ResponseBody
    @GetMapping("/community/{page}")
    public ExistDataSuccessResponse CommunityList(@PathVariable("page") int page) {
        List<PostListDTO> list = postTestPaperService.communityFindByPage(page);
        PostListData data = new PostListData(postTestPaperRepository.testpaperHasRow(), list);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                page + "페이지의 글이 조회되었습니다.", data);
    }

    @ResponseBody
    @GetMapping("/community/id/{post_id}")
    public ExistDataSuccessResponse CommunityFind(@PathVariable("post_id") Long id) {
        PostListDTO dto = postTestPaperService.communityFindById(id);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                id + "번 게시글이 조회되었습니다.", dto);
    }


    //@RequestParam("file") MultipartFile multipartFile
    /** 커뮤니티 게시글 작성 json 저장*/
    @ResponseBody
    @PostMapping("/community/write")
    public NotExistDataResultResponse writeCommunity(@ModelAttribute PaperForm form) throws NoSuchAlgorithmException, IOException {
        List<String> list = new ArrayList<>();
        Long id = postTestPaperService.writeCommunity(form);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "번 게시글이 작성되었습니다.");
    }
    //파일 업로드 구현 후 md5 해쉬값 구현하기

    /**
     * 커뮤니티 게시글에 포함되는 파일 저장 -
     */
    @ResponseBody
    @PostMapping("/community/user/write")
    public void writec(@ModelAttribute PaperForm form) throws IOException {
        log.debug("title : " + form.getTitle());
        log.debug("filename : " + form.getFile().get(0).getOriginalFilename());
        String path = "/home/seojaehui/testform";
        if(!new File(path).exists()){
            new File(path).mkdir();
        }
        form.getFile().get(0).transferTo(new File(path +"/" + form.getFile().get(0).getOriginalFilename()));



    }

    @ResponseBody
    @PutMapping("/community/id/{post_id}")
    public NotExistDataResultResponse CommunityUpdate(@PathVariable("post_id") Long id, @RequestBody PostListDTO postListDTO) {
        Long updatedId = postTestPaperService.updateCommunity(id, postListDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "번 게시글이 수정되었습니다.");
    }


    // FAQ ////////////////////////////////
    @ResponseBody
    @GetMapping("/faq/{page}")
    public List<PostfaqDTO> FaqList(@PathVariable("page") int page) {
        return postFaqService.faqFindByPage(page);
    }

    @ResponseBody
    @GetMapping("/faq/id/{faq_id}")
    public PostfaqDTO FaqFind(@PathVariable("faq_id") Long id) {
        return postFaqService.faqFindById(id);
    }

    // NOTICE ////////////////////////////////
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

    /**
     * list return test logic
     *
     * @ResponseBody
     * @GetMapping("/testlist") public FileListDto ListWhat() {
     * List<String> list = new ArrayList<>();
     * list.add("hello");
     * list.add("test");
     * return new FileListDto(list);
     * }
     */

}
