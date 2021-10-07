package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.tuthree.dto.user.AdminDTO;
import project.tuthree.repository.AdminRepository;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public String adminLogin(AdminDTO adminDTO) {
        return adminRepository.findByIdPwd(adminDTO.getId(), adminDTO.getPwd());
    }
}
