package project.tuthree.domain.post;

import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class PostNotice extends Post{

    private String id; //admin
}
