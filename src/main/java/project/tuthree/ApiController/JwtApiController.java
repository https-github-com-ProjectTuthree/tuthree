package project.tuthree.ApiController;


import com.fasterxml.jackson.core.JsonParseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.junit.Assert;
import org.springframework.stereotype.Component;
import project.tuthree.controller.JwtController;
import project.tuthree.repository.AdminRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtApiController {
    /** api 테스트를 위해 token 로직은 잠시 종료 해 둠 */

//    private final JwtController jwtController;
//    private final AdminRepository adminRepository;
//
//    private final HttpServletResponse response;
//    private final HttpServletRequest request;
//
//    /** 종료 시 값 실어서 보내는 로직으로 만들기 */
//    @After("execution(* project.tuthree.ApiController..*(..))")
//    public void createRefreshJwtToken() {
//        String Token = jwtController.makeJwtToken();
//        String BarerToken = "Barer " + Token;
//        response.setHeader("Autorization", BarerToken);
//    }
//
//    @Before("execution(* project.tuthree.ApiController..*(..))")
//    public void checkAdminValidToken() throws JsonParseException {
//        /** 로직 개선하기!! */
//        String[] token = request.getHeader("token").split(" ");
//
//        Map<String, Object> map = jwtController.decryptValidJwtToken(token[1]);
//        log.info("\nmap : " + map.toString());
//        Assert.assertEquals("admin", map.get("Grade"));
//        adminRepository.findById(String.valueOf(map.get("userId")));
//    }
}
