package project.tuthree.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostNotice;
import project.tuthree.domain.post.PostTestPaper;
import project.tuthree.dto.PostTestPaperDTO;
import project.tuthree.dto.PostnoticeDTO;
import project.tuthree.mapper.PostTestPaperMapper;
import project.tuthree.repository.PostTestPaperRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostTestPaperService {

    private final PostTestPaperRepository testPaperRepository;
    private final PostTestPaperMapper testPaperMapper;

    @Getter
    @AllArgsConstructor
    public class PostListDTO {
        Long id;
        String userId;
        String title;
        String content;
        Long view;
        Date writeAt;
        Date alterAt;
    }

    /** 커뮤니티 페이지 목록 조회하기 */
    public List<PostListDTO> testpsperByPage(int page) {
        List<PostTestPaper> list = testPaperRepository.findByPage(page);
        if (list.isEmpty()) {
            log.info("-------------------------errr-----------");
            throw new IllegalArgumentException();
        }

        return list.stream()
                .map(m -> new PostListDTO(m.getId(), m.getUserId().getId(), m.getTitle(), m.getContent(), m.getView(), m.getWriteAt(), m.getAlterAt()))
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
                postTestPaper.getWriteAt(), postTestPaper.getAlterAt());
        return postListDTO;
    }

    /**  커뮤니티 글 작성 */

    /**
     * 커뮤니티 글 수정 - 이것도 결국 파일 다루기
     */
//
//    public Long updateTestPaper(Long id, PostTestPaperDTO postTestPaperDTO) {
//        postTestPaperDTO.postTestPaperAlterAt();
//        PostTestPaper postTestPaper = testPaperMapper.toEntity(postTestPaperDTO);
//        testPaperRepository.updateTestPaper(id, postTestPaper);
//        return id;
//    }
}
