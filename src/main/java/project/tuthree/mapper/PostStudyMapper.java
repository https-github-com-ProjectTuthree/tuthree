package project.tuthree.mapper;

import org.mapstruct.Mapper;
import project.tuthree.domain.post.PostStudy;
import project.tuthree.dto.post.PoststudyDTO;


@Mapper(componentModel = "spring")
public interface PostStudyMapper extends GenericMapper<PoststudyDTO, PostStudy> {
    PoststudyDTO toDto(PostStudy postStudy);

    PostStudy toEntity(PoststudyDTO poststudyDTO);
}
