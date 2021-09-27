package project.tuthree.domain.post;

public enum NoticeType {

    act1("타입1"),
    act2("타입2"),
    act3("타입3");

    private String korType;

    NoticeType(String korType) {
        this.korType = korType;
    }
}
