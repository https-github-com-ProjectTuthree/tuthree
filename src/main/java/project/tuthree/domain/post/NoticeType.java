package project.tuthree.domain.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NoticeType {
    /** 공지사항 분류 */

    NORMAL("일반"),
    IMPORTANT("중요");

    private String korType;

    NoticeType(String korType) {
        this.korType = korType;
    }
}
