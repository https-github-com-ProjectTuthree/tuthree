package project.tuthree.exception;

public class NotEnoughUserException extends Exception{

    private String Message;

    public NotEnoughUserException() {
        this.Message = "NotEnoughUserException";
    }
}
