package project.tuthree.domain.user;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Tel {

    private String tel1;
    private String tel2;
    private String tel3;

    protected Tel() {

    }

    public Tel(String tel1, String tel2, String tel3) {
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.tel3 = tel3;
    }
}
