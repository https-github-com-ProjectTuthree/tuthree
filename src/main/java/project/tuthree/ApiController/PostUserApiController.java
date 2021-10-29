package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.ExistDoubleDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.ExistListDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.dto.EmbeddedDTO.PostListDTO;
import project.tuthree.dto.EmbeddedDTO.PostListTypeDTO;
import project.tuthree.dto.EmbeddedDTO.PostSingleContentDTO;
import project.tuthree.dto.EmbeddedDTO.PostSingleContentTypeDTO;
import project.tuthree.repository.PostFaqRepository;
import project.tuthree.repository.PostNoticeRepository;
import project.tuthree.repository.PostTestPaperRepository;
import project.tuthree.repository.UserFileRepository;
import project.tuthree.service.PostFaqService;
import project.tuthree.service.PostNoticeService;
import project.tuthree.service.PostTestPaperService;
import project.tuthree.service.PostTestPaperService.PaperForm;
import project.tuthree.service.UserFileService;
import project.tuthree.service.UserFileService.FileIdName;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostUserApiController {
    private final PostFaqService postFaqService;
    private final PostNoticeService postNoticeService;
    private final PostTestPaperService postTestPaperService;
    private final PostTestPaperRepository postTestPaperRepository;
    private final PostNoticeRepository postNoticeRepository;
    private final PostFaqRepository postFaqRepository;
    private final UserFileRepository userFileRepository;
    private final UserFileService userFileService;


    // TEST PAPER ////////////////////////////////
    /** 커뮤니티 글 목록 조회 */
    @GetMapping("/community/{page}")
    public ExistListDataSuccessResponse CommunityList(@PathVariable("page") int page) {
        List<PostListDTO> list = postTestPaperService.communityFindByPage(page);
        log.debug("\n---- 사용자 커뮤니티 목록 조회 [PAGE : " + page + "] ----\n");
        return new ExistListDataSuccessResponse(StatusCode.OK.getCode(),
                page + "페이지의 글이 조회되었습니다.", postTestPaperRepository.testpaperHasRow(), list);
    }

    /** 커뮤니티 특정 글 조회 */ //파일 이름도 같이 넘겨주기
    @GetMapping("/community/id/{post_id}")
    public ExistDoubleDataSuccessResponse CommunityFind(@PathVariable("post_id") Long id) throws IOException, ParseException {
        PostSingleContentDTO dto = postTestPaperService.communityFindById(id);
        List<FileIdName> userfileDTOList = userFileService.userFileFindByPostId(id);
        log.debug("\n---- 사용자 커뮤니티 글 조회 [ID : " + id + "] ----\n");
        return new ExistDoubleDataSuccessResponse(StatusCode.OK.getCode(), id + "번 게시글이 조회되었습니다.", dto, userfileDTOList);
    }

    /**
     * 커뮤니티 게시글 검색
     */
    @GetMapping(value = "/community")
    public ExistListDataSuccessResponse CommunitySearch(@RequestParam(value = "keyword") String keyword,
                                                        @RequestParam(required = false, defaultValue = "1", value = "page") int page) {
        List<PostListDTO> list = postTestPaperService.communityFindByKeyword(keyword, page);
        return new ExistListDataSuccessResponse(StatusCode.OK.getCode(),
                keyword + "에 대한 글 목록 중 " + page + "페이지를 조회하였습니다.",
                postTestPaperRepository.testpaperHasRowByKeyword(keyword), list);
    }

    /**
     * 파일 다운로드 테스트
     */
    @ResponseBody
    @GetMapping("/community/download/{file_id}")
    public void DownloadFile(HttpServletResponse response, @PathVariable("file_id") Long id) throws IOException {
        userFileRepository.downloadUserFile(response, id);
    }
    
    /** 커뮤니티 게시글 작성 -> post_testpaper & user_file*/
    @PostMapping("/community/write")
    public NotExistDataResultResponse writeCommunity(@ModelAttribute PaperForm form) throws NoSuchAlgorithmException, IOException {
        Long id = postTestPaperService.writeCommunity(form);
        log.debug("\n---- 사용자 커뮤니티 글 작성 [ID : " + id + "] ----\n");
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "번 게시글이 작성되었습니다.");
    }

    /** 게시글 수정 - 이미지도 수정하도록 */
    @PutMapping("/community/id/{post_id}")
    public NotExistDataResultResponse updateCommunity(@PathVariable("post_id") Long id, @ModelAttribute PaperForm form) throws NoSuchAlgorithmException, IOException {
        /** 이전에 그 글이랑 관련된 자료는 다 지우고 새로 수정하기?? 흠... */
        Long updatedId = postTestPaperService.updateCommunity(id, form);
        log.debug("\n---- 사용자 커뮤니티 글 수정 [ID : " + id + "] ----\n");
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), updatedId + "번 게시글이 수정되었습니다.");
    }

    /**
     * 게시글 삭제 - 게시글과 관련된 이미지도 삭제하도록
     */
    @DeleteMapping("/community/id/{post_id}")
    public NotExistDataResultResponse deleteCommunity(@PathVariable("post_id") Long id) {
        Long deletedId = postTestPaperService.deleteCommunity(id);
        log.debug("\n---- 사용자 커뮤니티 글 삭제 [ID : " + deletedId + "] ----\n");
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), deletedId + "번 게시글이 삭제되었습니다.");
    }

    // FAQ ////////////////////////////////
    /** FAQ 페이지 목록 조회 */
    @GetMapping("/faq/{page}")
    public ExistListDataSuccessResponse FaqList(@PathVariable("page") int page) {
        List<PostSingleContentTypeDTO> postSingleContentTypeDTO = postFaqService.faqFindByPage(page);
        log.debug("\n---- 사용자 FAQ 목록 조회 [PAGE : " + page + "] ----\n");
        return new ExistListDataSuccessResponse(StatusCode.OK.getCode(),
                page + "페이지의 FAQ가 조회되었습니다.", postFaqRepository.faqHasRow(), postSingleContentTypeDTO);
    }

    /** FAQ 특정 글 조회 */
    @GetMapping("/faq/id/{faq_id}")
    public ExistDataSuccessResponse FaqFind(@PathVariable("faq_id") Long id) throws ParseException {
        PostSingleContentTypeDTO postListDTO = postFaqService.faqFindById(id);
        log.debug("\n---- 사용자 FAQ 게시글 조회 [ID : " + id + "] ----\n");
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                id + "번 게시글이 조회되었습니다.", postListDTO);
    }

    /** FAQ 조회수 up */
    @GetMapping("/faq/view/{faq_id}")
    public NotExistDataResultResponse viewFaqUpdate(@PathVariable("faq_id") Long id) {
        Long updatedId = postFaqRepository.updateFaq(id);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), updatedId + "번 게시글의 조회수가 업로드됐습니다.");
    }

    // NOTICE ////////////////////////////////
    /** 공지사항 페이지 목록 조회 */
    @GetMapping("/notice/{page}")
    public ExistListDataSuccessResponse NoticeList(@PathVariable("page") int page) {
        List<PostListTypeDTO> postListDTO = postNoticeService.noticeByPage(page);
        log.debug("\n---- 사용자 공지사항 목록 조회 [PAGE : " + page + "] ----\n");
        return new ExistListDataSuccessResponse(StatusCode.OK.getCode(),
                page + "페이지의 공지사항이 조회되었습니다.", postNoticeRepository.noticeHasRow(), postListDTO);
    }

    /** 공지사항 특정 글 조회 */
    @GetMapping("/notice/id/{notice_id}")
    public ExistDataSuccessResponse NoticeFind(@PathVariable("notice_id") Long id) throws ParseException {
        PostSingleContentTypeDTO postListDTO = postNoticeService.noticeById(id);
        log.debug("\n---- 사용자 공지사항 게시글 조회 [ID : " + id + "] ----\n");
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(), id + "번 공지사항이 조회되었습니다.", postListDTO);
    }
}
