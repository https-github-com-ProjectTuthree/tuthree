package project.tuthree.dto.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostAnswerDTO {

    private Long prob;
    private List<AnswerDTO> answer;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class AnswerDTO {

        private Long question;
        private answerType score;
        private String teacherAnswer;
        private String studentAnswer;

    }

    public enum answerType {
        RIGHT, WRONG, NONE
    }

}
