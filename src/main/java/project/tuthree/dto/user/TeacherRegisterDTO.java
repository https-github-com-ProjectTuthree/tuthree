package project.tuthree.dto.user;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeacherRegisterDTO {

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
    private Integer birth;
    private String post;
    private MultipartFile file;

    @ColumnDefault("OPEN")
    private Status notification = Status.OPEN;

    @ColumnDefault("TEACHER")
    private Grade grade = Grade.TEACHER;
    private Date createDate = new Date();
    private String region; ///json
    private List<String> regionL;

    @ColumnDefault("OPEN")
    private Status registration = Status.OPEN;
    private String subject;
    private List<String> subjectL;

    private String cost;
    private String school;
    private SchoolStatus status;
    private String major;
    private double star;


    private String certification;
    private MultipartFile authFile;

    @ColumnDefault("FALSE")
    private boolean certifyStatus;
    private String detail;

    @ColumnDefault("OPEN")
    private Status userDel = Status.OPEN;

    @Builder
    public TeacherRegisterDTO(String id, String pwd, String name, String email, String tel, Sex sex, Integer birth,
                              String post, MultipartFile file, Status notification, Grade grade, List<String> regionL, Status registration, List<String> subjectL,
                              String cost, String school, SchoolStatus status, String major, String certification, MultipartFile authFile, boolean certifyStatus, String detail, Status userDel) {

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
        this.file = file;
        this.notification = notification;
        this.grade = grade;
        this.createDate = new Date();
        this.region = jsonp(regionL);
        this.registration = registration;
        this.subject = jsonp(subjectL);
        this.cost = cost;
        this.school = school;
        this.status = status;
        this.major = major;
        this.star = 0.0;
        this.certification = certification;
        this.authFile = authFile;
        this.certifyStatus = false;
        this.detail = detail;
        this.userDel = userDel;
    }

/*    @Override
    public void setSubject(String subject){
        this.subject=jsonp(subjectL);
    }*/
    public String jsonp(List<String> subjectL)  {
        String subjectS = null;
        for(int i=0; i<subjectL.size(); i++){
            subjectS = subjectS+","+subjectL.get(i);
        }
        return subjectS;
    }

/*    public User toEntity() {
        return User.builder().id(this.id).pwd((new BCryptPasswordEncoder()).encode(this.pwd)).name(this.name).email(this.email).tel(this.tel).sex(this.sex).birth(this.birth).post(this.post).grade(this.grade).build();
    }*/
    public Teacher toEntity() {
        return Teacher.builder()
                .id(id)
                .pwd(new BCryptPasswordEncoder().encode(pwd))
                .name(name)
                .email(email)
                .tel(tel)
                .sex(sex)
                .birth(birth)
                .post(post)
                .notification(notification)
                .grade(grade)
                .createDate(createDate)
                .registration(registration)
                .cost(cost)
                .school(school)
                .status(status)
                .major(major)
                .star(star)
                .certification(certification)
                .certifyStatus(certifyStatus)
                .detail(detail)
                .userDel(userDel)
                .build();
    }

    public void updatePost(String post) {
        this.post = post;
    }

    public void updateAuthPost(String post) {
        this.certification = post;
    }

}
