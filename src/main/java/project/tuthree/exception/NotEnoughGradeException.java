package project.tuthree.exception;

import lombok.Getter;

@Getter
public class NotEnoughGradeException extends Exception {

    private String Message;

    public NotEnoughGradeException() {
        this.Message = "옳지 않은 권한의 사용자입니다.";
    }
}
