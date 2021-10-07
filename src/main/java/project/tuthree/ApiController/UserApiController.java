package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.tuthree.ApiController.EmbeddedResponse.NonValueNotExistDataResultResponse;
import project.tuthree.controller.JwtController;
import project.tuthree.domain.user.Grade;
import project.tuthree.dto.user.*;
import project.tuthree.repository.UserFileRepository;
import project.tuthree.service.PostFindService;
import project.tuthree.service.UserRegisterService;
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer";

    private final UserRegisterService userRegisterService;
    private final JwtController jwtController;
    private final PostFindService postFindService;
    private final UserFileRepository userFileRepository;

    /** id체크 **/
    @GetMapping("/register/{id}/checkid")
    public ResponseEntity<Boolean> checkId(@PathVariable String id) {
        return ResponseEntity.ok(userRegisterService.checkId(id));
    }

    /** 학부모 회원가입 **/
    @PostMapping("/register/parent")
    public NotExistDataResultResponse ParentRegister(@ModelAttribute @Valid UserRegisterDTO registerDTO) {
        log.debug("\n---- 학부모 회원 가입 ----\n");
        String id = userRegisterService.createParent(registerDTO);
        if (id == "중복") {
            return new NotExistDataResultResponse(StatusCode.CONFLICT.getCode(), "중복된 아이디입니다.");
        }
        /**json파일 저장**/
        userFileRepository.jsonPParse(registerDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "님 안녕하세요.");
    }

    /**학생 회원가입**/
    @PostMapping("/register/tutee")
    public NotExistDataResultResponse StudentRegister(@ModelAttribute @Valid StudentRegisterDTO registerDTO) throws IOException {
        log.debug("\n---- 학생 회원 가입 ----\n");
        String id = userRegisterService.createStudent(registerDTO);
        if (id == "중복") {
            return new NotExistDataResultResponse(StatusCode.CONFLICT.getCode(), "중복된 아이디입니다.");
        }
        /**json파일 저장**/
        userFileRepository.jsonSParse(registerDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "님 안녕하세요.");
    }

    /**선생님 회원가입**/
    @PostMapping("/register/tutor")
    public NotExistDataResultResponse TeacherRegister(@ModelAttribute @Valid TeacherRegisterDTO registerDTO) {
        log.debug("\n---- 선생님 회원 가입 ----\n");
        String id = userRegisterService.createTeacher(registerDTO);

        if (id == "중복") {
            return new NotExistDataResultResponse(StatusCode.CONFLICT.getCode(), "중복된 아이디입니다.");
        }
        /**json파일 저장**/
        userFileRepository.jsonTParse(registerDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "님 안녕하세요.");
    }

/*    //기본정보조회
    @GetMapping("/user/mypage/{id}")
    public UserResponseDTO findUserInfo(HttpServletResponse response){
        return userRegisterService.findByInfo(id, grade);
    }*/

    //튜터 과외정보조회
    @GetMapping("/user/tutorclass/{id}") //토큰값으로 조회하게 바꾸기
    public ExistDataSuccessResponse findTutorInfo(@PathVariable String id, HttpServletResponse response) {
        TeacherResponseDTO responseDTO = userRegisterService.findTutorId(id);
        log.debug("\n---- 과외정보조회 ----\n");
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(), id + "의 과외정보를 조회합니다.", responseDTO);

    }

    //튜티
    @GetMapping("/user/tuteeclass/{id}")
    public ExistDataSuccessResponse findTuteeInfo(@PathVariable String id, HttpServletResponse response ) {
        StudentResponseDTO responseDTO = userRegisterService.findTuTeeId(id);
        log.debug("\n---- 과외정보조회 ----\n");
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(), id + "의 과외정보를 조회합니다.", responseDTO);
    }


    //정보 수정
    @PutMapping("/user/tutorclass/{id}")
    public String teacherUpdate(@PathVariable String id, @RequestBody TeacherUpdateDTO updateDTO) {
        return userRegisterService.teacherUpdate(id, updateDTO);
    }

    //정보 수정
    @PutMapping("/user/tuteeclass/{id}")
    public String studentUpdate(@PathVariable String id, @RequestBody StudentUpdateDTO updateDTO) {
        return userRegisterService.studentUpdate(id, updateDTO);
    }

    //정보 수정
/*    @PutMapping("/user/mypage")
    public Long update(@RequestBody UserResponseDTO responseDTO){
        return userRegisterService.update(id, responseDTO);
    }*/


    /**
     * 사용자 로그인
     */
    @PostMapping("/login")
    public NonValueNotExistDataResultResponse UserLogin(@RequestBody @Valid LoginDTO loginDTO, HttpServletResponse response) {
        log.debug("\n---- 로그인 ----\n");
        String str = userRegisterService.userLogin(loginDTO);
        if (!str.equals(" ")) {
            response.setHeader(AUTHORIZATION, BEARER + " " + jwtController.makeJwtToken(loginDTO.getId(), str));
            return new NonValueNotExistDataResultResponse(true, StatusCode.OK.getCode(),
                    "ID " + loginDTO.getId() + "(으)로 로그인되었습니다.");
        }
        return new NonValueNotExistDataResultResponse(false, StatusCode.CONFLICT.getCode(),
                "일치하는 계정 정보가 존재하지 않습니다.");
    }

    /**
     * 사용자 로그아웃
     */
    @GetMapping("/logout")
    public NotExistDataResultResponse UserLogout(HttpServletResponse response) {
        response.setHeader(AUTHORIZATION, BEARER + " ");
        return new NotExistDataResultResponse(StatusCode.OK.getCode(), "로그아웃되었습니다.");
    }

    /**아이디 찾기**/
    @PostMapping("/user/findid/email") //method로 바꾸고 싶은데 왜 안되지,,
    public NotExistDataResultResponse FindId(@RequestBody FindIdDTO findIdDTO){
        String method = "b";
        String id = userRegisterService.findId(findIdDTO, method);
        if (!id.equals(" ")) {
            return new NotExistDataResultResponse(StatusCode.OK.getCode(), "id는"+id+"입니다.");
        }
        return new NotExistDataResultResponse(StatusCode.CONFLICT.getCode(),
                "일치하는 계정 정보가 존재하지 않습니다.");
    }



}


