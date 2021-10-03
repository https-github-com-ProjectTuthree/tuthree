package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.user.*;
import project.tuthree.dto.*;

import javax.persistence.EntityManager;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    //학부모 회원가입
    @Transactional
    public String createParent(UserRegisterDTO registerDTO){
        //User user = registerDTO.toEntity();
        //user.setId("id");
        //EntityManager.persist(user);
        return userRepository.save(registerDTO.toEntity()).getId();
    }

    //학생 회원가입
    @Transactional
    public String createStudent(StudentRegisterDTO registerDTO){
        return studentRepository.save(registerDTO.toEntity()).getId();
    }

    //선생님 회원가입
    @Transactional
    public String createTeacher(TeacherRegisterDTO registerDTO){
        return teacherRepository.save(registerDTO.toEntity()).getId();
    }

    //아이디 중복확인
    public boolean checkId(String id){
        if(!userRepository.existsById(id)||!studentRepository.existsById(id)||!teacherRepository.existsById(id)){
            return true;
        }
        else{
            return false;
        }
    }

    //기본정보 조회
    @Transactional(readOnly = true)
    public UserResponseDTO findByInfo (String id, Grade grade){
        if(grade == grade.PARENT){
            User entity = userRepository.findById(id).orElseThrow(() ->  new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));
            return new UserResponseDTO(entity);
        }
        else if(grade == grade.TEACHER) {
            Teacher entity = teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
            return new UserResponseDTO(entity);
        }
        else {
            Student entity = studentRepository.findById(id).orElseThrow(() ->  new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));
            return new UserResponseDTO(entity);
        }
    }

    //튜터정보조회
    @Transactional(readOnly = true)
    public TeacherResponseDTO findTutorId (String id){
        Teacher entity = teacherRepository.findById(id).orElseThrow(() ->  new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));
        return new TeacherResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO findTuTeeId (String id){
        Student entity = studentRepository.findById(id).orElseThrow(() ->  new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));
        return new StudentResponseDTO(entity);
    }



/*    //아이디 중복 체크
    public boolean checkId(String id){
        return userRepository.existsById(id);
    }*/


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
