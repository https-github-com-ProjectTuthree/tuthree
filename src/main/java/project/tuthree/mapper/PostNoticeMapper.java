package project.tuthree.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.domain.post.PostNotice;
import project.tuthree.dto.PostfaqDTO;
import project.tuthree.dto.PostnoticeDTO;

@Mapper(componentModel = "spring")
public interface PostNoticeMapper extends GenericMapper<PostnoticeDTO, PostNotice> {

    @Mappings({
            @Mapping(source = "admin", target = "adminId")
    })
    PostnoticeDTO toDto(PostNotice postNotice);

    @Mappings({
            @Mapping(source = "adminId", target = "admin")
    })
    PostNotice toEntity(PostnoticeDTO postnoticeDTO);
}
