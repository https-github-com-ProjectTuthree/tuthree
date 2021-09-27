package project.tuthree.testlogic;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j
public class ChatApiController {

    @GetMapping("/chat")
    public void startChat() {
        log.info("@ChatController, chat GET()");
        
    }
}
