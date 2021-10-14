package project.tuthree.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class PostExamDTO {

    private Long prob;

    private List<ProblemDTO> problem;

    public static class ProblemDTO {
        private Long question;
        private String type; //num, str
        private String ans;
        private boolean auto; //yes : 자동 채점, no : 자동 채점 안함
    }

    @Builder
    public PostExamDTO(Long prob, List<ProblemDTO> problem) {
        this.prob = prob;
        this.problem = problem;
    }
}
