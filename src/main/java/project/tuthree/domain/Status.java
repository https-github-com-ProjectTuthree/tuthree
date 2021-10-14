package project.tuthree.domain;

import lombok.Getter;

@Getter
public enum Status {

    OPEN("open"),
    CLOSE("close");

    private String eng;

    Status(String eng) {
        this.eng = eng;
    }

}
