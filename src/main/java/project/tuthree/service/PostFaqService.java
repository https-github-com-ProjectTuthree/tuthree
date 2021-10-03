package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.dto.EmbeddedDTO;
import project.tuthree.dto.EmbeddedDTO.PostListDTO;
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
    public List<PostListDTO> faqFindByPage(int page) {
        List<PostFaq> list = postFaqRepository.findByPage(page);
        if (list.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return list.stream()
                .map(m -> new PostListDTO(m.getId(), m.getAdmin().getId(), m.getTitle(),
                        m.getContent(), m.getView(), m.getWriteAt(), null, m.getSecret()))
                .collect(Collectors.toList());
    }

    /** faq id로 조회 */
    public PostListDTO faqFindById(Long id) {
        PostFaq postFaq = postFaqRepository.findById(id);
        return new PostListDTO(postFaq.getId(), postFaq.getAdmin().getId(), postFaq.getTitle(), postFaq.getContent(),
                postFaq.getView(), postFaq.getWriteAt(), null, postFaq.getSecret());
    }

    /**
     * faq 작성
     */
    public Long writeFaq(PostfaqDTO postfaqDTO) {
        PostFaq postFaq = postFaqMapper.toEntity(postfaqDTO);
        return postFaqRepository.writeFaq(postFaq);
    }

    /**
     * faq 수정
     */
    public Long updateFaq(Long id, PostfaqDTO postfaqDTO) {
        PostFaq faq = postFaqMapper.toEntity(postfaqDTO);
        return postFaqRepository.updateFaq(id, faq);
    }
}
