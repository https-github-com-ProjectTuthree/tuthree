package project.tuthree.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.Sex;
import project.tuthree.domain.user.Teacher;
import project.tuthree.domain.user.User;

import javax.persistence.EntityManager;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDTO {
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

    @ColumnDefault("Parent")
    private Grade grade = Grade.PARENT;

    private Date create_date = new Date();


   @Builder
    public UserRegisterDTO(String id, String pwd, String name, String email, String tel, Sex sex, Integer birth, String post, MultipartFile file, Status notification, Grade grade, Date create_date){
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
       this.notification = Status.OPEN;
       this.grade = grade.PARENT;
       this.create_date = new Date();
    }

    public User toEntity(){
        return User.builder()
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
                .create_date(create_date)
                .build();
    }

    public void updatePost(String post) {
       this.post = post;
    }

}
