package project.tuthree.mapper;

import org.mapstruct.Mapper;
import project.tuthree.domain.room.StudyRoomInfo;
import project.tuthree.dto.room.StudyroomInfoDTO;

@Mapper(componentModel = "spring")
public interface StudyroomInfoMapper extends GenericMapper<StudyroomInfoDTO, StudyRoomInfo> {

    StudyroomInfoDTO toDto(StudyRoomInfo studyRoomInfo);

    StudyRoomInfo toEntity(StudyroomInfoDTO studyroomInfoDTO);
}
