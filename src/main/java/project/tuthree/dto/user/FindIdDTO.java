package project.tuthree.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class FindIdDTO {
    //@NotBlank(message = "id를 입력해주세요")
    private String id;
    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    //@NotBlank(message = "번호를 입력해주세요")
    private String tel;
    //@NotBlank(message = "이메일을 입력해주세요")
    private String email;

}
