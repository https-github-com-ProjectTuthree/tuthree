package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.user.TeacherDTO;
import project.tuthree.mapper.TeacherMapper;
import project.tuthree.repository.UserEntityRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;
    private final TeacherMapper teacherMapper;

    public TeacherDTO findById(String id) {
        Teacher teacher = userEntityRepository.teacherFindById(id);
        TeacherDTO teacherDTO = teacherMapper.toDto(teacher);
        return teacherDTO;
    }
}
