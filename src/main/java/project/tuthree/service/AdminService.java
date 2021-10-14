package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.user.*;
import project.tuthree.dto.user.AdminDTO;
import project.tuthree.dto.user.UserDTO;
import project.tuthree.dto.user.UserListDTO;
import project.tuthree.repository.AdminRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public String adminLogin(AdminDTO adminDTO) {
        return adminRepository.findByIdPwd(adminDTO.getId(), adminDTO.getPwd());
    }

    @Transactional
    public List<UserListDTO> userList() {
        List<User> userEntities = userRepository.findAll();
        List<Teacher> teacherEntites = teacherRepository.findAll();
        List<Student> studentEntities = studentRepository.findAll();

        List<UserListDTO> userListDTO = new ArrayList<>();

        for (User user : userEntities) {
            UserListDTO parentDTO = UserListDTO.builder()
                    .id(user.getId())
                    .pwd(user.getPwd())
                    .birth(user.getBirth())
                    .name(user.getName())
                    .email(user.getEmail())
                    .sex(user.getSex())
                    .grade(user.getGrade())
                    .tel(user.getTel())
                    .create_date(user.getCreate_date())
                    .build();
            userListDTO.add(parentDTO);
        }
        for (Teacher teacher : teacherEntites) {
            UserListDTO teacherDTO = UserListDTO.builder()
                    .id(teacher.getId())
                    .pwd(teacher.getPwd())
                    .birth(teacher.getBirth())
                    .name(teacher.getName())
                    .email(teacher.getEmail())
                    .sex(teacher.getSex())
                    .grade(teacher.getGrade())
                    .tel(teacher.getTel())
                    .create_date(teacher.getCreate_date())
                    .build();
            userListDTO.add(teacherDTO);
        }

        for (Student student : studentEntities) {
            UserListDTO studentDTO = UserListDTO.builder()
                    .id(student.getId())
                    .pwd(student.getPwd())
                    .birth(student.getBirth())
                    .name(student.getName())
                    .email(student.getEmail())
                    .sex(student.getSex())
                    .grade(student.getGrade())
                    .tel(student.getTel())
                    .create_date(student.getCreate_date())
                    .build();
            userListDTO.add(studentDTO);
        }
        Collections.sort(userListDTO, new ListComparator());

        return userListDTO;
    }
    public class ListComparator implements Comparator<UserListDTO>{

        @Override
        public int compare(UserListDTO b1, UserListDTO b2) {
            return b1.getCreate_date().compareTo(b2.getCreate_date());
        }
    }

/*    @Transactional
    public Page<User> parentList(Pageable pageable){
        return userRepository.findAll(pageable);
    }*/






}
