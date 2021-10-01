package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.user.StudentRepository;
import project.tuthree.domain.user.TeacherRepository;
import project.tuthree.domain.user.UserRepository;
import project.tuthree.dto.UserRegisterDTO;
import project.tuthree.dto.StudentRegisterDTO;
import project.tuthree.dto.TeacherRegisterDTO;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Transactional
    public String createParent(UserRegisterDTO registerDTO){
        return userRepository.save(registerDTO.toEntity()).getId();
    }

    @Transactional
    public String createStudent(StudentRegisterDTO registerDTO){
        return studentRepository.save(registerDTO.toEntity()).getId();
    }

    @Transactional
    public String createTeacher(TeacherRegisterDTO registerDTO){
        return teacherRepository.save(registerDTO.toEntity()).getId();
    }

    public boolean checkId(String id){
        return userRepository.existsById(id);
    }

/*    public String makeSignature(Long time) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {
        String space = " "; // one space
        String newLine = "\n"; // new line
        String method = "POST"; // method
        String url = "/sms/v2/services/"+applicationNaverSENS.getServiceid()+"/messages"; // url (include query string)
        String timestamp = time.toString(); // current timestamp (epoch)
        String accessKey = applicationNaverSENS.getAccesskey(); // access key id (from portal or Sub Account)
        String secretKey = applicationNaverSENS.getSecretkey();
        String message = new StringBuilder() .append(method) .append(space) .append(url) .append(newLine) .append(timestamp) .append(newLine) .append(accessKey) .toString();
        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);
        return encodeBase64String;
    }


    ncp:sms:kr:272525568257:sms_checkphone*/
}
