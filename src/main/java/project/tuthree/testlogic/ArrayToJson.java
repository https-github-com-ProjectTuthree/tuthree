package project.tuthree.testlogic;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.tuthree.dto.post.PostExamDTO;
import project.tuthree.dto.post.PostExamDTO.ProblemDTO;
import project.tuthree.repository.UserFileRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static project.tuthree.repository.UserFileRepository.FileType.POSTPAPER;

@Repository
@RequiredArgsConstructor
public class ArrayToJson {

    private final UserFileRepository userFileRepository;

    public String toJson() throws IOException, NoSuchAlgorithmException {


        List<ProblemDTO> list = new ArrayList<>();
        ProblemDTO dto = new ProblemDTO(1L, "num", "5", false);
        ProblemDTO dto2 = new ProblemDTO(1L, "num", "5", false);
        list.add(dto);
        list.add(dto2);

        PostExamDTO postExamDTO = new PostExamDTO(1L, list);

        return userFileRepository.saveJsonFile(postExamDTO, "post_answer.json", POSTPAPER);
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

}
