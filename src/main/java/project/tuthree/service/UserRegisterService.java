package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.user.User;
import project.tuthree.domain.user.UserRepository;
import project.tuthree.dto.UserRegisterDTO;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;

    @Transactional
    public String createUser(UserRegisterDTO registerDTO){
        return userRepository.save(registerDTO.toEntity()).getId();
    }
}
