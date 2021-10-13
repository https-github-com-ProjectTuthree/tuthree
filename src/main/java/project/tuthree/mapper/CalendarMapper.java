package project.tuthree.mapper;

import org.mapstruct.Mapper;
import project.tuthree.domain.room.Calendar;
import project.tuthree.domain.user.Teacher;
import project.tuthree.dto.room.CalendarDTO;
import project.tuthree.dto.user.TeacherDTO;

@Mapper(componentModel = "spring")
public interface CalendarMapper extends GenericMapper<CalendarDTO, Calendar> {
    CalendarDTO toDto(Calendar calendar);

    Calendar toEntity(CalendarDTO calendarDTO);
}
