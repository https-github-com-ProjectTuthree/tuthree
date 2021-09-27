package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.tuthree.domain.post.PostNotice;
import project.tuthree.dto.PostnoticeDTO;
import project.tuthree.mapper.PostNoticeMapper;
import project.tuthree.repository.PostNoticeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostNoticeService {

    private final PostNoticeRepository postNoticeRepository;
    private final PostNoticeMapper postNoticeMapper;

    /**
     * 공지사항 페이지 목록 조회하기
     * @return DTO
     */
    public List<PostnoticeDTO> noticeByPage(int page) {
        List<PostNotice> list = postNoticeRepository.findByPage(page);

        return list.stream()
                .map(m -> postNoticeMapper.toDto(m))
                .collect(Collectors.toList());
    }

    public PostnoticeDTO noticeById(Long id) {
        PostNotice postNotice = postNoticeRepository.findById(id);
        return postNoticeMapper.toDto(postNotice);
    }

    /** 관리자 공지사항 작성 */
    public Long writeNotice(PostnoticeDTO postnoticeDTO){
        PostNotice postNotice = postNoticeMapper.toEntity(postnoticeDTO);
        return postNoticeRepository.writeNotice(postNotice);
    }

}
