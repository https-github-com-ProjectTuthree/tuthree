package project.tuthree.mapper;

import project.tuthree.domain.post.PostFind;
import project.tuthree.dto.post.PostfindDTO;

public interface PostFindMapper extends GenericMapper<PostfindDTO, PostFind> {

    PostfindDTO toDto(PostFind postFind);

    PostFind toEntity(PostfindDTO postfindDTO);

}
