package project.tuthree.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Sex;

@Getter
@NoArgsConstructor
public class UserUpdateDTO {
    private String id;
    private String email;
    private String tel;
    private Integer birth;
    private String post;
    private Status notification;

    @Builder
    public UserUpdateDTO(String id, String email, String tel,Integer birth, String post, Status notification){
        this.id = id;
        this.email = email;
        this.tel = tel;
        this.birth = birth;
        this.post = post;
        this.notification = notification;
    }
}
