package project.tuthree.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.SchoolStatus;
import project.tuthree.domain.user.Sex;
import project.tuthree.domain.user.Teacher;
import project.tuthree.domain.user.Teacher.TeacherBuilder;
import project.tuthree.dto.TeacherDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-03T15:37:46+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 1.8.0_291 (Oracle Corporation)"
)
@Component
public class TeacherMapperImpl implements TeacherMapper {

    @Override
    public List<TeacherDTO> toDto(List<Teacher> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TeacherDTO> list = new ArrayList<TeacherDTO>( entityList.size() );
        for ( Teacher teacher : entityList ) {
            list.add( toDto( teacher ) );
        }

        return list;
    }

    @Override
    public List<Teacher> toEntity(List<TeacherDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Teacher> list = new ArrayList<Teacher>( dtoList.size() );
        for ( TeacherDTO teacherDTO : dtoList ) {
            list.add( toEntity( teacherDTO ) );
        }

        return list;
    }

    @Override
    public TeacherDTO toDto(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        String id = null;
        String pwd = null;
        String name = null;
        String email = null;
        String tel = null;
        Sex sex = null;
        int birth = 0;
        String post = null;
        Status notification = null;
        Grade grade = null;
        Date create_date = null;
        String region = null;
        Status registration = null;
        String subject = null;
        int cost = 0;
        String school = null;
        SchoolStatus status = null;
        String major = null;
        String certification = null;
        boolean certifyStatus = false;
        String detail = null;

        id = teacher.getId();
        pwd = teacher.getPwd();
        name = teacher.getName();
        email = teacher.getEmail();
        tel = teacher.getTel();
        sex = teacher.getSex();
        birth = teacher.getBirth();
        post = teacher.getPost();
        notification = teacher.getNotification();
        grade = teacher.getGrade();
        create_date = teacher.getCreate_date();
        region = teacher.getRegion();
        registration = teacher.getRegistration();
        subject = teacher.getSubject();
        cost = teacher.getCost();
        school = teacher.getSchool();
        status = teacher.getStatus();
        major = teacher.getMajor();
        certification = teacher.getCertification();
        certifyStatus = teacher.isCertifyStatus();
        detail = teacher.getDetail();

        TeacherDTO teacherDTO = new TeacherDTO( id, pwd, name, email, tel, sex, birth, post, notification, grade, create_date, region, registration, subject, cost, school, status, major, certification, certifyStatus, detail );

        return teacherDTO;
    }

    @Override
    public Teacher toEntity(TeacherDTO teacherDTO) {
        if ( teacherDTO == null ) {
            return null;
        }

        TeacherBuilder teacher = Teacher.builder();

        teacher.id( teacherDTO.getId() );
        teacher.pwd( teacherDTO.getPwd() );
        teacher.name( teacherDTO.getName() );
        teacher.email( teacherDTO.getEmail() );
        teacher.tel( teacherDTO.getTel() );
        teacher.sex( teacherDTO.getSex() );
        teacher.birth( teacherDTO.getBirth() );
        teacher.post( teacherDTO.getPost() );
        teacher.notification( teacherDTO.getNotification() );
        teacher.grade( teacherDTO.getGrade() );
        teacher.create_date( teacherDTO.getCreate_date() );
        teacher.region( teacherDTO.getRegion() );
        teacher.registration( teacherDTO.getRegistration() );
        teacher.subject( teacherDTO.getSubject() );
        teacher.cost( teacherDTO.getCost() );
        teacher.school( teacherDTO.getSchool() );
        teacher.status( teacherDTO.getStatus() );
        teacher.major( teacherDTO.getMajor() );
        teacher.certification( teacherDTO.getCertification() );
        teacher.certifyStatus( teacherDTO.isCertifyStatus() );
        teacher.detail( teacherDTO.getDetail() );

        return teacher.build();
    }
}
