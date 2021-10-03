package project.tuthree.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.user.Student;
import project.tuthree.domain.user.Teacher;
import project.tuthree.domain.user.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "아이디를 입력해주세요")
    private String id;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String pwd;

    public LoginDTO(User entity){
        this.id = entity.getId();
        this.pwd = entity.getPwd();
    }

    public LoginDTO(Student entity){
        this.id = entity.getId();
        this.pwd = entity.getPwd();
    }

    public LoginDTO(Teacher entity){
        this.id = entity.getId();
        this.pwd = entity.getPwd();
    }
}
