package project.tuthree.controller;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Getter
public class JwtController {

    private final String SECRET_KEY = "qweasdzxcqweasdzxc";
    private final Long EXPIRATION_TIME = 15 * 60 * 1000L;

    public String makeJwtToken() {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("fresh_token")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRATION_TIME))
                .claim("userId", "유저 아이디")
                .claim("그외 정보", "그외 정보")
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Map<String, Object> undoJwtToken(String token) {

        Map<String, Object> map = null;
        map = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        System.out.println("map = " + map.toString());

        return map;
    }

}
