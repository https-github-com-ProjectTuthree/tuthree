package project.tuthree.domain.user;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Mail {

    private String mail;
    private String domain;

    protected Mail() {

    }

    public Mail(String mail, String domain) {
        this.mail = mail;
        this.domain = domain;
    }
}
