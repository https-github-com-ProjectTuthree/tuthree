package project.tuthree.dto;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.Sex;
import project.tuthree.domain.user.User;
import project.tuthree.domain.user.UserRepository;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRegisterControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() throws Exception{
        userRepository.deleteAll();
    }

   @Test
    public void 회원가입() throws Exception {
       //given
       String id = "user123";
       String pwd = "asdf1234";
       String name = "user";
       String email = "user@user.com";
       String tel = "010-1234-1234";
       Sex sex = Sex.FEMALE;
       int birth = 1999;
       String post = "post/user";
       UserRegisterDTO registerDto = UserRegisterDTO.builder()
               .id(id)
               .pwd(pwd)
               .name(name)
               .email(email)
               .tel(tel)
               .sex(sex)
               .birth(birth)
               .post(post)
               .build();

        String url = "http://localhost:" + port + "/register/parent";

        //when
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, registerDto, String.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan("0L");
        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getId()).isEqualTo(id);
        //assertThat(all.get(0).getPwd()).isEqualTo(pwd);
       assertThat(all.get(0).getName()).isEqualTo(name);
       assertThat(all.get(0).getEmail()).isEqualTo(email);
       assertThat(all.get(0).getSex()).isEqualTo(sex);
       assertThat(all.get(0).getBirth()).isEqualTo(birth);
       assertThat(all.get(0).getPost()).isEqualTo(post);
   }

}