package project.tuthree.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostAnswerDTO {

    private Long prob;
    private List<AnswerDTO> answer;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AnswerDTO {

        private Long question;
        private answerType score;

        public AnswerDTO(Long question, answerType score) {
            this.question = question;
            this.score = score;
        }
    }

    public enum answerType {
        RIGHT, WRONG, NONE
    }

    public PostAnswerDTO(Long prob, List<AnswerDTO> answer) {
        this.prob = prob;
        this.answer = answer;
    }
}
