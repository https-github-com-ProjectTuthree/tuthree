package project.tuthree.dto.user;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
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
    private Integer birth;
    private Status notification;
    private byte[] post;


    public UserResponseDTO(User entity, byte[] post){
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.tel = entity.getTel();
        this.sex = entity.getSex();
        this.birth = entity.getBirth();
        this.notification = entity.getNotification();
        this.post = post;
    }
    public UserResponseDTO(Teacher entity, byte[] post){
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.tel = entity.getTel();
        this.sex = entity.getSex();
        this.birth = entity.getBirth();
        this.notification = entity.getNotification();
        this.post = post;
    }
    public UserResponseDTO(Student entity, byte[] post){
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.tel = entity.getTel();
        this.sex = entity.getSex();
        this.birth = entity.getBirth();
        this.notification = entity.getNotification();
        this.post = post;
    }

}
