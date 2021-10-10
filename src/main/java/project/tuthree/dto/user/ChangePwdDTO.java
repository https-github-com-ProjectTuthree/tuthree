package project.tuthree.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ChangePwdDTO {

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String pwd;
}
