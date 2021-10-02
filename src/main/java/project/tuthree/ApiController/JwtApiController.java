package project.tuthree.ApiController;


import com.fasterxml.jackson.core.JsonParseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.junit.Assert;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.tuthree.controller.JwtController;
import project.tuthree.repository.AdminRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtApiController {
    /** api 테스트를 위해 token 로직은 잠시 종료 해 둠 */

    private final JwtController jwtController;
    private final AdminRepository adminRepository;

    private final HttpServletResponse response;
    private final HttpServletRequest request;

    /** 종료 시 값 실어서 보내는 로직으로 만들기 */
    @After("execution(* PostAdminApiController.*(..))")
    public void createRefreshJwtToken() {
        String Token = jwtController.makeJwtToken();
        String BarerToken = "Barer " + Token;
        response.setHeader("Autorization", BarerToken);
    }

    @Before("execution(* PostAdminApiController.*(..))")
    public void checkAdminValidToken() throws JsonParseException {
        /** 로직 개선하기!! */
        String[] token = request.getHeader("Authorization").split(" ");

        /** 관리자 아이디 받아서 비교하기 */

        //관리자 로그인, 로그아웃
        //관리자 faq 조회, 수정, 삭제, 특정 글 조회 - 공지사항도 마찬가지  =>  faq에 전달된 아이디랑 비교해서 알아야 하나...

        Map<String, Object> map = jwtController.decryptValidJwtToken(token[1]);
        log.info("\nmap : " + map.toString());
        Assert.assertEquals("admin", map.get("Grade"));
        adminRepository.findById(String.valueOf(map.get("userId")));
    }

    @RestController
    class example {
        @GetMapping("/token")
        public Map<String, String> exampleToken() {
            String Token = jwtController.makeJwtToken();
            String BarerToken = "Barer " + Token;
            Map<String, String> token = new HashMap<>();
            token.put("Authorization", BarerToken);
            return token;
        }
    }
}
