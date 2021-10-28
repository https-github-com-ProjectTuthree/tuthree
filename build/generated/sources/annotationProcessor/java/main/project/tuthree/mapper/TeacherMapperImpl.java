package project.tuthree.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import project.tuthree.domain.user.Teacher;
import project.tuthree.domain.user.Teacher.TeacherBuilder;
import project.tuthree.dto.user.TeacherDTO;
import project.tuthree.dto.user.TeacherDTO.TeacherDTOBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
<<<<<<< HEAD
    date = "2021-10-29T05:03:55+0900",
=======
    date = "2021-10-27T15:16:17+0900",
>>>>>>> 5dfd0ebfafc32d184cadb884062ade5a4105a111
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

        TeacherDTOBuilder teacherDTO = TeacherDTO.builder();

        teacherDTO.id( teacher.getId() );
        teacherDTO.pwd( teacher.getPwd() );
        teacherDTO.name( teacher.getName() );
        teacherDTO.email( teacher.getEmail() );
        teacherDTO.tel( teacher.getTel() );
        teacherDTO.sex( teacher.getSex() );
        if ( teacher.getBirth() != null ) {
            teacherDTO.birth( teacher.getBirth() );
        }
        teacherDTO.post( teacher.getPost() );
        teacherDTO.notification( teacher.getNotification() );
        teacherDTO.grade( teacher.getGrade() );
        teacherDTO.createDate( teacher.getCreateDate() );
        teacherDTO.registration( teacher.getRegistration() );
        teacherDTO.cost( teacher.getCost() );
        teacherDTO.school( teacher.getSchool() );
        teacherDTO.status( teacher.getStatus() );
        teacherDTO.major( teacher.getMajor() );
        teacherDTO.star( teacher.getStar() );
        teacherDTO.certification( teacher.getCertification() );
        teacherDTO.certifyStatus( teacher.isCertifyStatus() );
        teacherDTO.detail( teacher.getDetail() );
        teacherDTO.userDel( teacher.getUserDel() );

        return teacherDTO.build();
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
        teacher.createDate( teacherDTO.getCreateDate() );
        teacher.registration( teacherDTO.getRegistration() );
        teacher.cost( teacherDTO.getCost() );
        teacher.school( teacherDTO.getSchool() );
        teacher.status( teacherDTO.getStatus() );
        teacher.major( teacherDTO.getMajor() );
        teacher.star( teacherDTO.getStar() );
        teacher.certification( teacherDTO.getCertification() );
        teacher.certifyStatus( teacherDTO.isCertifyStatus() );
        teacher.detail( teacherDTO.getDetail() );
        teacher.userDel( teacherDTO.getUserDel() );

        return teacher.build();
    }
}
