package project.tuthree.controller;

import com.fasterxml.jackson.core.JsonParseException;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import project.tuthree.configuration.Utils;
import project.tuthree.domain.user.Grade;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static project.tuthree.configuration.Utils.*;

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

    public String makeJwtToken(String userId, String strGrade) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("fresh_token")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(EXPIRATION_TIME).toMillis()))
                .claim(CLAIMUSERID, userId)
                .claim(CLAIMGRADE, strGrade)
                .signWith(SignatureAlgorithm.HS256, Utils.SECRET_KEY)
                .compact();
    }

    public Map<String, Object> decryptValidJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(Utils.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public String parseValueFromJwtToken(String token, String val) {
        String[] requestToken = token.split(" ");
        if(!requestToken[0].equals(BEARER)){
            throw new MalformedJwtException("잘못된 토큰정보입니다.");
        }

        Map<String, Object> map = decryptValidJwtToken(requestToken[1]);
        if(val.equals(CLAIMUSERID)){
            return String.valueOf(map.get(CLAIMUSERID));
        } else if (val.equals(CLAIMGRADE)) {
            return String.valueOf(map.get(CLAIMGRADE));
        }
        return null;
    }

}
