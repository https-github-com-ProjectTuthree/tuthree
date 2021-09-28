package project.tuthree.domain.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FaqType {
    /**
     * 로그인/회원가입, 수업매칭서비스, 수업관리서비스, 기타, 채팅서비스
     */

    CERTIFY("사용자인증"),
    MATCHING("수업매칭서비스"),
    MANAGE("수업관리서비스"),
    ETC("기타");

    private String korType;

    FaqType(String korType) {
        this.korType = korType;
    }
}
