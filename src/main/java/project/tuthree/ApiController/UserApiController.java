package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.tuthree.domain.user.User;
import project.tuthree.dto.UserRegisterDTO;
import project.tuthree.dto.StudentRegisterDTO;
import project.tuthree.dto.TeacherRegisterDTO;
import project.tuthree.service.UserRegisterService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserRegisterService userRegisterService;

    //id체크
    @GetMapping("/register/{id}/checkid")
    public ResponseEntity<Boolean> checkId(@PathVariable String id) {
        return ResponseEntity.ok(userRegisterService.checkId(id));
    }

    //학부모 회원가입
    @PostMapping("/register/parent")
    public String ParentRegister(@RequestBody UserRegisterDTO registerDTO) {
        log.debug("\n---- 학부모 회원 가입 ----\n");
        return userRegisterService.createParent(registerDTO);
    }

    //학생 회원가입
    @PostMapping("/register/tutee")
    public String StudentRegister(@RequestBody StudentRegisterDTO registerDTO) {
        log.debug("\n---- 학생 회원 가입 ----\n");
        return userRegisterService.createStudent(registerDTO);
    }

    //선생님 회원가입
    @PostMapping("/register/tutor")
    public String TeacherRegister(@RequestBody TeacherRegisterDTO registerDTO) {
        log.debug("\n---- 학생 회원 가입 ----\n");
        return userRegisterService.createTeacher(registerDTO);
    }


}


