package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.post.PostNotice;
import project.tuthree.dto.PostnoticeDTO;
import project.tuthree.mapper.PostNoticeMapper;
import project.tuthree.repository.PostNoticeRepository;

import java.util.Date;
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
    public List<PostnoticeDTO> noticeByPage(int page) {
        List<PostNotice> list = postNoticeRepository.findByPage(page);

        return list.stream()
                .map(m -> postNoticeMapper.toDto(m))
                .collect(Collectors.toList());
    }

    /** 공지사항 id로 조회 */
    public PostnoticeDTO noticeById(Long id) {
        PostNotice postNotice = postNoticeRepository.findById(id);
        return postNoticeMapper.toDto(postNotice);
    }

    /** 관리자 공지사항 작성 */
    public Long writeNotice(PostnoticeDTO postnoticeDTO){
        postnoticeDTO.setWriteAt(new Date());
        postnoticeDTO.setView(0L);
        System.out.println("postnoticeDTO.toString() = " + postnoticeDTO.toString());
        PostNotice postNotice = postNoticeMapper.toEntity(postnoticeDTO);
        return postNoticeRepository.writeNotice(postNotice);
    }

}