package project.tuthree.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import project.tuthree.domain.post.PostNotice;
import project.tuthree.domain.post.PostNotice.PostNoticeBuilder;
import project.tuthree.dto.post.PostnoticeDTO;
import project.tuthree.dto.post.PostnoticeDTO.PostnoticeDTOBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-22T12:06:20+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 1.8.0_291 (Oracle Corporation)"
)
@Component
public class PostNoticeMapperImpl implements PostNoticeMapper {

    @Override
    public List<PostnoticeDTO> toDto(List<PostNotice> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PostnoticeDTO> list = new ArrayList<PostnoticeDTO>( entityList.size() );
        for ( PostNotice postNotice : entityList ) {
            list.add( toDto( postNotice ) );
        }

        return list;
    }

    @Override
    public List<PostNotice> toEntity(List<PostnoticeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<PostNotice> list = new ArrayList<PostNotice>( dtoList.size() );
        for ( PostnoticeDTO postnoticeDTO : dtoList ) {
            list.add( toEntity( postnoticeDTO ) );
        }

        return list;
    }

    @Override
    public PostnoticeDTO toDto(PostNotice postNotice) {
        if ( postNotice == null ) {
            return null;
        }

        PostnoticeDTOBuilder postnoticeDTO = PostnoticeDTO.builder();

        postnoticeDTO.adminId( postNotice.getAdmin() );
        postnoticeDTO.id( postNotice.getId() );
        postnoticeDTO.type( postNotice.getType() );
        postnoticeDTO.title( postNotice.getTitle() );
        postnoticeDTO.content( postNotice.getContent() );
        postnoticeDTO.view( postNotice.getView() );
        postnoticeDTO.writeAt( postNotice.getWriteAt() );
        postnoticeDTO.alterAt( postNotice.getAlterAt() );
        postnoticeDTO.secret( postNotice.getSecret() );

        return postnoticeDTO.build();
    }

    @Override
    public PostNotice toEntity(PostnoticeDTO postnoticeDTO) {
        if ( postnoticeDTO == null ) {
            return null;
        }

        PostNoticeBuilder postNotice = PostNotice.builder();

        postNotice.admin( postnoticeDTO.getAdminId() );
        postNotice.title( postnoticeDTO.getTitle() );
        postNotice.content( postnoticeDTO.getContent() );
        postNotice.view( postnoticeDTO.getView() );
        postNotice.writeAt( postnoticeDTO.getWriteAt() );
        postNotice.alterAt( postnoticeDTO.getAlterAt() );
        postNotice.secret( postnoticeDTO.getSecret() );
        postNotice.type( postnoticeDTO.getType() );

        return postNotice.build();
    }
}
