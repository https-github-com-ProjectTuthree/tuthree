package project.tuthree.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import project.tuthree.domain.file.UserFile;
import project.tuthree.domain.file.UserFile.UserFileBuilder;
import project.tuthree.dto.file.UserfileDTO;
import project.tuthree.dto.file.UserfileDTO.UserfileDTOBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-08T04:59:21+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
@Component
public class UserFileMapperImpl implements UserFileMapper {

    @Override
    public List<UserfileDTO> toDto(List<UserFile> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserfileDTO> list = new ArrayList<UserfileDTO>( entityList.size() );
        for ( UserFile userFile : entityList ) {
            list.add( toDto( userFile ) );
        }

        return list;
    }

    @Override
    public List<UserFile> toEntity(List<UserfileDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UserFile> list = new ArrayList<UserFile>( dtoList.size() );
        for ( UserfileDTO userfileDTO : dtoList ) {
            list.add( toEntity( userfileDTO ) );
        }

        return list;
    }

    @Override
    public UserfileDTO toDto(UserFile userFile) {
        if ( userFile == null ) {
            return null;
        }

        UserfileDTOBuilder userfileDTO = UserfileDTO.builder();

        userfileDTO.id( userFile.getId() );
        userfileDTO.studyRoomId( userFile.getStudyRoomId() );
        userfileDTO.testpaperId( userFile.getTestpaperId() );
        userfileDTO.saveTitle( userFile.getSaveTitle() );
        userfileDTO.realTitle( userFile.getRealTitle() );

        return userfileDTO.build();
    }

    @Override
    public UserFile toEntity(UserfileDTO userfileDTO) {
        if ( userfileDTO == null ) {
            return null;
        }

        UserFileBuilder userFile = UserFile.builder();

        userFile.studyRoomId( userfileDTO.getStudyRoomId() );
        userFile.testpaperId( userfileDTO.getTestpaperId() );
        userFile.saveTitle( userfileDTO.getSaveTitle() );
        userFile.realTitle( userfileDTO.getRealTitle() );

        return userFile.build();
    }
}
