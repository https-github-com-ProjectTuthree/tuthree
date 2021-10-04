package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.User;
import project.tuthree.dto.*;
import project.tuthree.dto.user.*;
import project.tuthree.service.UserRegisterService;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

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

/*    //기본정보조회
    @GetMapping("/user/mypage/{id}")
    public UserResponseDTO findUserInfo(@ModelAttribute UserResponseDTO userPage, Model model){
        return userRegisterService.findByInfo(id, grade);
    }*/

    //튜터 과외정보조회
    @GetMapping("/user/tutorclass/{id}") //토큰값으로 조회하게 바꾸기
    public TeacherResponseDTO findTutorInfo(@PathVariable String id){
        return userRegisterService.findTutorId(id);
    }

    //튜티
    @GetMapping("/user/tuteeclass/{id}")
    public StudentResponseDTO findTuteeInfo(@PathVariable String id){
        return userRegisterService.findTuTeeId(id);
    }


    /*//정보 수정
    @PutMapping("/user/tutorclass/{id}")
    public Long update(@PathVariable String id, @RequestBody TeacherResponseDTO responseDTO){
        return userRegisterService.update(id, responseDTO);
    }
    //정보 수정
    @PutMapping("/user/tuteeclass/{id}")
    public Long update(@PathVariable String id, @RequestBody StudentResponseDTO responseDTO){
        return userRegisterService.update(id, responseDTO);
    }
    //정보 수정
    @PutMapping("/user/mypage")
    public Long update(@RequestBody UserResponseDTO responseDTO){
        return userRegisterService.update(id, responseDTO);
    }
*/

/*    //로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginDTO loginDTO){
        log.debug("\n---- 로그인 ----\n");
        return userRegisterService.userLogin(loginDTO);
    }*/


}


