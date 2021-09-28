package project.tuthree.testlogic;

import com.fasterxml.jackson.core.JsonParseException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import project.tuthree.controller.JwtController;

import java.util.Map;

class JwtControllerTest {

    @Test
    public void 토큰생성_검증() throws JsonParseException {
        JwtController jwtController = new JwtController();
        String token = jwtController.makeJwtToken();
        Map<String, Object> map = jwtController.decryptValidJwtToken(token);

        Assertions.assertThat("admin1").isEqualTo(map.get("userId"));
    }
}