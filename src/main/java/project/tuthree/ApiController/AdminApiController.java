package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.tuthree.ApiController.EmbeddedResponse.NonValueNotExistDataResultResponse;
import project.tuthree.ApiController.EmbeddedResponse.*;
import project.tuthree.controller.JwtController;
import project.tuthree.domain.user.*;
import project.tuthree.dto.user.*;
import project.tuthree.repository.AdminRepository;
import project.tuthree.service.AdminService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static project.tuthree.domain.user.QUser.user;

@Controller
@RequiredArgsConstructor
public class AdminApiController {
    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer";

    private final AdminService adminService;
    private final JwtController jwtController;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    @PostMapping("/admin/in")
    public NonValueNotExistDataResultResponse adminLogin(@RequestBody @Valid AdminDTO adminDTO, HttpServletResponse response) {
        String grade = adminService.adminLogin(adminDTO);
        if(!grade.equals(" ")){
            response.setHeader(AUTHORIZATION,  BEARER+ " " + jwtController.makeJwtToken(adminDTO.getId(), grade));
            return new NonValueNotExistDataResultResponse(true, StatusCode.OK.getCode(),
                    "ID " + adminDTO.getId() + "(으)로 로그인되었습니다.");
        }
        return new NonValueNotExistDataResultResponse(false, StatusCode.CONFLICT.getCode(),
                "일치하는 계정 정보가 존재하지 않습니다.");
    }

    @GetMapping("/admin/out")
    public NotExistDataResultResponse UserLogout(HttpServletResponse response) {
        response.setHeader(AUTHORIZATION, BEARER + " ");
        return new NotExistDataResultResponse(StatusCode.OK.getCode(), "로그아웃되었습니다.");
    }

    /**회원목록 조회**/
    @GetMapping("/admin/userlist")
    public EmbeddedResponse.ExistListDataSuccessResponse UserList (@RequestParam("grade") String grade, @RequestParam(value = "userId", required = false) String userId, @PageableDefault(size= 10, sort="createDate") Pageable pageRequest) {

        Page<UserListDTO> userPageList = adminService.userList(grade, pageRequest, userId);

        return new EmbeddedResponse.ExistListDataSuccessResponse(StatusCode.OK.getCode(),
                "회원 목록이 조회되었습니다.", userPageList.getTotalElements() , userPageList);

    }


    /**회원조회**/
    @GetMapping("/user/{grade}/{user_id}")
    public ExistDataSuccessResponse viewUser(@PathVariable("grade") String grade, @PathVariable("user_id")String userId) throws IOException {
        if (Objects.equals(grade, "parent")) {
            UserDTO userDTO = adminService.viewParent(userId);
            return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                    userId + " 를 조회했습니다.", userDTO);
        } else if (Objects.equals(grade, "teacher")) {
            TeacherDTO teacherDTO = adminService.viewTeacher(userId);
            return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                    userId + " 를 조회했습니다.", teacherDTO);
        } else if (Objects.equals(grade, "student")) {
            StudentAllDTO studentDTO = adminService.viewStudent(userId);
            return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                    userId + " 를 조회했습니다.", studentDTO);
        } else {
            return new ExistDataSuccessResponse(StatusCode.CONFLICT.getCode(),
                    userId + " 가 없습니다.", null);
        }
    }

}
