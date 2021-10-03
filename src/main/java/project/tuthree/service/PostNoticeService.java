package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostNotice;
import project.tuthree.dto.EmbeddedDTO.PostListDTO;
import project.tuthree.dto.post.PostnoticeDTO;
import project.tuthree.mapper.PostNoticeMapper;
import project.tuthree.repository.PostNoticeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostNoticeService {

    private final PostNoticeRepository postNoticeRepository;
    private final PostNoticeMapper postNoticeMapper;

    /**
     * 공지사항 페이지 목록 조회하기
     */
    public List<PostListDTO> noticeByPage(int page) {
        List<PostNotice> list = postNoticeRepository.findByPage(page);
        if (list.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return list.stream()
                .map(m -> new PostListDTO(m.getId(), m.getAdmin().getId(), m.getTitle(), m.getContent(), m.getView(), m.getWriteAt(), null, m.getSecret()))
                .collect(Collectors.toList());
    }

    /** 공지사항 id로 조회 */
    public PostListDTO noticeById(Long id) {
        PostNotice postNotice = postNoticeRepository.findById(id);
        return new PostListDTO(postNotice.getId(), postNotice.getAdmin().getId(), postNotice.getTitle(), postNotice.getContent(), postNotice.getView(),
                postNotice.getWriteAt(), null, postNotice.getSecret());
    }

    /** 관리자 공지사항 작성 */
    public Long writeNotice(PostnoticeDTO postnoticeDTO){
        PostNotice postNotice = postNoticeMapper.toEntity(postnoticeDTO);
        return postNoticeRepository.writeNotice(postNotice);
    }

    /**
     * 공지사항 수정
     */
    public Long updateNotice(Long id, PostnoticeDTO postnoticeDTO) {
        PostNotice postNotice = postNoticeMapper.toEntity(postnoticeDTO);
        return postNoticeRepository.updateNotice(id, postNotice);
    }

}
