package project.tuthree.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Child;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.Sex;
import project.tuthree.domain.user.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserDTO {
    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 6, max = 12, message = "아이디는 6~12자 이어야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String pwd;

    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 1, max = 6)
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String tel;

    private Sex sex;
    private int birth;
    private String post;
    private Grade grade;
    private Date createDate;
    private List<Child> child;
    private byte[] file;
    private Status userDel;

    @Builder
    public UserDTO(String id, String pwd, String name, String email, String tel, Sex sex, Integer birth, String post, Grade grade, Date createDate){
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.sex = sex;
        this.birth = birth;
        this.post = post;
        this.grade = grade.PARENT;
        this.createDate = new Date();
    }
    public UserDTO(User entity, List<Child> child, byte[] file){
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.tel = entity.getTel();
        this.sex = entity.getSex();
        this.birth = entity.getBirth();
        this.userDel = entity.getUserDel();
        this.child = child;
        this.file = file;
    }


}
