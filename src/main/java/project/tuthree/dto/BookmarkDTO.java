package project.tuthree.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkDTO {

    private Long id;
    private String user1;
    private String user2;

    public BookmarkDTO(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    @Builder
    public BookmarkDTO(Long id, String user1, String user2) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
    }
}
