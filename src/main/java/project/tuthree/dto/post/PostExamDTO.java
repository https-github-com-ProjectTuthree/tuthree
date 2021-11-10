package project.tuthree.dto.post;

import lombok.*;
import project.tuthree.ApiController.ValidationGroups;
import project.tuthree.ApiController.ValidationGroups.PostExamTeacherGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostExamDTO {

    @NotBlank(message = "총 문제 수 입력값 필요")
    private Long prob;

    private String dueDate;

    @NotNull(message = "답안지 입력값 필요")
    private List<ProblemDTO> problem;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ProblemDTO {

        @NotBlank(message = "문제 번호 입력값 필요")
        private Long question;
        @NotBlank(groups = PostExamTeacherGroup.class, message = "문제 형식 입력값 필요 : [num, str]")
        private String type; //num, str
        @NotBlank(groups = PostExamTeacherGroup.class, message = "문제 답 입력값 필요")
        private String ans;
        @NotBlank(groups = PostExamTeacherGroup.class, message = "문제 자동채점 여부 입력값 필요 : [yes, no]")
        private boolean auto; //yes : 자동 채점, no : 자동 채점 안함
    }
}
