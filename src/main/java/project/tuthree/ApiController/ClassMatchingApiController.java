package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.tuthree.repository.UserEntityRepository;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClassMatchingApiController {

    private UserEntityRepository userEntityRepository;

    @GetMapping("/tutor/list/{page}")
    public void FindTutor() {

    }
}
