package project.tuthree.testlogic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import project.tuthree.domain.Status;
import project.tuthree.domain.file.UserFile;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.Sex;
import project.tuthree.domain.user.User;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Repository
public class ArrayToJson {

    public void toJson() throws IOException {


        ObjectMapper mapper = new ObjectMapper();
        User user1 = new User("parent1", "parent1", "name1", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture1", Status.CLOSE, Grade.PARENT, new Date());

        mapper.writeValue(new File("/home/seojaehui/test.json"), user1);

        String jsonSTring = mapper.writeValueAsString(user1);

    }
}
