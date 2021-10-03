package project.tuthree.dto.file;

import lombok.Builder;
import lombok.Getter;
import project.tuthree.domain.post.PostTestPaper;
import project.tuthree.domain.room.StudyRoom;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
public class UserfileDTO {

    private Long id;

    private StudyRoom studyRoomId;

    private PostTestPaper testpaperId;
    @NotNull
    private String saveTitle;
    @NotNull
    private String realTitle;

    @Builder
    public UserfileDTO(Long id, StudyRoom studyRoomId, PostTestPaper testpaperId, String saveTitle, String realTitle) {
        this.id = id;
        this.studyRoomId = studyRoomId;
        this.testpaperId = testpaperId;
        this.saveTitle = saveTitle;
        this.realTitle = realTitle;
    }
}
