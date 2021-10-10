package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.ApiController.EmbeddedResponse;
import project.tuthree.ApiController.StatusCode;
import project.tuthree.domain.user.*;
import project.tuthree.dto.user.AdminDTO;
import project.tuthree.repository.AdminRepository;

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
    public void userList(String grade){
        if (grade == "PARENT"){
            userRepository.findAll();
        }
        else if(grade == "TEACHER"){
            teacherRepository.findAll();
        }
        else{
            studentRepository.findAll();
        }
    }
}
