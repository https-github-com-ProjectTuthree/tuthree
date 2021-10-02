package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.TeacherDTO;
import project.tuthree.mapper.TeacherMapper;
import project.tuthree.repository.TeacherEntityRepository;


@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherEntityRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public TeacherDTO findById(String id) {
        Teacher teacher = teacherRepository.findById(id);
        TeacherDTO teacherDTO = teacherMapper.toDto(teacher);
        return teacherDTO;

    }
}
