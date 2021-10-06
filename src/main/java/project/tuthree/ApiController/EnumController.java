package project.tuthree.ApiController;

import org.springframework.web.bind.annotation.PathVariable;
import project.tuthree.ApiController.EmbeddedResponse.RestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.tuthree.domain.user.Category;

import java.util.HashMap;
import java.util.List;

@RestController
public class EnumController {

    //한글만 출력됨,, 하위카테고리 나오게 하기
    @GetMapping("/enum/{code}")
    private RestResponse showCategory(@PathVariable String code){
        return new RestResponse(true, Category.valueOf(code));
    }
}
