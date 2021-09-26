package project.tuthree.testlogic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import project.tuthree.controller.JwtController;

import java.util.Map;

class JwtControllerTest {

    @Test
    public void 토큰생성_검증() {
        JwtController jwtController = new JwtController();
        String token = jwtController.makeJwtToken();
        Map<String, Object> map = jwtController.undoJwtToken(token);

        Assertions.assertThat("유저 아이디").isEqualTo(map.get("userId"));
    }
}