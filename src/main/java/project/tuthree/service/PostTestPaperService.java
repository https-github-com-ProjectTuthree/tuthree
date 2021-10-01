package project.tuthree.service;

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

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostTestPaperService {

    private final PostTestPaperRepository testPaperRepository;
    private final PostTestPaperMapper testPaperMapper;

    /** 커뮤니티 페이지 목록 조회하기 */
    public List<PostTestPaperDTO> testpsperByPage(int page) {
        List<PostTestPaper> list = testPaperRepository.findByPage(page);
        if (list.isEmpty()) {
            log.info("-------------------------errr-----------");
            throw new IllegalArgumentException();
        }
        return testPaperMapper.toDto(list);
    }

//            return list.stream()
//                    .map(m -> postFaqMapper.toDto(m))
//            .collect(Collectors.toList());


    /** 커뮤니티 글 id로 조회하기 */
    public PostTestPaperDTO testpaperById(Long id) {
        PostTestPaper postTestPaper = testPaperRepository.findById(id);
        return testPaperMapper.toDto(postTestPaper);
    }

    /**  커뮤니티 글 작성 */

    /**
     * 커뮤니티 글 수정 - 이것도 결국 파일 다루기
     */

    public Long updateTestPaper(Long id, PostTestPaperDTO postTestPaperDTO) {
        postTestPaperDTO.postTestPaperAlterAt();
        PostTestPaper postTestPaper = testPaperMapper.toEntity(postTestPaperDTO);
        testPaperRepository.updateTestPaper(id, postTestPaper);
        return id;
    }
}
