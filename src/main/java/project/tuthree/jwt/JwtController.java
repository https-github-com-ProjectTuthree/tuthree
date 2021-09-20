package project.tuthree.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.util.Date;

public class JwtController {

    public String makeJwtToken() {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("fresh")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis()))
                .claim("id", "아이디")
                .claim("email", "project.tuthree@gmail.com")
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
    }

//    public static void main(String[] args) {
//        JwtController jw = new JwtController();
//        String s = jw.makeJwtToken();
//        System.out.println("s = " + s);
//    }
}
