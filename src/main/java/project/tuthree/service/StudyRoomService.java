package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Student;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.room.StudyroomDTO;
import project.tuthree.dto.room.StudyroomInfoDTO;
import project.tuthree.mapper.StudyRoomMapper;
import project.tuthree.mapper.StudyroomInfoMapper;
import project.tuthree.repository.StudyRoomRepository;
import project.tuthree.repository.UserEntityRepository;

@Service
@RequiredArgsConstructor
public class StudyRoomService {
    private final StudyRoomRepository studyRoomRepository;
    private final StudyroomInfoMapper studyroomInfoMapper;
    private final UserEntityRepository userEntityRepository;
    private final StudyRoomMapper studyRoomMapper;

    /** 스터디룸 개설하기 */
    public String roomRegister(String teacherId, String studentId) {
        Teacher teacher = userEntityRepository.teacherFindById(teacherId);
        Student student = userEntityRepository.studentFindById(studentId);
        StudyroomDTO studyroomDTO = new StudyroomDTO(teacher, student, Status.CLOSE);
        return studyRoomRepository.roomRegister(studyRoomMapper.toEntity(studyroomDTO));
    }

    /** 수업계획서 등록하기 */
    public String infoRegister(StudyroomInfoDTO studyroomInfoDTO) {
        return studyRoomRepository.infoRegister(studyroomInfoMapper.toEntity(studyroomInfoDTO));
    }
}
