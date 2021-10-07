package project.tuthree.mapper;


import org.mapstruct.Mapper;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.dto.room.StudyroomDTO;

@Mapper(componentModel = "spring")
public interface StudyRoomMapper extends GenericMapper<StudyroomDTO, StudyRoom> {

    StudyroomDTO toDto(StudyRoom studyRoom);

    StudyRoom toEntity(StudyroomDTO studyroomDTO);
}
