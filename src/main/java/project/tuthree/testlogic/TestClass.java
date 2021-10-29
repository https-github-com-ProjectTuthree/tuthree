package project.tuthree.testlogic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.repository.UserFileRepository;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestClass {

    private final UserFileRepository userFileRepository;

    @PostMapping("/test_test")
    public void test(@ModelAttribute List<MultipartFile> file) throws NoSuchAlgorithmException, IOException {

        List<String> strings = userFileRepository.saveFile(file, UserFileRepository.FileType.POSTPAPER);

        for (String s : strings) {
            log.info("\n" + s);
        }

    }
}
