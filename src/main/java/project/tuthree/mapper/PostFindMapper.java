package project.tuthree.mapper;

import org.mapstruct.Mapper;
import project.tuthree.domain.post.PostFind;
import project.tuthree.dto.post.PostfindDTO;

@Mapper(componentModel = "spring")
public interface PostFindMapper extends GenericMapper<PostfindDTO, PostFind> {

    PostfindDTO toDto(PostFind postFind);

    PostFind toEntity(PostfindDTO postfindDTO);

}
