package project.tuthree.mapper;

import org.mapstruct.Mapper;
import project.tuthree.domain.user.Student;
import project.tuthree.dto.user.StudentDTO;


@Mapper(componentModel = "spring")
public interface StudentMapper extends GenericMapper<StudentDTO, Student> {

    StudentDTO toDto(Student student);

    Student toEntity(StudentDTO studentDTO);
}
