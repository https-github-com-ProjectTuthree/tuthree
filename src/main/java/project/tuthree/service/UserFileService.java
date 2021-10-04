package project.tuthree.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.tuthree.domain.file.UserFile;
import project.tuthree.mapper.UserFileMapper;
import project.tuthree.repository.UserFileRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Service
@RequiredArgsConstructor
public class UserFileService {

    private final UserFileMapper userFileMapper;
    private final UserFileRepository userFileRepository;

    @Getter
    @AllArgsConstructor
    public static class FileIdName{
        Long id;
        String name;
    }

    /**
     * post 조회 시, 파일 이름 반환
     */
    public List<FileIdName> userFileFindByPostId(Long postId) {
        List<UserFile> fileList = userFileRepository.userFileFindByPostId(postId);
        return fileList.stream()
                .map(m -> new FileIdName(m.getId(), m.getRealTitle()))
                .collect(Collectors.toList());

    }


}
