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
import project.tuthree.dto.post.PostTestPaperDTO;
import project.tuthree.dto.file.UserfileDTO;
import project.tuthree.mapper.PostTestPaperMapper;
import project.tuthree.mapper.UserFileMapper;
import project.tuthree.repository.PostTestPaperRepository;
import project.tuthree.repository.TeacherEntityRepository;
import project.tuthree.repository.UserFileRepository;


import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
    private final TeacherEntityRepository teacherRepository;
    private final UserFileMapper userFileMapper;
    private final UserFileRepository userFileRepository;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class PaperForm {
        @NotNull
        String userId;
        @NotNull
        String title;
        @NotNull
        String content;
        List<MultipartFile> file;
    }

    /** 커뮤니티 페이지 목록 조회하기 */
    public List<PostListDTO> communityFindByPage(int page) {
        List<PostTestPaper> list = testPaperRepository.findByPage(page);
        if (list.isEmpty()) {
            log.info("-------------------------errr-----------");
            throw new IllegalArgumentException();
        }

        return list.stream()
                .map(m -> new PostListDTO(m.getId(), m.getUserId().getId(), m.getTitle(), m.getContent(), m.getView(), m.getWriteAt(), m.getAlterAt(), m.getSecret()))
                .collect(Collectors.toList());
    }

//            return list.stream()
//                    .map(m -> postFaqMapper.toDto(m))
//            .collect(Collectors.toList());

    /** 커뮤니티 글 id로 조회하기 */
    public PostListDTO communityFindById(Long id) {
        PostTestPaper postTestPaper = testPaperRepository.findById(id);
        PostListDTO postListDTO = new PostListDTO(postTestPaper.getId(), postTestPaper.getUserId().getId(),
                postTestPaper.getTitle(), postTestPaper.getContent(), postTestPaper.getView(),
                postTestPaper.getWriteAt(), postTestPaper.getAlterAt(), postTestPaper.getSecret());
        return postListDTO;
    }

    /**
     * 커뮤니티 글 작성 --  코드가 너무 깔끔하지가 않다...
     */
    public Long writeCommunity(PaperForm form) throws NoSuchAlgorithmException, IOException {
        Teacher teacher = teacherRepository.findById(form.getUserId());
        PostTestPaperDTO postTestPaperDTO = new PostTestPaperDTO(null, teacher, form.getTitle(), form.getContent(), 0L, new Date(), null, Status.OPEN);
        log.info("======================" + teacher.getId());

        PostTestPaper post =  testPaperRepository.writeTestPaper(testPaperMapper.toEntity(postTestPaperDTO));

        List<String> saveNames = userFileRepository.saveFile(form.getFile(), POSTPAPER);

        for (int i = 0; i < saveNames.size(); i++) {
            UserfileDTO userfileDTO = new UserfileDTO(null, null, post, saveNames.get(i), form.getFile().get(i).getOriginalFilename());
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
            log.info("파일 " + i + "삭제되었음\n\n\n\n");
        }
        Long deletedId = testPaperRepository.deleteTestPaper(testPaperId);

        return deletedId;
    }


    /**
     * 커뮤니티 글 수정
     */

    public Long updateCommunity(Long id, PostListDTO postListDTO) {
        Teacher teacher = teacherRepository.findById(postListDTO.getUserId());
        PostTestPaperDTO postTestPaperDTO = new PostTestPaperDTO(postListDTO.getId(),
                teacher, postListDTO.getTitle(), postListDTO.getContent(), null,
                null, null, postListDTO.getSecret());
        PostTestPaper postTestPaper = testPaperMapper.toEntity(postTestPaperDTO);
        return testPaperRepository.updateTestPaper(id, postTestPaper);
    }


}
