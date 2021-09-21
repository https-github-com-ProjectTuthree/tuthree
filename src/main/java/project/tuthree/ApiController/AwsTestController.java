package project.tuthree.ApiController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AwsTestController {

    @GetMapping("/test")
    public String test() {
        String test = "aws test success!!";
        return test;
    }
}
