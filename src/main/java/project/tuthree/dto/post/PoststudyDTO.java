package project.tuthree.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.tuthree.domain.room.StudyRoom;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PoststudyDTO {


    private Long id;

    private StudyRoom studyRoomId;

    private Date date;
    private Long number;

    private String start;
    private String end;

    private String detail;
}
