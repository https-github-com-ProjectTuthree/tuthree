package project.tuthree.mapper;

import org.mapstruct.Mapper;
import project.tuthree.domain.post.PostTestPaper;
import project.tuthree.dto.post.PostTestPaperDTO;

@Mapper(componentModel = "spring")
public interface PostTestPaperMapper extends GenericMapper<PostTestPaperDTO, PostTestPaper> {

    PostTestPaperDTO toDto(PostTestPaper postTestPaper);

    PostTestPaper toEntity(PostTestPaperDTO posttestpaperDTO);

}
