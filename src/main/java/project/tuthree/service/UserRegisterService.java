package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.user.*;
import project.tuthree.dto.user.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;


    //아이디 중복확인
    public boolean checkId(String id){
        boolean parent = userRepository.existsById(id);
        boolean student = studentRepository.existsById(id);
        boolean teacher = teacherRepository.existsById(id);

        boolean result = TRUE;

        if(parent == FALSE) {
            if (student == FALSE) {
                if (teacher == FALSE) {
                    result = FALSE;
                    return result;
                }
            }            
        }else {
            result = TRUE;
        }
        return result;
    }

        /*if(!userRepository.existsById(id)||!studentRepository.existsById(id)||!teacherRepository.existsById(id)){
            return true;
        }
        else{
            return false;
        }*/
    
    /*    //아이디 중복 체크
    public boolean checkId(String id){
        return userRepository.existsById(id);
    }*/



    //학부모 회원가입
    @Transactional
    public String createParent(UserRegisterDTO registerDTO){
        //boolean result = checkId(registerDTO.getId());
        try{
            if(!checkId(registerDTO.getId())){
                return userRepository.save(registerDTO.toEntity()).getId();
            }
            else{
                return "redirect";
            }

        }catch(Exception e){
            throw new RuntimeException();
        }

        //return "redirect:/";

        //return userRepository.save(registerDTO.toEntity()).getId();
    }

    //학생 회원가입
    @Transactional
    public String createStudent(StudentRegisterDTO registerDTO){
        try{
            if(!checkId(registerDTO.getId())){
                return studentRepository.save(registerDTO.toEntity()).getId();
            }
            else{
                return "redirect";
            }

        }catch(Exception e){
            throw new RuntimeException();
        }
    }

    //선생님 회원가입
    @Transactional
    public String createTeacher(TeacherRegisterDTO registerDTO){
        try{
            if(!checkId(registerDTO.getId())){
                return teacherRepository.save(registerDTO.toEntity()).getId();
            }
            else{
                return "redirect";
            }

        }catch(Exception e){
            throw new RuntimeException();
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

    //튜티정보조회
    @Transactional(readOnly = true)
    public StudentResponseDTO findTuTeeId (String id){
        Student entity = studentRepository.findById(id).orElseThrow(() ->  new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));
        return new StudentResponseDTO(entity);
    }

/*    @Transactional
    public String update(String id, StudentResponseDTO requestDto){
        Student student = studentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));

        student.update(requestDto.getName(),requestDto.getEmail(), requestDto.getTel(), requestDto.getSex(), requestDto.getBirth(), requestDto.getPost(), requestDto.getNotification());

        return id;
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
