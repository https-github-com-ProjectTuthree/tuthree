package project.tuthree.mapper;

import org.mapstruct.Mapper;
import project.tuthree.domain.file.UserFile;
import project.tuthree.dto.file.UserfileDTO;

@Mapper(componentModel = "spring")
public interface UserFileMapper extends GenericMapper<UserfileDTO, UserFile> {

    UserfileDTO toDto(UserFile userFile);

    UserFile toEntity(UserfileDTO userfileDTO);
}
