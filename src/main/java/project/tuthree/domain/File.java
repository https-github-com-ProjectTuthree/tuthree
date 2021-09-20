package project.tuthree.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class File {
    @Id @GeneratedValue
    private Long file_id;

    private String user_id;
    private Long post_id;
    private String real_title;
    private String save_title;
}
