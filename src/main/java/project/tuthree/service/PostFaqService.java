package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.dto.EmbeddedDTO.PostSingleContentTypeDTO;
import project.tuthree.dto.post.PostfaqDTO;
import project.tuthree.mapper.PostFaqMapper;
import project.tuthree.repository.PostFaqRepository;
import project.tuthree.repository.UserFileRepository;

import static project.tuthree.exception.ExceptionSupplierImpl.wrap;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostFaqService {

    private final PostFaqRepository postFaqRepository;
    private final PostFaqMapper postFaqMapper;
    private final UserFileRepository userFileRepository;

    /** faq 페이지 목록 조회 */
    public List<PostSingleContentTypeDTO> faqFindByPage(int page) {
        List<PostFaq> list = postFaqRepository.findByPage(page);
        return list.stream()
                .map(m -> wrap(() -> new PostSingleContentTypeDTO(m.getId(), m.getAdmin().getId(), m.getTitle(), m.getContent(), m.getView(), userFileRepository.unixToDate(m.getWriteAt()), m.getType().getKorType(), m.getSecret())))
                .collect(Collectors.toList());
    }

    /** faq id로 조회 */
    public PostSingleContentTypeDTO faqFindById(Long id) throws ParseException {
        PostFaq postFaq = postFaqRepository.findById(id);
        return new PostSingleContentTypeDTO(postFaq.getId(), postFaq.getAdmin().getId(), postFaq.getTitle(), postFaq.getContent(),
                postFaq.getView(), userFileRepository.unixToDate(postFaq.getWriteAt()), postFaq.getType().getKorType(), postFaq.getSecret());
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
