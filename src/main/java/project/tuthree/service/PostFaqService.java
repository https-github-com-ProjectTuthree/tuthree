package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.dto.PostfaqDTO;
import project.tuthree.mapper.PostFaqMapper;
import project.tuthree.repository.PostFaqRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostFaqService {

    private final PostFaqRepository postFaqRepository;
    private final PostFaqMapper postFaqMapper;

    /** faq 페이지 목록 조회 */
    public List<PostfaqDTO> faqFindByPage(int page) {
        List<PostFaq> list = postFaqRepository.findByPage(page);
        if (list.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return list.stream()
                .map(m -> postFaqMapper.toDto(m))
                .collect(Collectors.toList());
    }

    /** faq id로 조회 */
    public PostfaqDTO faqFindById(Long id) {
        PostFaq postFaq = postFaqRepository.findById(id);

        return postFaqMapper.toDto(postFaq);
    }

    /**
     * faq 작성
     */
    public Long writeFaq(PostfaqDTO postfaqDTO) {
        postfaqDTO.setWriteAt(new Date());
        //postfaqDTO.setView(0L);

        return postFaqRepository.writeFaq(postFaqMapper.toEntity(postfaqDTO));
    }

    /**
     * faq 수정
     */
    public Long updateFaq(Long id, PostfaqDTO postfaqDTO) {
        postfaqDTO.setAlterAt(new Date());
        PostFaq faq = postFaqMapper.toEntity(postfaqDTO);
        postFaqRepository.updateFaq(id, faq);
        return id;
    }


}
