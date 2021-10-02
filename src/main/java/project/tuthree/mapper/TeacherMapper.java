package project.tuthree.mapper;

import org.mapstruct.Mapper;
import project.tuthree.domain.post.PostTestPaper;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.PostTestPaperDTO;
import project.tuthree.dto.TeacherDTO;

@Mapper(componentModel = "spring")
public interface TeacherMapper extends GenericMapper<TeacherDTO, Teacher> {


    TeacherDTO toDto(Teacher teacher);

    Teacher toEntity(TeacherDTO teacherDTO);
}
