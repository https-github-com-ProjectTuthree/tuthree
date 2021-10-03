package project.tuthree.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.tuthree.domain.file.UserFile;
import project.tuthree.dto.UserfileDTO;
import project.tuthree.mapper.UserFileMapper;
import project.tuthree.repository.UserFileRepository;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service
@RequiredArgsConstructor
public class UserFileService {

    private final UserFileMapper userFileMapper;
    private final UserFileRepository userFileRepository;

    /**
     * post_id로 파일 찾기
     */
    public List<UserfileDTO> userFileFindByPostId(Long postId) {
        List<UserFile> fileList = userFileRepository.userFileFindByPostId(postId);

        return fileList.stream()
                .map(m -> userFileMapper.toDto(m))
                .collect(Collectors.toList());
    }


}
