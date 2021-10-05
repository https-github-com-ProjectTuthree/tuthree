package project.tuthree.exception;

import lombok.Getter;

@Getter
public class NotEnoughGradeException extends Exception {

    private String Message;

    public NotEnoughGradeException() {
        Message = "NotEnoughGradeException";
    }
}
