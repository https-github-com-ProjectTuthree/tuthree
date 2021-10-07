package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.user.*;
import project.tuthree.dto.user.*;
import project.tuthree.repository.UserEntityRepository;
import project.tuthree.repository.UserFileRepository;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static project.tuthree.repository.UserFileRepository.FileType.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final UserEntityRepository userEntityRepository;
    private final UserFileRepository userFileRepository;
    private final PostFindService postFindService;



    /** 아이디 중복 확인 */
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
        //throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    public void validId(String id){
        boolean parent = userRepository.existsById(id);
        boolean student = studentRepository.existsById(id);
        boolean teacher = teacherRepository.existsById(id);

        if(!parent&&!student&&!teacher){
        }
        else{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /** 로그인 */
    public String userLogin(LoginDTO loginDTO) {
        String id = loginDTO.getId();
        String pwd = loginDTO.getPwd();
        String str = userEntityRepository.studentFindByIdPwd(id, pwd);
        if(str.equals(" ")){
            str = userEntityRepository.teacherFindByIdPwd(id, pwd);
            if(str.equals(" ")){
                str = userEntityRepository.parentFindByIdPwd(id, pwd);
                if(str.equals(" ")){
                    return " ";
                }
            }
        }
        return str;

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
//로그인
//    public Optional<User> userLogin(String id){
//        return userRepository.findById(id);
//    }



    //학부모 회원가입
    @Transactional
    public String createParent(UserRegisterDTO registerDTO){
        try{
            if(!checkId(registerDTO.getId())){
                return userRepository.save(registerDTO.toEntity()).getId();
            }
            else{
                return "중복";
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
                /** 마이페이지 이미지 수정 기능이 만들고 나서 빼기 */
                if (!registerDTO.getFile().isEmpty()) {
                    String post = userFileRepository.saveFile(registerDTO.getFile(), STUDENT);
                    registerDTO.updatePost(post);
                }
                String id = studentRepository.save(registerDTO.toEntity()).getId();
                postFindService.studentPostFindRegister(id);
                return id;
            }
            else{
                return "중복";
            }

        }catch(Exception e){
            throw new RuntimeException();
        }
    }

    //선생님 회원가입
    @Transactional
    public String createTeacher(TeacherRegisterDTO registerDTO) {
        try{
            if(!checkId(registerDTO.getId())){
                /** 마이페이지 이미지 수정 기능이 만들고 나서 빼기 */
                if (!registerDTO.getFile().isEmpty()) {
                    String post = userFileRepository.saveFile(registerDTO.getFile(), TEACHER);
                    registerDTO.updatePost(post);
                }
                if (!registerDTO.getAuthFile().isEmpty()) {
                    String certification = userFileRepository.saveFile(registerDTO.getAuthFile(), TEACHER);
                    registerDTO.updateAuthPost(certification);
                }
                String id = teacherRepository.save(registerDTO.toEntity()).getId();
                postFindService.teacherPostFindRegister(id);
                return id;

            }
            else{
                return "중복";
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

    /*@Transactional
    public String userUpdate(String id, StudentResponseDTO requestDto){
        Student student = studentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));

        student.update(requestDto.getName(),requestDto.getEmail(), requestDto.getTel(), requestDto.getSex(), requestDto.getBirth(), requestDto.getPost(), requestDto.getNotification());

        return id;
    }*/
    //학생과외정보 수정
    @Transactional
    public String studentUpdate(String id, StudentUpdateDTO updateDTO){
        Student student = studentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));

        student.update(updateDTO.getRegion(),updateDTO.getRegistration(), updateDTO.getSubject(), updateDTO.getCost(), updateDTO.getSchool(), updateDTO.getDetail());

        return id;
    }
    //선생님과외정보 수정
    @Transactional
    public String teacherUpdate(String id, TeacherUpdateDTO updateDTO){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));

        teacher.update(updateDTO.getRegion(),updateDTO.getRegistration(), updateDTO.getSubject(), updateDTO.getCost(), updateDTO.getSchool(), updateDTO.getStatus(), updateDTO.getMajor(), updateDTO.getDetail());

        return id;
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
