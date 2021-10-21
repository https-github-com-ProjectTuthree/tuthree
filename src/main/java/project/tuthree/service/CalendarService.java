package project.tuthree.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.tuthree.domain.room.Calendar;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.dto.room.CalendarDTO;
import project.tuthree.mapper.CalendarMapper;
import project.tuthree.repository.*;
import project.tuthree.repository.PostStudyRepository.StudyListDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static project.tuthree.exception.ExceptionSupplierImpl.wrap;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final StudyRoomRepository studyRoomRepository;
    private final CalendarMapper calendarMapper;
    private final PostStudyService postStudyService;
    private final UserFileRepository userFileRepository;

    /** 선생님, 학생 아이디로 스터디룸 일정 전체 조회 */
    public List<calendarFullListDTO> findByStudyroomIds(String teacherId, String studentId) throws ParseException {
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId);
        List<calendarFullListDTO> list = new ArrayList<>();
        List<Calendar> calendars = calendarRepository.findByStudyroom(studyRoom);
        for(Calendar c : calendars) {
            list.add(new calendarFullListDTO(c.getId(),
                    userFileRepository.unixToDate(c.getDateAt()), c.getSchedule(), "일정"));
        }

        List<StudyListDTO> postStudy = postStudyService.findPostByStudyRoom(teacherId, studentId);
        for(StudyListDTO s : postStudy){
            list.add(new calendarFullListDTO(s.getId(), s.getDateAt(), String.valueOf(s.getNumber()) + "회차", "보고서"));
        }
        return list;
    }

    /**
     * 특정 날짜 일정 조회하기
     */
    public List<CalendarListDTO> findByDate(String teacherId, String studentId, String date) throws ParseException {

        List<Calendar> dateList = calendarRepository.findByDate(teacherId, studentId, date);
        return dateList.stream()
                .map(m -> wrap(() -> new CalendarListDTO(m.getId(), userFileRepository.unixToDate(m.getDateAt()), m.getSchedule())))
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
    public static class calendarFullListDTO{
        Long id;
        String date;
        String schedule;
        String type;
    }

    @Getter
    @AllArgsConstructor
    public static class CalendarListDTO{
        Long id;
        String dateAt;
        String schedule;
    }
}
