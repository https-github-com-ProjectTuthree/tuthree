package project.tuthree.domain.post;

import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class PostFaq extends Post{

    private String admin_id; //admin
}
