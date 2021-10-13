package project.tuthree.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.room.Calendar;
import project.tuthree.domain.room.QCalendar;
import project.tuthree.domain.room.StudyRoom;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static project.tuthree.domain.room.QCalendar.calendar;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class CalendarRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    /** 스터디룸 캘린더 전체 조회 - 일정, 보고서 */

    public List<Calendar> findByStudyroom(StudyRoom studyRoom){

        return jpaQueryFactory.selectFrom(calendar)
                .where(calendar.studyRoomId.eq(studyRoom))
                .fetch();
    }

    /** 특정 날짜 일정 조회하기 */
    public List<Calendar> findByDate(String teacherId, String studentId, Date date) {
        return jpaQueryFactory.selectFrom(calendar)
                .where(calendar.dateAt.eq(date)
                        .and(calendar.studyRoomId.teacherId.id.eq(teacherId))
                        .and(calendar.studyRoomId.studentId.id.eq(studentId)))
                .fetch();
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
