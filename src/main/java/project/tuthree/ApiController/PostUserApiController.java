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
import project.tuthree.repository.PostFaqRepository;
import project.tuthree.repository.PostNoticeRepository;
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
    private final PostNoticeRepository postNoticeRepository;
    private final PostFaqRepository postFaqRepository;

    // TEST PAPER ////////////////////////////////
    @ResponseBody
    @GetMapping("/community/{page}")
    public ExistDataSuccessResponse CommunityList(@PathVariable("page") int page) {
        List<PostListDTO> list = postTestPaperService.communityFindByPage(page);
        PostListData data = new PostListData(postTestPaperRepository.testpaperHasRow(), list);
        log.debug("\n---- 사용자 커뮤니티 목록 조회 [PAGE : " + page + "] ----\n");
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                page + "페이지의 글이 조회되었습니다.", data);
    }

    @ResponseBody
    @GetMapping("/community/id/{post_id}")
    public ExistDataSuccessResponse CommunityFind(@PathVariable("post_id") Long id) {
        PostListDTO dto = postTestPaperService.communityFindById(id);
        log.debug("\n---- 사용자 커뮤니티 글 조회 [ID : " + id + "] ----\n");
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                id + "번 게시글이 조회되었습니다.", dto);
    }


    /** 커뮤니티 게시글 작성 -> post_testpaper & user_file*/
    @ResponseBody
    @PostMapping("/community/write")
    public NotExistDataResultResponse writeCommunity(@ModelAttribute PaperForm form) throws NoSuchAlgorithmException, IOException {
        List<String> list = new ArrayList<>();
        Long id = postTestPaperService.writeCommunity(form);
        log.debug("\n---- 사용자 커뮤니티 글 작성 [ID : " + id + "] ----\n");
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "번 게시글이 작성되었습니다.");
    }
    //파일 업로드 구현 후 md5 해쉬값 구현하기

    /** 게시글 수정 - 이미지도 수정하도록 */
    @ResponseBody
    @PutMapping("/community/id/{post_id}")
    public NotExistDataResultResponse CommunityUpdate(@PathVariable("post_id") Long id, @RequestBody PostListDTO postListDTO) {
        Long updatedId = postTestPaperService.updateCommunity(id, postListDTO);
        log.debug("\n---- 사용자 커뮤니티 글 수정 [ID : " + id + "] ----\n");
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "번 게시글이 수정되었습니다.");
    }


    // FAQ ////////////////////////////////
    @ResponseBody
    @GetMapping("/faq/{page}")
    public ExistDataSuccessResponse FaqList(@PathVariable("page") int page) {
        List<PostListDTO> postListDTO = postFaqService.faqFindByPage(page);
        PostListData data = new PostListData(postFaqRepository.faqHasRow(), postListDTO);
        log.debug("\n---- 사용자 FAQ 목록 조회 [PAGE : " + page + "] ----\n");
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                page + "페이지의 FAQ가 조회되었습니다.", data);

    }

    @ResponseBody
    @GetMapping("/faq/id/{faq_id}")
    public ExistDataSuccessResponse FaqFind(@PathVariable("faq_id") Long id) {
        PostListDTO postListDTO = postFaqService.faqFindById(id);
        log.debug("\n---- 사용자 FAQ 게시글 조회 [ID : " + id + "] ----\n");
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                id + "번 게시글이 조회되었습니다.", postListDTO);
    }

    // NOTICE ////////////////////////////////
    @ResponseBody
    @GetMapping("/notice/{page}")
    public ExistDataSuccessResponse NoticeList(@PathVariable("page") int page) {
        List<PostListDTO> postListDTO = postNoticeService.noticeByPage(page);
        PostListData data = new PostListData(postNoticeRepository.noticeHasRow(), postListDTO);
        log.debug("\n---- 사용자 공지사항 목록 조회 [PAGE : " + page + "] ----\n");
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                page + "페이지의 공지사항이 조회되었습니다.", data);
    }

    @ResponseBody
    @GetMapping("/notice/id/{notice_id}")
    public ExistDataSuccessResponse NoticeFind(@PathVariable("notice_id") Long id) {
        PostListDTO postListDTO = postNoticeService.noticeById(id);
        log.debug("\n---- 사용자 공지사항 게시글 조회 [ID : " + id + "] ----\n");
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(), id + "번 공지사항이 조회되었습니다.", postListDTO);
    }


}
