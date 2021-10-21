package project.tuthree.ApiController;

import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.tuthree.controller.JwtController;
import project.tuthree.dto.EmbeddedDTO.LoginReturnDTO;
import project.tuthree.dto.user.*;
import project.tuthree.repository.UserFileRepository;
import project.tuthree.service.PostFindService;
import project.tuthree.service.UserRegisterService;
import project.tuthree.ApiController.EmbeddedResponse.ExistDataSuccessResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

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
        //userFileRepository.jsonPParse(registerDTO);
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
        //userFileRepository.jsonSParse(registerDTO);
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
        //userFileRepository.jsonTParse(registerDTO);
        return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), id + "님 안녕하세요.");
    }

    /**기본정보조회**/
    @GetMapping("/user/mypage")
    public UserResponseDTO findUserInfo(@RequestHeader(value="Authorization") String AUTHORIZATION){
        String id = CheckUserI(AUTHORIZATION).getId();
        String grade = CheckUserI(AUTHORIZATION).getGrade();
        return userRegisterService.findByInfo(id, grade);
    }

    /**튜터 과외정보조회**/
    @GetMapping("/user/tutorclass") //토큰값으로 조회하게 바꾸기
    public ExistDataSuccessResponse findTutorInfo(@RequestHeader(value="Authorization") String AUTHORIZATION) {
        String id = CheckUserI(AUTHORIZATION).getId();
        TeacherResponseDTO responseDTO = userRegisterService.findTutorId(id);
        log.debug("\n---- 과외정보조회 ----\n");
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(), id + "의 과외정보를 조회합니다.", responseDTO);

    }

    /**튜티 과외정보조회**/
    @GetMapping("/user/tuteeclass")
    public ExistDataSuccessResponse findTuteeInfo(@RequestHeader(value="Authorization") String AUTHORIZATION) {
        String id = CheckUserI(AUTHORIZATION).getId();
        StudentResponseDTO responseDTO = userRegisterService.findTuTeeId(id);
        log.debug("\n---- 과외정보조회 ----\n");
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(), id + "의 과외정보를 조회합니다.", responseDTO);
    }


    /**튜터 정보 수정**/
    @PutMapping("/user/tutorclass")
    public String teacherUpdate(@RequestHeader(value="Authorization") String AUTHORIZATION, @RequestBody TeacherUpdateDTO updateDTO) {
        String id = CheckUserI(AUTHORIZATION).getId();
        return userRegisterService.teacherUpdate(id, updateDTO);
    }

    /**튜티 정보 수정**/
    @PutMapping("/user/tuteeclass")
    public String studentUpdate(@RequestHeader(value="Authorization") String AUTHORIZATION, @RequestBody StudentUpdateDTO updateDTO) {
        String id = CheckUserI(AUTHORIZATION).getId();
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
    public Object UserLogin(@RequestBody @Valid LoginDTO loginDTO, HttpServletResponse response) {
        log.debug("\n---- 로그인 ----\n");
        String str = userRegisterService.userLogin(loginDTO);
        if (!str.equals(" ")) {
            response.setHeader(AUTHORIZATION, BEARER + " " + jwtController.makeJwtToken(loginDTO.getId(), str));
            return new ExistDataSuccessResponse(StatusCode.OK.getCode(), "로그인되었습니다.",  new LoginReturnDTO(loginDTO.getId(), str));
        }
        return new NotExistDataResultResponse(StatusCode.CONFLICT.getCode(),"일치하는 계정 정보가 존재하지 않습니다.");
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
    @PostMapping("/user/findid/{method}")
    public NotExistDataResultResponse FindId(@RequestBody FindIdDTO findIdDTO, @PathVariable("method") String method){
        String id = userRegisterService.findId(findIdDTO, method);
        if (!id.equals(" ")) {
            return new NotExistDataResultResponse(StatusCode.OK.getCode(), "id는"+id+"입니다.");
        }
        return new NotExistDataResultResponse(StatusCode.CONFLICT.getCode(),
                "일치하는 계정 정보가 존재하지 않습니다.");
    }

    /**비밀번호 찾기**/
    @PostMapping("/user/findpwd/{method}") //여기서 그다음 어떻게 넘겨주지.. 토큰을 줘야되나
    public NotExistDataResultResponse FindPwd(@RequestBody FindIdDTO findIdDTO, @PathVariable("method") String method, HttpServletResponse response){
        String str;
        str = userRegisterService.findPwd(findIdDTO, method);
        if(!str.equals(" ")){
            response.addHeader("Id", findIdDTO.getId());
            response.addHeader("Grade", str);
            return new NotExistDataResultResponse(StatusCode.OK.getCode(),
                    findIdDTO.getId()+"의 비밀번호를 변경합니다.");
        }
        return new NotExistDataResultResponse(StatusCode.CONFLICT.getCode(),
                "일치하는 계정 정보가 존재하지 않습니다.");
    }

    /**비밀번호 변경**/
    @PutMapping("/user/changepwd") //권한을 일시적으로 줘도 되나,,,
    public NotExistDataResultResponse ChangePwd(@RequestBody ChangePwdDTO pwdDTO, @RequestHeader(value="Id") String Id, @RequestHeader(value="Grade") String grade){
        //String userId = CheckUserI(request).getId();
        //String grade = CheckUserI(request).getGrade();
        userRegisterService.changePwd(pwdDTO, Id, grade);
        return new NotExistDataResultResponse(StatusCode.OK.getCode(),"비밀번호가 변경되었습니다.");

    }

    /**자녀추가**/
    @PostMapping("/user/parent")
    public NotExistDataResultResponse PlusChild(@RequestParam("parentId") String parentId, @RequestParam("studentId") String studentId, ChildDTO childDTO){
        String id = userRegisterService.plusChild(parentId, childDTO);
        return new NotExistDataResultResponse(StatusCode.OK.getCode(),id+"로 자녀를 신청하였습니다.");
    }

    /**자녀수락**/
    @PostMapping("/user/myclass")
    public NotExistDataResultResponse AcceptChild(@RequestParam("parentId") String parentId, @RequestParam("studentId") String studentId){
        String id = userRegisterService.acceptChild(parentId, studentId);
        return new NotExistDataResultResponse(StatusCode.OK.getCode(), id+"가 부모로 등록되었습니다.");
    }

    /**요청보기**/
    @GetMapping("/user/myclass")
    public ExistDataSuccessResponse AcceptChild(@RequestHeader(value="Authorization") String AUTHORIZATION){
        String studentId = CheckUserI(AUTHORIZATION).getId();
        ChildDTO childDTO = userRegisterService.checkChild(studentId);
        return new ExistDataSuccessResponse(StatusCode.OK.getCode(), "자녀 요청의 정보가 조회되었습니다.", childDTO);

    }



    /**헤더에서 사용자 정보 확인**/
    public CheckUser CheckUserI(String AUTHORIZATION){
        String[] requestToken = AUTHORIZATION.split(" ");
        if(!requestToken[0].equals(BEARER)){
            throw new MalformedJwtException("잘못된 토큰정보입니다.");
        }
        Map<String, Object> map = jwtController.decryptValidJwtToken(requestToken[1]);
        log.info(jwtController.decryptValidJwtToken(requestToken[1]).toString());

        String userId = String.valueOf(map.get("userId"));
        String grade = String.valueOf(map.get("Grade"));
        CheckUser checkUser = new CheckUser();
        checkUser.setId(userId);
        checkUser.setGrade(grade);

        return checkUser;
    }


}


