package project.tuthree.controller;

import com.fasterxml.jackson.core.JsonParseException;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import project.tuthree.domain.user.Grade;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Getter
@Slf4j
@Controller
@RequiredArgsConstructor
public class JwtController {
    /**
     * 토큰 생성, 토큰 복호화 하는 로직
     * 토큰 형식
     * user id
     * user grade : 역할에 따라 접근 권한이 있는지 확인한다.
     * 토큰 만료 시간이랑 현재 시간 비교, 만료 시간이 지났으면 세션 없애기
     *
     */

    /**
     * 에러처리 하기
     */

    private final String SECRET_KEY = "qweasdzxcqweasdzxc";
    //15 * 60 * 1000L // 15 * 60 * 4 * 1000L
    private final Long EXPIRATION_TIME = 15 * 60 * 4 * 1000L;



    public String makeJwtToken(String userId, String strGrade) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("fresh_token")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRATION_TIME))
                .claim("userId", userId)
                .claim("Grade", strGrade)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        //access token
        //refresh token
    }

    public Map<String, Object> decryptValidJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static void main(String[] args) {
        String id = "admin1";
        String grade = Grade.ADMIN.getStrType();
        JwtController jwtController = new JwtController();
        String token = jwtController.makeJwtToken(id, grade);
        System.out.println("token = " + token);
        System.out.println("decrypt : " + jwtController.decryptValidJwtToken(token).toString());

        System.out.println("\n\ndate : " + new Date().getTime());
    }

}
