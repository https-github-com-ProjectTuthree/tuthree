package project.tuthree.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class PostExamDTO {

    private Long prob;

    private List<ProblemDTO> problem;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ProblemDTO {

        private Long question;
        private String type; //num, str
        private String ans;
        private boolean auto; //yes : 자동 채점, no : 자동 채점 안함

        public ProblemDTO(Long question, String type, String ans, boolean auto) {
            this.question = question;
            this.type = type;
            this.ans = ans;
            this.auto = auto;
        }
    }

    public PostExamDTO(Long prob, List<ProblemDTO> problem) {
        this.prob = prob;
        this.problem = problem;
    }
}
