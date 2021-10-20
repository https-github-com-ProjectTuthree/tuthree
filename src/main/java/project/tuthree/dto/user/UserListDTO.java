package project.tuthree.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@NoArgsConstructor
public class UserListDTO {
    private String id;
    private String name;
    private Date createDate;
    private String pwd;
    private String email;
    private String tel;
    private Sex sex;
    private int birth;
    private Grade grade;


    @Builder
    public UserListDTO(String id, String pwd, String name, String email, String tel, Sex sex, Integer birth, Grade grade, Date createDate){
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.sex = sex;
        this.birth = birth;
        this.grade = grade;
        this.createDate = createDate;

    }
   /* @Builder
    public UserListDTO(Teacher entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.create_date = entity.getCreate_date();
        this.grade = entity.getGrade();
        this.birth = entity.getBirth();
        this.email = entity.getEmail();
        this.pwd = entity.getPwd();
        this.email = entity.getEmail();
        this.tel = entity.getTel();
        this.sex = entity.getSex();
    }
    @Builder
    public UserListDTO(Student entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.create_date = entity.getCreate_date();
        this.grade = entity.getGrade();
        this.birth = entity.getBirth();
        this.email = entity.getEmail();
        this.pwd = entity.getPwd();
        this.email = entity.getEmail();
        this.tel = entity.getTel();
        this.sex = entity.getSex();
    }*/

}
