package project.tuthree.mapper;

import org.mapstruct.Mapper;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.user.TeacherDTO;

@Mapper(componentModel = "spring")
public interface TeacherMapper extends GenericMapper<TeacherDTO, Teacher> {


    TeacherDTO toDto(Teacher teacher);

    Teacher toEntity(TeacherDTO teacherDTO);
}
