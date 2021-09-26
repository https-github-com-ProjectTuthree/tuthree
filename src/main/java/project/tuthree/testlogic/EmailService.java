package project.tuthree.testlogic;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

import java.util.Random;

//@ResponseBody
@Controller
@AllArgsConstructor
public class EmailService {

    private JavaMailSender mailsender;
    //    private MimeMessage msg;


    public String mailSending() {

        SimpleMailMessage msg = new SimpleMailMessage();

        String from = "project.tuthree@gmail.com";

        msg.setFrom(from);
        System.out.println("from = " + from);

        Random rand = new Random();

        String numStr = "";
        for (int i = 0; i < 4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }

        msg.setTo("wogmlsu@gmail.com");
        msg.setSubject("spring test mail");
        msg.setText("인증번호 : " + numStr);
        try {
            mailsender.send(msg);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        /**
//        String setfrom = "wkdskdus@gamil.com";
//        String title = "테스트 인증번호."; // 제목
//        String content =
//
//                System.getProperty("line.separator")+ //한줄씩 줄간격을 두기위해 작성
//
//
//
//                        System.getProperty("line.separator")+
//
//                        System.getProperty("line.separator")+
//
//                        " 인증번호는 " +numStr+ " 입니다. ";

//
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper messageHelper = new MimeMessageHelper(message,
//                    true, "UTF-8");
//
//            messageHelper.setFrom(setfrom); // 보내는사람 생략하면 정상작동을 안함
//            messageHelper.setTo(e_mail); // 받는사람 이메일
//            messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
//            messageHelper.setText(content); // 메일 내용
//
//            mailSender.send(message);
//
//            System.out.println(e_mail);
//        } catch (Exception e) {
//            System.out.println(e);
//        }

        return numStr;
    }
         */
        return numStr;
    }

//
//    public static void main(String[] args) {
//        EmailService em = new EmailService(new MailConfig().javaMailService());
//        em.mailSending();
//    }
}
