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
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.ExistDoubleDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.ExistListDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;

import javax.validation.Valid;
import java.io.IOException;

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
    public NotExistDataResultResponse ParentRegister(@RequestBody @Valid UserRegisterDTO registerDTO){
        log.debug("\n---- 학부모 회원 가입 ----\n");
        String id = userRegisterService.createParent(registerDTO);
        if (id == "중복"){
            return new NotExistDataResultResponse(StatusCode.CREATED.getCode(),   "중복된 아이디입니다.");
        }
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "님 안녕하세요.");

    }

    //학생 회원가입
    @PostMapping("/register/tutee")
    public NotExistDataResultResponse StudentRegister(@RequestBody @Valid StudentRegisterDTO registerDTO) throws IOException{
        log.debug("\n---- 학생 회원 가입 ----\n");
        String id = userRegisterService.createStudent(registerDTO);
        if (id == "중복"){
            return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), "중복된 아이디입니다.");
        }
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "님 안녕하세요.");
    }

    //선생님 회원가입
    @PostMapping("/register/tutor")
    public NotExistDataResultResponse TeacherRegister(@RequestBody @Valid TeacherRegisterDTO registerDTO) throws IOException {
        log.debug("\n---- 선생님 회원 가입 ----\n");
        String id = userRegisterService.createTeacher(registerDTO);
        if (id == "중복"){
            return new NotExistDataResultResponse(StatusCode.CREATED.getCode(),  "중복된 아이디입니다.");
        }
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "님 안녕하세요.");
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


    //정보 수정
    @PutMapping("/user/tutorclass/{id}")
    public String teacherUpdate(@PathVariable String id, @RequestBody TeacherUpdateDTO updateDTO){
        return userRegisterService.teacherUpdate(id, updateDTO);
    }
    //정보 수정
    @PutMapping("/user/tuteeclass/{id}")
    public String studentUpdate(@PathVariable String id, @RequestBody StudentUpdateDTO updateDTO){
        return userRegisterService.studentUpdate(id, updateDTO);
    }
    //정보 수정
/*    @PutMapping("/user/mypage")
    public Long update(@RequestBody UserResponseDTO responseDTO){
        return userRegisterService.update(id, responseDTO);
    }*/


/*    //로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginDTO loginDTO){
        log.debug("\n---- 로그인 ----\n");
        return userRegisterService.userLogin(loginDTO);
    }*/


}


