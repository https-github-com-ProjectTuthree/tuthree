package project.tuthree.testlogic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testChat {

    @GetMapping("/room1")
    public String room1() {
        return "chatRoom1";
    }

    @GetMapping("/room2")
    public String room2() {
        return "chatRoom2";
    }

    @GetMapping("/room3")
    public String room3() {
        return "chatRoom3";
    }
}
