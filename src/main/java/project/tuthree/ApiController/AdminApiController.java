package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.tuthree.ApiController.EmbeddedResponse.*;
import project.tuthree.controller.JwtController;
import project.tuthree.dto.user.AdminDTO;
import project.tuthree.dto.user.UserListDTO;
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

    @PostMapping("/admin/in")
    public Object adminLogin(@RequestBody @Valid AdminDTO adminDTO, HttpServletResponse response) {
        String grade = adminService.adminLogin(adminDTO);
        if(!grade.equals(" ")){
            response.setHeader(AUTHORIZATION,  BEARER+ " " + jwtController.makeJwtToken(adminDTO.getId(), grade));
            return new NotExistDataResultResponse(StatusCode.OK.getCode(),
                    "ID " + adminDTO.getId() + "(으)로 로그인되었습니다.");
        }
        return new NotExistBadDataResultResponse(StatusCode.CONFLICT.getCode(),
                "일치하는 계정 정보가 존재하지 않습니다.");
    }

    @GetMapping("/admin/out")
    public NotExistDataResultResponse UserLogout(HttpServletResponse response) {
        response.setHeader(AUTHORIZATION, BEARER + " ");
        return new NotExistDataResultResponse(StatusCode.OK.getCode(), "로그아웃되었습니다.");
    }

/*    @GetMapping("/admin/userlist")
    public EmbeddedResponse.ExistListDataSuccessResponse UserList (@PageableDefault(size=10, sort="createdate") Pageable pageRequest) {
        Page<UserListDTO> userPageList = adminService.parentList(pageRequest);
        return new EmbeddedResponse.ExistListDataSuccessResponse(StatusCode.OK.getCode(),
                "회원 목록이 조회되었습니다.", adminRepository.userHasRow() , userPageList);
    }*/

}
