package project.tuthree.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import project.tuthree.domain.post.PostTestPaper;
import project.tuthree.domain.post.PostTestPaper.PostTestPaperBuilder;
import project.tuthree.dto.post.PostTestPaperDTO;
import project.tuthree.dto.post.PostTestPaperDTO.PostTestPaperDTOBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-20T00:48:46+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 1.8.0_291 (Oracle Corporation)"
)
@Component
public class PostTestPaperMapperImpl implements PostTestPaperMapper {

    @Override
    public List<PostTestPaperDTO> toDto(List<PostTestPaper> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PostTestPaperDTO> list = new ArrayList<PostTestPaperDTO>( entityList.size() );
        for ( PostTestPaper postTestPaper : entityList ) {
            list.add( toDto( postTestPaper ) );
        }

        return list;
    }

    @Override
    public List<PostTestPaper> toEntity(List<PostTestPaperDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<PostTestPaper> list = new ArrayList<PostTestPaper>( dtoList.size() );
        for ( PostTestPaperDTO postTestPaperDTO : dtoList ) {
            list.add( toEntity( postTestPaperDTO ) );
        }

        return list;
    }

    @Override
    public PostTestPaperDTO toDto(PostTestPaper postTestPaper) {
        if ( postTestPaper == null ) {
            return null;
        }

        PostTestPaperDTOBuilder postTestPaperDTO = PostTestPaperDTO.builder();

        postTestPaperDTO.id( postTestPaper.getId() );
        postTestPaperDTO.userId( postTestPaper.getUserId() );
        postTestPaperDTO.title( postTestPaper.getTitle() );
        postTestPaperDTO.content( postTestPaper.getContent() );
        postTestPaperDTO.view( postTestPaper.getView() );
        postTestPaperDTO.writeAt( postTestPaper.getWriteAt() );
        postTestPaperDTO.alterAt( postTestPaper.getAlterAt() );
        postTestPaperDTO.secret( postTestPaper.getSecret() );

        return postTestPaperDTO.build();
    }

    @Override
    public PostTestPaper toEntity(PostTestPaperDTO posttestpaperDTO) {
        if ( posttestpaperDTO == null ) {
            return null;
        }

        PostTestPaperBuilder postTestPaper = PostTestPaper.builder();

        postTestPaper.userId( posttestpaperDTO.getUserId() );
        postTestPaper.title( posttestpaperDTO.getTitle() );
        postTestPaper.content( posttestpaperDTO.getContent() );
        postTestPaper.view( posttestpaperDTO.getView() );
        postTestPaper.writeAt( posttestpaperDTO.getWriteAt() );
        postTestPaper.alterAt( posttestpaperDTO.getAlterAt() );
        postTestPaper.secret( posttestpaperDTO.getSecret() );

        return postTestPaper.build();
    }
}
