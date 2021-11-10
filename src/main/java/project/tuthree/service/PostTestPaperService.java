package project.tuthree.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.domain.Status;
import project.tuthree.domain.post.PostTestPaper;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.EmbeddedDTO.PostListDTO;
import project.tuthree.dto.EmbeddedDTO.PostSingleContentDTO;
import project.tuthree.dto.post.PostTestPaperDTO;
import project.tuthree.dto.file.UserfileDTO;
import project.tuthree.mapper.PostTestPaperMapper;
import project.tuthree.mapper.UserFileMapper;
import project.tuthree.repository.PostTestPaperRepository;
import project.tuthree.repository.UserEntityRepository;
import project.tuthree.repository.UserFileRepository;

import static project.tuthree.exception.ExceptionSupplierImpl.wrap;


import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static project.tuthree.repository.UserFileRepository.FileType.POSTPAPER;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostTestPaperService {

    private final EntityManager em;
    private final PostTestPaperRepository testPaperRepository;
    private final PostTestPaperMapper testPaperMapper;
    private final UserEntityRepository userEntityRepository;
    private final UserFileMapper userFileMapper;
    private final UserFileRepository userFileRepository;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class PaperForm {
        @NotBlank(message = "작성자 아이디 입력값 필요")
        String userId;
        @NotBlank(message = "게시글 제목 입력값 필요")
        String title;
        @NotBlank(message = "게시글 내용 입력값 필요")
        String content;
        @NotNull(message = "게시글 비밀글 여부 입력값 필요 : [OPEN, CLOSE]")
        Status secret;
        @NotNull(message = "게시글 파일 첨부 필요 : [pdf]")
        List<MultipartFile> file;
    }

    /** 커뮤니티 페이지 목록 조회하기 */
    public List<PostListDTO> communityFindByPage(int page) {
        List<PostTestPaper> list = testPaperRepository.findByPage(page);
        return list.stream()
                .map(m -> wrap(() -> new PostListDTO(m.getId(), m.getUserId().getId(), m.getTitle(), userFileRepository.unixToDate(m.getWriteAt()), m.getView())))
                .collect(Collectors.toList());
    }

    /** 커뮤니티 글 id로 조회하기 */
    public PostSingleContentDTO communityFindById(Long id) throws ParseException {
        PostTestPaper postTestPaper = testPaperRepository.findById(id);
        PostSingleContentDTO postListDTO = new PostSingleContentDTO(postTestPaper.getId(), postTestPaper.getUserId().getId(),
                postTestPaper.getTitle(), postTestPaper.getContent(), postTestPaper.getView(), userFileRepository.unixToDate(postTestPaper.getWriteAt()), postTestPaper.getSecret());
        return postListDTO;
    }

    /**
     * 커뮤니티 글 검색
     */
    public List<PostListDTO> communityFindByKeyword(String keyword, int page) {
        List<PostTestPaper> list =testPaperRepository.findByKeyword(keyword, page);
        if(list.isEmpty()) throw new NullPointerException();
        return list.stream()
                .map(m -> wrap(() -> new PostListDTO(m.getId(), m.getUserId().getId(), m.getTitle(), userFileRepository.unixToDate(m.getWriteAt()), m.getView())))
                        .collect(Collectors.toList());
    }

    /**
     * 커뮤니티 글 작성 --  코드가 너무 깔끔하지가 않다...
     */
    public Long writeCommunity(PaperForm form) throws NoSuchAlgorithmException, IOException {
        Teacher teacher = userEntityRepository.teacherFindById(form.getUserId());
        PostTestPaperDTO postTestPaperDTO = new PostTestPaperDTO(null, teacher, form.getTitle(), form.getContent(), 0L, new Date(), null, form.getSecret());

        PostTestPaper post =  testPaperRepository.writeTestPaper(testPaperMapper.toEntity(postTestPaperDTO));
        List<String> saveNames = userFileRepository.saveFile(form.getFile(), POSTPAPER);

        for (int i = 0; i < saveNames.size(); i++) {
            UserfileDTO userfileDTO = new UserfileDTO(post, saveNames.get(i), form.getFile().get(i).getOriginalFilename());
            userFileRepository.userFileSave(userFileMapper.toEntity(userfileDTO));
        }
        return post.getId();
    }

    /** 커뮤니티 글 삭제 -> post_testpaper id를 가진 user file 삭제, user file 삭제 */

    public Long deleteCommunity(Long testPaperId){
        List<Long> fileList = em.createQuery("select f.id from UserFile f where f.testpaperId.id = :id")
                .setParameter("id", testPaperId)
                .getResultList();
        for (int i = 0; i < fileList.size(); i++) {
            userFileRepository.deleteUserFile(fileList.get(i));
        }
        return testPaperRepository.deleteTestPaper(testPaperId);
    }


    /**
     * 커뮤니티 글 수정
     */

    public Long updateCommunity(Long id, PaperForm form) throws NoSuchAlgorithmException, IOException {
        List<Long> list = em.createQuery("select f.id from UserFile f where f.testpaperId.id = :id")
                .setParameter("id", id)
                .getResultList();
        for (int i = 0; i < list.size(); i++) {
            userFileRepository.deleteUserFile(list.get(i));
        }
        Teacher teacher = userEntityRepository.teacherFindById(form.getUserId());
        PostTestPaperDTO postTestPaperDTO = new PostTestPaperDTO(id, teacher, form.getTitle(),
                form.getContent(), null, null, null, form.getSecret());
        PostTestPaper postTestPaper = testPaperRepository.updateTestPaper(id, testPaperMapper.toEntity(postTestPaperDTO));
        List<String> saveNames = userFileRepository.saveFile(form.getFile(), POSTPAPER);

        for (int i = 0; i < saveNames.size(); i++) {
            UserfileDTO userfileDTO = new UserfileDTO(postTestPaper, saveNames.get(i), form.getFile().get(i).getOriginalFilename());
            userFileRepository.userFileSave(userFileMapper.toEntity(userfileDTO));
        }
        return postTestPaper.getId();
    }

}
