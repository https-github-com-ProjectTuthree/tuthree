package project.tuthree.mapper;

import org.mapstruct.Mapper;
import project.tuthree.domain.post.PostReview;
import project.tuthree.dto.post.PostreviewDTO;

@Mapper(componentModel = "spring")
public interface PostReviewMapper extends GenericMapper<PostreviewDTO, PostReview> {

    PostreviewDTO toDto(PostReview postReview);

    PostReview toEntity(PostreviewDTO postreviewDTO);
}
