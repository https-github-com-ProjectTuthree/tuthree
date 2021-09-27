//package project.tuthree.testlogic;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.stereotype.Repository;
//
//import java.util.Properties;
//
//@Repository
//@Slf4j
//public class MailConfig {
//
//    @Bean
//    public JavaMailSender javaMailService() {
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//
//        javaMailSender.setHost("myHost");
//        javaMailSender.setPort(25);
//
//        javaMailSender.setJavaMailProperties(getMailProperties());
//
//        return javaMailSender;
//    }
//
//    private Properties getMailProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("mail.transport.protocol", "smtp");
//        properties.setProperty("mail.smtp.auth", "false");
//        properties.setProperty("mail.smtp.starttls.enable", "false");
//        properties.setProperty("mail.debug", "false");
//        return properties;
//    }
//
//}
