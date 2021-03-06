package project.tuthree.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.dto.post.PostfaqDTO;

@Mapper(componentModel = "spring")
public interface PostFaqMapper extends GenericMapper<PostfaqDTO, PostFaq> {

//인자로 받은 것 / setter로 설정할 것
    @Mappings({

            @Mapping(source = "admin", target = "adminId")
    })
    PostfaqDTO toDto(PostFaq postFaq);

    @Mappings({
            @Mapping(source = "adminId", target = "admin")
    })
    PostFaq toEntity(PostfaqDTO postfaqDTO);

}
