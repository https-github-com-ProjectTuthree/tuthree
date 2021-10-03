package project.tuthree.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
public class UserResponseDTO {
    private String id;
    private String name;
    private String email;
    private String tel;
    private Sex sex;
    private int birth;
    private String post;
    private Status notification;


    public UserResponseDTO(User entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.tel = entity.getTel();
        this.sex = entity.getSex();
        this.birth = entity.getBirth();
        this.post = entity.getPost();
        this.notification = entity.getNotification();
    }
    public UserResponseDTO(Teacher entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.tel = entity.getTel();
        this.sex = entity.getSex();
        this.birth = entity.getBirth();
        this.post = entity.getPost();
        this.notification = entity.getNotification();
    }
    public UserResponseDTO(Student entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.tel = entity.getTel();
        this.sex = entity.getSex();
        this.birth = entity.getBirth();
        this.post = entity.getPost();
        this.notification = entity.getNotification();
    }
}
