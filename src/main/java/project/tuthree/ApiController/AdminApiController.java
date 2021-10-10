package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import project.tuthree.ApiController.EmbeddedResponse.NonValueNotExistDataResultResponse;
import project.tuthree.ApiController.EmbeddedResponse.*;
import project.tuthree.controller.JwtController;
import project.tuthree.domain.user.*;
import project.tuthree.dto.EmbeddedDTO;
import project.tuthree.dto.user.AdminDTO;
import project.tuthree.service.AdminService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

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

/*    @GetMapping("/admin/userlist/{page}")//조회방법 물어보기>page
    public ExistListDataSuccessResponse UserList(@PathVariable("page") long page){
        adminService.userList(page);

        return new ExistListDataSuccessResponse(StatusCode.OK.getCode(),
                page + "의 회원정보가 조회되었습니다.", );
    }*/
}
