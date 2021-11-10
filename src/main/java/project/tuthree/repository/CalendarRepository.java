package project.tuthree.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.room.Calendar;
import project.tuthree.domain.room.StudyRoom;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static project.tuthree.domain.room.QCalendar.calendar;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class CalendarRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;
    private final UserFileRepository userFileRepository;


    /** 스터디룸 캘린더 전체 조회 - 일정, 보고서 */

    public List<Calendar> findByStudyroom(StudyRoom studyRoom){
        return jpaQueryFactory.selectFrom(calendar)
                .where(calendar.studyRoomId.eq(studyRoom))
                .fetch();
    }

    /** 특정 날짜 일정 조회하기 */
    public List<Calendar> findByDate(String teacherId, String studentId, String date) throws ParseException {
        List<Calendar> fetch = jpaQueryFactory.selectFrom(calendar)
                .where(calendar.studyRoomId.teacherId.id.eq(teacherId)
                        .and(calendar.studyRoomId.studentId.id.eq(studentId)))
                .fetch();
        if(fetch.isEmpty()) throw new NullPointerException("스터디룸에 등록된 일정이 없습니다.");

        List<Calendar> list = new ArrayList<>();

        for (Calendar c : fetch) {
            if (userFileRepository.unixToDate(c.getDateAt()).equals(date)) list.add(c);
        }

        if(list.isEmpty()) throw new NullPointerException("해당 날짜에 등록된 일정이 없습니다.");
        return list;
    }

    /** 일정 등록 하기 */
    public Long registerCalendar(Calendar calendar) {
        em.persist(calendar);
        return calendar.getId();
    }

    /** 일정 수정하기 */
    public Long updateCalendar(Long id, Calendar calendar) {
        Calendar calendar1 = em.find(Calendar.class, id);
        calendar1.updateCalendar(calendar);
        return calendar1.getId();
    }

    /** 일정 삭제하기 */
    public Long deleteCalendar(Long id) {
        em.remove(em.find(Calendar.class, id));
        return id;
    }

}
