package project.tuthree.domain.user;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.tuthree.domain.Status;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserEntityRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @After
    public void cleanup() {
        userRepository.deleteAll();
        teacherRepository.deleteAll();}


    @Test
    public void parentregister(){
        String id="user123";
        String pwd="asdf1234";
        String name="user";
        String email="user@user.com";
        String tel="010-1234-1234";
        Sex sex=Sex.FEMALE;
        Integer birth=1999;
        String post="post/user";
        Grade grade = Grade.PARENT;


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


    }

    @Test
    public void teacherregister(){
        String id="teacher123";
        String pwd="asdf1234";
        String name="teacher";
        String email="teacher@user.com";
        String tel="010-1234-1234";
        Sex sex=Sex.FEMALE;
        int birth=1999;
        String post="post/user";
        Grade grade=Grade.TEACHER;
        String region="경기도 수원시";
        Status registration = Status.OPEN;
        String subject = "수학";
        String cost = "20000";
        String school = "경기대";
        SchoolStatus status = SchoolStatus.ABSENCE_OF_SCHOOL;
        String major = "컴공";
        String certification = "certi";
        String detail = "gd";



        teacherRepository.save(Teacher.builder()
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
                .registration(registration)
                .cost(cost)
                .school(school)
                .status(status)
                .major(major)
                .certification(certification)
                .detail(detail)
                .build());

        List<Teacher> teacherList = teacherRepository.findAll();

        Teacher teacher = teacherList.get(0);
        assertThat(teacher.getId()).isEqualTo(id);
        assertThat(teacher.getPwd()).isEqualTo(pwd);
        assertThat(teacher.getName()).isEqualTo(name);
        assertThat(teacher.getEmail()).isEqualTo(email);
        assertThat(teacher.getTel()).isEqualTo(tel);
        assertThat(teacher.getSex()).isEqualTo(sex);
        assertThat(teacher.getBirth()).isEqualTo(birth);
        assertThat(teacher.getPost()).isEqualTo(post);
        assertThat(teacher.getGrade()).isEqualTo(grade);
        assertThat(teacher.getRegistration()).isEqualTo(registration);
        assertThat(teacher.getCost()).isEqualTo(cost);
        assertThat(teacher.getSchool()).isEqualTo(school);
        assertThat(teacher.getStatus()).isEqualTo(status);
        assertThat(teacher.getMajor()).isEqualTo(major);
        assertThat(teacher.getCertification()).isEqualTo(certification);
        assertThat(teacher.getDetail()).isEqualTo(detail);


    }
}


