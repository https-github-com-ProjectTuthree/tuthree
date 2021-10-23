package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.tuthree.ApiController.EmbeddedResponse.*;
import project.tuthree.controller.JwtController;
import project.tuthree.dto.EmbeddedDTO;
import project.tuthree.dto.EmbeddedDTO.LoginReturnDTO;
import project.tuthree.dto.user.AdminDTO;
import project.tuthree.dto.user.UserListDTO;
import project.tuthree.repository.AdminRepository;
import project.tuthree.service.AdminService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminApiController {
    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer";

    private final AdminService adminService;
    private final JwtController jwtController;
    private final AdminRepository adminRepository;

    @PostMapping("/admin/in")
    public Object adminLogin(@RequestBody @Valid AdminDTO adminDTO, HttpServletResponse response) {

        String grade = adminRepository.findByIdPwd(adminDTO.getId(), adminDTO.getPwd());
        if(!grade.equals(" ")){
            response.setHeader(AUTHORIZATION,  BEARER+ " " + jwtController.makeJwtToken(adminDTO.getId(), grade));
            log.info("login==================");
            return new ExistDataSuccessResponse(StatusCode.OK.getCode(),
                    "ID " + adminDTO.getId() + "(으)로 로그인되었습니다.", new LoginReturnDTO(adminDTO.getId(), grade));
        }
        log.info("not found=======================");
        return new NotExistBadDataResultResponse(StatusCode.CONFLICT.getCode(),
                "일치하는 계정 정보가 존재하지 않습니다.");
    }

    @GetMapping("/admin/out")
    public NotExistDataResultResponse UserLogout(HttpServletResponse response) {
        response.setHeader(AUTHORIZATION, BEARER + " ");
        return new NotExistDataResultResponse(StatusCode.OK.getCode(), "로그아웃되었습니다.");
    }

}
