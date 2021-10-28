package project.tuthree.ApiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import project.tuthree.ApiController.EmbeddedResponse.NotExistBadDataResultResponse;
import project.tuthree.ApiController.EmbeddedResponse.NotExistDataResultResponse;
import project.tuthree.configuration.Utils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailApiController {

    private final JavaMailSender mailSender;

    /** 이메일 보내기 **/
    @GetMapping("/auth-email")
    public Object mailSending(@RequestParam("mail") String email) {
        HashMap<String, Object> result = new HashMap<>();

        if (emailCheck(email) == 1)
            return new NotExistBadDataResultResponse(StatusCode.CONFLICT.getCode(), "이메일 형식이 아닙니다.");

        Random rand = new Random();
        String numStr = "";
        for (int i = 0; i < 4; i++) {
            numStr += Integer.toString(rand.nextInt(10));
        }

        String title = "TuThree 과외 플랫폼 회원가입 인증"; // 제목
        String content = System.getProperty("line.separator") +
                "Tuthree 과외 플랫폼 회원가입을 위한 인증번호입니다." +
                System.getProperty("line.separator") +
                "아래의 4자리 숫자 를 인증 번호 확인란에 입력해주세요." +
                System.getProperty("line.separator") +
                numStr +
                System.getProperty("line.separator");
        //한줄씩 줄간격을 두기위해 작성

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true, "UTF-8");

            messageHelper.setFrom(Utils.emailSender); // 보내는사람 생략하면 정상작동을 안함
            messageHelper.setTo(email); // 받는사람 이메일
            messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
            messageHelper.setText(content); // 메일 내용

            mailSender.send(message);

            return new NotExistDataResultResponse(StatusCode.CREATED.getCode(), numStr);
        } catch (Exception e) {
            e.printStackTrace();
            return new NotExistBadDataResultResponse(StatusCode.NO_CONTENT.getCode(), "인증 번호 발급에 실패했습니다.");
        }
    }

    public int emailCheck(String e_mail) {
        if (e_mail.contains("@")) {
            if (e_mail.split("@")[1].contains(".")) {
                return 0;
            }
        } return 1;
    }}


