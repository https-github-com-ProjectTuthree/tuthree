package project.tuthree.dto.post;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostExamDTO {

    @NotNull
    private Long prob;

    @NotNull
    private String dueDate;

    @NotNull
    private List<ProblemDTO> problem;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ProblemDTO {

        @NotNull
        private Long question;
        private String type; //num, str
        @NotNull
        private String ans;
        private boolean auto; //yes : 자동 채점, no : 자동 채점 안함
    }
}
