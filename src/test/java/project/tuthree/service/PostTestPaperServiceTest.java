package project.tuthree.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class PostTestPaperServiceTest {

    @Autowired
    private PostTestPaperService postTestPaperService;

    @Test
    public void hash검증() throws NoSuchAlgorithmException {

    }


}