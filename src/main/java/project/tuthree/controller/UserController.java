package project.tuthree.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import project.tuthree.dto.UserRegisterDTO;
import project.tuthree.service.UserRegisterService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRegisterService userRegisterService;

    @PostMapping("/user/register")
    public String parentRegister(UserRegisterDTO userRegisterDTO){
        userRegisterService.createUser(userRegisterDTO);
        return "redirect:/";
    }

}
