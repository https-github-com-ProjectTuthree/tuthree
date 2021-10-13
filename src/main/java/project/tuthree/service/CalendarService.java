package project.tuthree.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.tuthree.domain.room.Calendar;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.domain.user.Student;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.room.CalendarDTO;
import project.tuthree.mapper.CalendarMapper;
import project.tuthree.repository.CalendarRepository;
import project.tuthree.repository.StudyRoomRepository;
import project.tuthree.repository.UserEntityRepository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarService {
    private final JPAQueryFactory jpaQueryFactory;
    private final CalendarRepository calendarRepository;
    private final StudyRoomRepository studyRoomRepository;
    private final CalendarMapper calendarMapper;
    private final UserEntityRepository userEntityRepository;

    /** 선생님, 학생 아이디로 스터디룸 일정 전체 조회 */
    public List<CalendarDTO> findByStudyroomIds(String teacherId, String studentId) {
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId);
        List<Calendar> calendars = calendarRepository.findByStudyroom(studyRoom);
        return calendars.stream()
                .map(m -> calendarMapper.toDto(m))
                .collect(Collectors.toList());
    }

    /**
     * 특정 날짜 일정 조회하기
     */
    public List<CalendarListDTO> findByDate(String teacherId, String studentId, Date date) {
        List<Calendar> dateList = calendarRepository.findByDate(teacherId, studentId, date);
        return dateList.stream()
                .map(m -> new CalendarListDTO(m.getId(), m.getDateAt(), m.getSchedule()))
                .collect(Collectors.toList());
    }

    /** 일정 등록하기 /////////////////////////////// */
    public Long registerCalendar(String teacherId, String studentId, CalendarDTO calendarDTO) {
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId);
        calendarDTO.updateDTO(studyRoom);
        return calendarRepository.registerCalendar(calendarMapper.toEntity(calendarDTO));
    }

    /** 일정 수정하기 */
    public Long updateCalendar(Long id, CalendarDTO calendarDTO) {
        return calendarRepository.updateCalendar(id, calendarMapper.toEntity(calendarDTO));
    }


    @Getter
    @AllArgsConstructor
    public static class CalendarListDTO{
        Long id;
        Date dateAt;
        String schedule;
    }
}
