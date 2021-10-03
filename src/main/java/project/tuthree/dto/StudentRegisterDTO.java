package project.tuthree.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@NoArgsConstructor
public class StudentRegisterDTO {


    @Builder
    public StudentRegisterDTO(String id, String pwd, String name, String email, String tel, Sex sex, int birth,
                   String post, Grade grade, Status notification, String region, Status registration, String subject, int cost, School school, String detail) {
        //super(id, pwd, name, email, tel, sex, birth, post, grade);
        Assert.notNull(id, "id must not be blank");
        Assert.notNull(pwd, "pwd must not be blank");
        Assert.notNull(name, "name must not be blank");
        Assert.notNull(tel, "tel must not be blank");
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.sex = sex;
        this.birth = birth;
        this.post = post;
        this.notification = Status.OPEN;
        this.grade = grade.STUDENT;
        this.create_date = new Date();
        this.region = region;
        this.registration = registration;
        this.subject = subject;
        this.cost = cost;
        this.school = school;
        this.detail = detail;
    }

    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 6, max = 12, message = "아이디는 6~12자 이어야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String pwd;

    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 1, max = 6)
    private String name;

    @Email
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "전화번호를 입력해주세요")
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    private String tel;

    private Sex sex;
    private int birth;
    private String post;

    @ColumnDefault("OPEN")
    private Status notification;

    @ColumnDefault("STUDENT")
    private Grade grade;

    private Date create_date;
    private String region; ///json
    private Status registration;
    private String subject;
    private int cost;
    private School school;
    private String detail;

    public Student toEntity() {
        //super.toEntity();
        return Student.builder()
                .id(id)
                .pwd(new BCryptPasswordEncoder().encode(pwd))
                .name(name)
                .email(email)
                .tel(tel)
                .sex(sex)
                .birth(birth)
                .post(post)
                .grade(grade)
                .create_date(create_date)
                .region(region)
                .registration(registration)
                .subject(subject)
                .cost(cost)
                .school(school)
                .detail(detail)
                .build();
    }

}
