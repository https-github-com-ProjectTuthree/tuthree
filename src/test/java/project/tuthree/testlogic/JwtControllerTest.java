package project.tuthree.testlogic;

import com.fasterxml.jackson.core.JsonParseException;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import project.tuthree.controller.JwtController;
import project.tuthree.domain.user.Grade;

import java.util.Map;

class JwtControllerTest {

    @Test
    public void 토큰생성_검증() throws JsonParseException {
        String userId = "admin1";
        String grade = Grade.ADMIN.getStrType();
        JwtController jwtController = new JwtController();
        String token = jwtController.makeJwtToken(userId, grade);
        Map<String, Object> map = jwtController.decryptValidJwtToken(token);
        String getId = String.valueOf(map.get("userId"));
        System.out.println("getId = " + getId);
        Grade grade1 = (Grade) map.get("grade");
        System.out.println("grade1 = " + grade1);
        System.out.println("map.toString() = " + map.toString());
        Assert.assertEquals("admin1", map.get("userId"));
        Assert.assertEquals("ADMIN", map.get("Grade"));

        //asseteuqals 문자열 비교, assertthat.isequalto 자료형도 비교...??
    }
}