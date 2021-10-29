package project.tuthree.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Sex;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateDTO {
    private String id;
    private String email;
    private String tel;
    private Integer birth;
    private String post;
    private MultipartFile file;
    private Status notification;

    @Builder
    public UserUpdateDTO(String id, String email, String tel,Integer birth, String post, MultipartFile file, Status notification){
        this.id = id;
        this.email = email;
        this.tel = tel;
        this.birth = birth;
        this.post = post;
        this.file = file;
        this.notification = notification;
    }
    public void updatePost(String post) {
        this.post = post;
    }
}
