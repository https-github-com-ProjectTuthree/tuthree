package project.tuthree.testlogic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.stereotype.Repository;
import project.tuthree.domain.Status;
import project.tuthree.domain.file.UserFile;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.Sex;
import project.tuthree.domain.user.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Repository
public class ArrayToJson {

    public void toJson(Object object) throws IOException {


        ObjectMapper mapper = new ObjectMapper();
        User user1 = new User("parent1", "parent1", "name1", "email@naver.com", "01012341234", Sex.FEMALE, 1990, "p_picture1", Status.CLOSE, Grade.PARENT, new Date());

        mapper.writeValue(new File("/home/seojaehui/test.json"), user1);

        String jsonSTring = mapper.writeValueAsString(user1);

    }

    public Object tobyte(Object object) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        String item = objectMapper.writeValueAsString(object);
        System.out.println("item = " + item);

        byte[] buf = item.getBytes(StandardCharsets.UTF_8);
        System.out.println("buf = " + buf);


        String buf_item = buf.toString();

        //byte. tostring
        String tel = new String(buf);
        System.out.println("tel = " + tel);


        Map<String, String> list = objectMapper.readValue(tel, HashMap.class);

        String name = objectMapper.writeValueAsString(list);
        System.out.println("name = " + name);

        return list;


//        SerializationUtils.serialize(object);
//        System.out.println("object = " + object);
    }

    public static void main(String[] args) throws IOException {
        List<String> subject = new ArrayList<>();
        subject.add("math");
        subject.add("kor");
        subject.add("eng");
        //list

        Map<String, Map<String, String>> map = new HashMap<>();
        Map<String, String> time1 = new HashMap<>();
        //hash<hash>
        time1.put("start", "17:00");
        time1.put("end", "20:00");
        map.put("mon", time1);
        Map<String, String> time2 = new HashMap<>();

        time2.put("start", "20:00");
        time2.put("end", "24:00");
        map.put("tue", time2);
        Map<String, Object> info = new HashMap<>();

        info.put("subject", subject);
        info.put("schedule", map);

        ArrayToJson arrayToJson = new ArrayToJson();
        arrayToJson.tobyte(info);
    }
}
