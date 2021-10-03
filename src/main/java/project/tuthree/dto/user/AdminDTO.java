package project.tuthree.dto.user;

import lombok.*;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminDTO {

    @NotNull
    private String id;

    @NotNull
    private String pwd;

    @Builder
    public AdminDTO(String id, String pwd) {
        Assert.notNull(id, "id must not to be null");
        Assert.notNull(pwd, "pwd must not to be null");
        this.id = id;
        this.pwd = pwd;
    }
}
