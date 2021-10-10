package project.tuthree.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.Status;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.domain.user.*;
import project.tuthree.dto.room.StudyroomDTO;
import project.tuthree.dto.room.StudyroomInfoDTO;
import project.tuthree.mapper.StudyRoomMapper;
import project.tuthree.mapper.StudyroomInfoMapper;
import project.tuthree.repository.StudyRoomRepository;

import javax.persistence.EntityManager;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class StudyRoomServiceTest {
    @Autowired
    StudyRoomRepository studyRoomRepository;
    @Autowired
    StudyRoomMapper studyRoomMapper;
    @Autowired
    StudyroomInfoMapper studyroomInfoMapper;
    @Autowired
    EntityManager em;

    @Test
//    @Rollback(false)
    public void 스터디룸_개설() {
        Teacher teacher = new Teacher("teachertt", new BCryptPasswordEncoder().encode("teachertt"), "name2", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture2", Status.OPEN, Grade.TEACHER, new Date(), "t_region2", Status.OPEN, "t_subject2", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", 0.0,"t_certif2", true, "잘 부탁드립니다");
        Student student = new Student("studenttt", new BCryptPasswordEncoder().encode("studenttt"), "name1", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "picture1", Status.CLOSE, Grade.STUDENT, new Date(), "s_region1", Status.OPEN, "subject1", 200000, School.H1, "잘부탁드립니다", null);
        em.persist(teacher);
        em.persist(student);
        StudyroomDTO studyroomDTO = new StudyroomDTO(teacher, student, Status.OPEN);
        studyRoomRepository.roomRegister(studyRoomMapper.toEntity(studyroomDTO));
    }

    @Test
    @Rollback(false)
    public void 스터디룸_계획서_작성() {
        Teacher teacher = new Teacher("teachertt", new BCryptPasswordEncoder().encode("teachertt"), "name2", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture2", Status.OPEN, Grade.TEACHER, new Date(), "t_region2", Status.OPEN, "t_subject2", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", 0.0,"t_certif2", true, "잘 부탁드립니다");
        Student student = new Student("studenttt", new BCryptPasswordEncoder().encode("studenttt"), "name1", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "picture1", Status.CLOSE, Grade.STUDENT, new Date(), "s_region1", Status.OPEN, "subject1", 200000, School.H1, "잘부탁드립니다", null);
        em.persist(teacher);
        em.persist(student);
        StudyRoom studyRoom = new StudyRoom(teacher, student, Status.OPEN);
        em.persist(studyRoom);

        StudyroomInfoDTO dto = new StudyroomInfoDTO(studyRoom, "math", 200000, "mon", "17:00", "20:00", "deatil", new Date(), true);
        studyRoomRepository.infoRegister(studyroomInfoMapper.toEntity(dto));
    }

    @Test
    @Rollback(false)
    public void 스터디룸_계획서_수정() {
        Teacher teacher = new Teacher("teachertt", new BCryptPasswordEncoder().encode("teachertt"), "name2", "email@naver.com", "01012341234", Sex.MALE, 2001, "t_picture2", Status.OPEN, Grade.TEACHER, new Date(), "t_region2", Status.OPEN, "t_subject2", 200000, "가천대", SchoolStatus.IN_SCHOOL, "컴공", 0.0,"t_certif2", true, "잘 부탁드립니다");
        Student student = new Student("studenttt", new BCryptPasswordEncoder().encode("studenttt"), "name1", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "picture1", Status.CLOSE, Grade.STUDENT, new Date(), "s_region1", Status.OPEN, "subject1", 200000, School.H1, "잘부탁드립니다", null);
        em.persist(teacher);
        em.persist(student);
        StudyRoom studyRoom = new StudyRoom(teacher, student, Status.OPEN);
        em.persist(studyRoom);

        StudyroomInfoDTO dto = new StudyroomInfoDTO(studyRoom, "math", 200000, "mon", "17:00", "20:00", "detail", new Date(), true);
        em.persist(studyroomInfoMapper.toEntity(dto));

        StudyroomInfoDTO dto1 = new StudyroomInfoDTO(studyRoom, "korean", 4400000, "saturday", "21:00", "24:00", "hello.....student", null, true);
        studyRoomRepository.infoUpdate(studyroomInfoMapper.toEntity(dto1));
    }
}