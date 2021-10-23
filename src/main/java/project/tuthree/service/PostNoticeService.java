package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostNotice;
import project.tuthree.dto.EmbeddedDTO;
import project.tuthree.dto.EmbeddedDTO.PostListDTO;
import project.tuthree.dto.EmbeddedDTO.PostListTypeDTO;
import project.tuthree.dto.EmbeddedDTO.PostSingleContentDTO;
import project.tuthree.dto.EmbeddedDTO.PostSingleContentTypeDTO;
import project.tuthree.dto.post.PostnoticeDTO;
import project.tuthree.mapper.PostNoticeMapper;
import project.tuthree.repository.PostNoticeRepository;
import project.tuthree.repository.UserFileRepository;

import static project.tuthree.exception.ExceptionSupplierImpl.wrap;


import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostNoticeService {

    private final PostNoticeRepository postNoticeRepository;
    private final PostNoticeMapper postNoticeMapper;
    private final UserFileRepository userFileRepository;

    /**
     * 공지사항 페이지 목록 조회하기
     */
    public List<PostListTypeDTO> noticeByPage(int page) {
        List<PostNotice> list = postNoticeRepository.findByPage(page);

        return list.stream()
                .map(m -> wrap(() -> new PostListTypeDTO(m.getId(), m.getAdmin().getId(), m.getTitle(), userFileRepository.unixToDate(m.getWriteAt()), m.getType().getKorType(), m.getView())))
                        .collect(Collectors.toList());
    }

    /** 공지사항 id로 조회 */
    public PostSingleContentTypeDTO noticeById(Long id) throws ParseException {
        PostNotice postNotice = postNoticeRepository.findById(id);
        return new PostSingleContentTypeDTO(postNotice.getId(), postNotice.getAdmin().getId(), postNotice.getTitle(), postNotice.getContent(), postNotice.getView(),
                userFileRepository.unixToDate(postNotice.getWriteAt()), postNotice.getType().getKorType(), postNotice.getSecret());
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
