package project.tuthree.domain.user;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @After
    public void cleanup() { userRepository.deleteAll();}

    @Test
    public void parent_회원가입(){
        String id="user123";
        String pwd="asdf1234";
        String name="user";
        String email="user@user.com";
        String tel="010-1234-1234";
        Sex sex=Sex.FEMALE;
        int birth=1999;
        String post="post/user";
        Grade grade=Grade.PARENT;


        userRepository.save(User.builder()
                .id(id)
                .pwd(pwd)
                .name(name)
                .email(email)
                .tel(tel)
                .sex(sex)
                .birth(birth)
                .post(post)
                .grade(grade)
                .create_date(new Date())
                .build());

        List<User> userList = userRepository.findAll();

        User user = userList.get(0);
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getPwd()).isEqualTo(pwd);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getTel()).isEqualTo(tel);
        assertThat(user.getSex()).isEqualTo(sex);
        assertThat(user.getBirth()).isEqualTo(birth);
        assertThat(user.getPost()).isEqualTo(post);
        assertThat(user.getGrade()).isEqualTo(grade);


    }
}


