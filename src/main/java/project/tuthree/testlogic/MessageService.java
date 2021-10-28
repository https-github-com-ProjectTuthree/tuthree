package project.tuthree.testlogic;

import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.repository.UserFileRepository;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * https://coolsms.co.kr/
 * id : project.tuthree@gmail.com
 * pwd : tuthree1234!
 * https://developer.coolsms.co.kr/JAVA_SDK_Example
 */

@RestController
@RequiredArgsConstructor
public class MessageService {
    //private static final String api_key = "NCSGGOPN3Q9CASYW";
    //private static final String api_secret = "AE07APMXU9GEGAWSVO33BYX5FVXJ9BDI";

    private final UserFileRepository userFileRepository;

    public void sendMessage(String api_key, String api_secret) {
        Message coolsms = new Message(api_key, api_secret);

        int authNo = (int) (Math.random() * (99999 - 10000 + 1)) + 10000;
        System.out.println(authNo);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", "tonumber");
        params.put("from", "fromnumber");
        params.put("type", "SMS");
        params.put("text", "인증 번호 : " + authNo);
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }

}
