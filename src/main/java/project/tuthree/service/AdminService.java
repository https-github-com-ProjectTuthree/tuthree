package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.user.*;
import project.tuthree.dto.user.AdminDTO;
import project.tuthree.dto.user.UserListDTO;
import project.tuthree.repository.AdminRepository;

import java.util.*;

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
    public List<UserListDTO> userList(int page) {
        List<User> userEntities = adminRepository.userByPage(page);
        List<Teacher> teacherEntites = adminRepository.teacherByPage(page);
        List<Student> studentEntities = adminRepository.studentByPage(page);

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
                    .createDate(user.getCreateDate())
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
                    .createDate(teacher.getCreateDate())
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
                    .createDate(student.getCreateDate())
                    .build();
            userListDTO.add(studentDTO);
        }
        Collections.sort(userListDTO, new ListComparator());

        return userListDTO;
    }
  /* @Transactional
   public Page<UserListDTO> userList(Pageable pageable) {
       Page<User> userEntities = userRepository.findAll(pageable);
       Page<Teacher> teacherEntites = teacherRepository.findAll(pageable);
       Page<Student> studentEntities = studentRepository.findAll(pageable);


       Page<UserListDTO> userPageList = userEntities.map(
               user -> new UserListDTO(
                       user.getId(), user.getPwd() , user.getName(),
                       user.getEmail(), user.getTel(), user.getSex(),
                       user.getBirth(), user.getGrade(), user.getCreateDate()
               ));


       return userPageList;
   }*/
    /**날짜순 정렬**/
    public class ListComparator implements Comparator<UserListDTO>{

        @Override
        public int compare(UserListDTO b1, UserListDTO b2) {
            return b1.getCreateDate().compareTo(b2.getCreateDate());
        }
    }



    @Transactional
    public Page<UserListDTO> userList(String grade, Pageable pageable, String id){
        if(Objects.equals(grade, "parent")){
            if(Objects.equals(id, null)) {
                Page<User> userList = userRepository.findAll(pageable);

                Page<UserListDTO> userPageList = userList.map(
                        user -> new UserListDTO(
                                user.getId(), user.getPwd(), user.getName(),
                                user.getEmail(), user.getTel(), user.getSex(),
                                user.getBirth(), user.getGrade(), user.getCreateDate()
                        ));
                return userPageList;
            }
            else{
                Page<User> userList = userRepository.findById(id, pageable);

                Page<UserListDTO> userPageList = userList.map(
                        user -> new UserListDTO(
                                user.getId(), user.getPwd(), user.getName(),
                                user.getEmail(), user.getTel(), user.getSex(),
                                user.getBirth(), user.getGrade(), user.getCreateDate()
                        ));
                return userPageList;
            }
        }
        else if(Objects.equals(grade, "teacher")){
            if(Objects.equals(id, null)) {
                Page<Teacher> userList = teacherRepository.findAll(pageable);

                Page<UserListDTO> userPageList = userList.map(
                        user -> new UserListDTO(
                                user.getId(), user.getPwd(), user.getName(),
                                user.getEmail(), user.getTel(), user.getSex(),
                                user.getBirth(), user.getGrade(), user.getCreateDate()
                        ));
                return userPageList;
            }else{
                Page<Teacher> userList = teacherRepository.findById(id, pageable);

                Page<UserListDTO> userPageList = userList.map(
                        user -> new UserListDTO(
                                user.getId(), user.getPwd(), user.getName(),
                                user.getEmail(), user.getTel(), user.getSex(),
                                user.getBirth(), user.getGrade(), user.getCreateDate()
                        ));
                return userPageList;
            }
        }
        else if(Objects.equals(grade, "student")){
            if(Objects.equals(id, null)) {
                Page<Student> userList = studentRepository.findAll(pageable);

                Page<UserListDTO> userPageList = userList.map(
                        user -> new UserListDTO(
                                user.getId(), user.getPwd(), user.getName(),
                                user.getEmail(), user.getTel(), user.getSex(),
                                user.getBirth(), user.getGrade(), user.getCreateDate()
                        ));
                return userPageList;
            }else{
                Page<Student> userList = studentRepository.findById(id, pageable);

                Page<UserListDTO> userPageList = userList.map(
                        user -> new UserListDTO(
                                user.getId(), user.getPwd(), user.getName(),
                                user.getEmail(), user.getTel(), user.getSex(),
                                user.getBirth(), user.getGrade(), user.getCreateDate()
                        ));
                return userPageList;
            }
        }
        else{
            return (Page<UserListDTO>) new NullPointerException();
        }


    }






}
