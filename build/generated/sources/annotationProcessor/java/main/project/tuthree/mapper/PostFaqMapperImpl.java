package project.tuthree.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.domain.post.PostFaq.PostFaqBuilder;
import project.tuthree.dto.PostfaqDTO;
import project.tuthree.dto.PostfaqDTO.PostfaqDTOBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-27T22:54:52+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 1.8.0_291 (Oracle Corporation)"
)
@Component
public class PostFaqMapperImpl implements PostFaqMapper {

    @Override
    public List<PostfaqDTO> toDto(List<PostFaq> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PostfaqDTO> list = new ArrayList<PostfaqDTO>( entityList.size() );
        for ( PostFaq postFaq : entityList ) {
            list.add( toDto( postFaq ) );
        }

        return list;
    }

    @Override
    public List<PostFaq> toEntity(List<PostfaqDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<PostFaq> list = new ArrayList<PostFaq>( dtoList.size() );
        for ( PostfaqDTO postfaqDTO : dtoList ) {
            list.add( toEntity( postfaqDTO ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(PostfaqDTO dto, PostFaq entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getContent() != null ) {
            entity.setContent( dto.getContent() );
        }
        if ( dto.getView() != null ) {
            entity.setView( dto.getView() );
        }
        if ( dto.getWriteAt() != null ) {
            entity.setWriteAt( dto.getWriteAt() );
        }
        if ( dto.getAlterAt() != null ) {
            entity.setAlterAt( dto.getAlterAt() );
        }
        if ( dto.getSecret() != null ) {
            entity.setSecret( dto.getSecret() );
        }
        if ( dto.getType() != null ) {
            entity.setType( dto.getType() );
        }
    }

    @Override
    public PostfaqDTO toDto(PostFaq postFaq) {
        if ( postFaq == null ) {
            return null;
        }

        PostfaqDTOBuilder postfaqDTO = PostfaqDTO.builder();

        postfaqDTO.adminId( postFaq.getAdmin() );
        postfaqDTO.id( postFaq.getId() );
        postfaqDTO.type( postFaq.getType() );
        postfaqDTO.title( postFaq.getTitle() );
        postfaqDTO.content( postFaq.getContent() );
        postfaqDTO.view( postFaq.getView() );
        postfaqDTO.writeAt( postFaq.getWriteAt() );
        postfaqDTO.alterAt( postFaq.getAlterAt() );
        postfaqDTO.secret( postFaq.getSecret() );

        return postfaqDTO.build();
    }

    @Override
    public PostFaq toEntity(PostfaqDTO postfaqDTO) {
        if ( postfaqDTO == null ) {
            return null;
        }

        PostFaqBuilder postFaq = PostFaq.builder();

        postFaq.admin( postfaqDTO.getAdminId() );
        postFaq.title( postfaqDTO.getTitle() );
        postFaq.content( postfaqDTO.getContent() );
        postFaq.view( postfaqDTO.getView() );
        postFaq.writeAt( postfaqDTO.getWriteAt() );
        postFaq.alterAt( postfaqDTO.getAlterAt() );
        postFaq.secret( postfaqDTO.getSecret() );
        postFaq.type( postfaqDTO.getType() );

        return postFaq.build();
    }
}