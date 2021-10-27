package project.tuthree.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.*;
import project.tuthree.dto.user.*;
import project.tuthree.repository.AdminRepository;
import project.tuthree.repository.UserEntityRepository;
import project.tuthree.repository.UserFileRepository;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
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
    private final ChildRepository childRepository;
    private final AdminRepository adminRepository;

    private final PostFindService postFindService;

    /**
     * 아이디 중복 확인
     */
    public boolean checkId(String id) {
        //아이디 중복이면 true, 아니면 false
        boolean parent = userRepository.existsById(id);
        boolean student = studentRepository.existsById(id);
        boolean teacher = teacherRepository.existsById(id);
        Long admin = adminRepository.existById(id);

        boolean result = TRUE;

        //중복이 아니면
        if (parent == FALSE) {
            if (student == FALSE) {
                if (teacher == FALSE) {
                    if (admin == 0L)
                        result = FALSE;
                    return result;

                }
            }
        } else {
            result = TRUE;
        }
        return result;
    }

    public void validId(String id) {
        boolean parent = userRepository.existsById(id);
        boolean student = studentRepository.existsById(id);
        boolean teacher = teacherRepository.existsById(id);

        if (!parent && !student && !teacher) {
        } else {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 로그인
     */
    public String userLogin(LoginDTO loginDTO) {
        String id = loginDTO.getId();
        String pwd = loginDTO.getPwd();
        String str = userEntityRepository.studentFindByIdPwd(id, pwd);
        if (!str.equals(" ")) {
            Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
            if (student.getUserDel() == Status.CLOSE) {
                str = " ";
            }
        }
        if (str.equals(" ")) {
            str = userEntityRepository.teacherFindByIdPwd(id, pwd);
            if (!str.equals(" ")) {
                Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
                if (teacher.getUserDel() == Status.CLOSE) {
                    str = " ";
                }
            }
            if (str.equals(" ")) {
                str = userEntityRepository.parentFindByIdPwd(id, pwd);
                if (!str.equals(" ")) {
                    User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
                    if (user.getUserDel() == Status.CLOSE) {
                        str = " ";
                    }
                }
                if (str.equals(" ")) {
                    return " ";
                }
            }
        }
        return str;
    }

    /**
     * 학부모 회원가입
     **/
    @Transactional
    public String createParent(UserRegisterDTO registerDTO) {
        try {
            if (!checkId(registerDTO.getId())) {

                if (!registerDTO.getFile().isEmpty()) {
                    String post = userFileRepository.saveFile(registerDTO.getFile(), PARENT);
                    registerDTO.updatePost(post);
                }
                return userRepository.save(registerDTO.toEntity()).getId();
            } else {
                return "중복";
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 학생 회원가입
     **/
    @Transactional
    public String createStudent(StudentRegisterDTO registerDTO) {
        try {
            if (!checkId(registerDTO.getId())) {
                /** 마이페이지 이미지 수정 기능이 만들고 나서 빼기 */
                if (!registerDTO.getFile().isEmpty()) {
                    String post = userFileRepository.saveFile(registerDTO.getFile(), STUDENT);
                    registerDTO.updatePost(post);
                }
                String id = studentRepository.save(registerDTO.toEntity()).getId();
                userEntityRepository.userSaveSubject(registerDTO.getId(), registerDTO.getSubjectL());
                userEntityRepository.userSaveRegion(registerDTO.getId(), registerDTO.getRegionL());
                postFindService.studentPostFindRegister(id);
                return id;
            } else {
                return "중복";
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 선생님 회원가입
     **/
    @Transactional
    public String createTeacher(TeacherRegisterDTO registerDTO) {
        try {
            if (!checkId(registerDTO.getId())) {
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
                userEntityRepository.userSaveRegion(registerDTO.getId(), registerDTO.getRegionL());
                userEntityRepository.userSaveSubject(registerDTO.getId(), registerDTO.getSubjectL());
                postFindService.teacherPostFindRegister(id);
                return id;

            } else {
                return "중복";
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    /**
     * 기본정보 조회
     **/
    @Transactional(readOnly = true)
    public UserResponseDTO findByInfo(String id, String grade) throws IOException {
        if (Objects.equals(grade, "parent")) {
            User entity = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

            byte[] file = userFileRepository.transferUserFile(entity.getPost());
            return new UserResponseDTO(entity, file);
        } else if (Objects.equals(grade, "teacher")) {
            Teacher entity = teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

            byte[] file = userFileRepository.transferUserFile(entity.getPost());
            return new UserResponseDTO(entity, file);
        } else if (Objects.equals(grade, "student")) {
            Student entity = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id + grade));
            byte[] file = userFileRepository.transferUserFile(entity.getPost());
            return new UserResponseDTO(entity, file);
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * 튜터정보조회
     **/
    @Transactional(readOnly = true)
    public TeacherResponseDTO findTutorId(String id) throws IOException {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
        List<String> region = userEntityRepository.userFindRegion(id);
        List<String> subject = userEntityRepository.userFindSubject(id);
        byte[] file = userFileRepository.transferUserFile(teacher.getCertification());
        return new TeacherResponseDTO(teacher, region, subject, file);
    }

    /**
     * 튜티정보조회
     **/
    @Transactional(readOnly = true)
    public StudentResponseDTO findTuTeeId(String id) {
        Student entity = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
        List<String> region = userEntityRepository.userFindRegion(id);
        List<String> subject = userEntityRepository.userFindSubject(id);
        return new StudentResponseDTO(entity, region, subject);
    }

    /**
     * 개인정보수정
     **/
    @Transactional
    public String userUpdate(String id, String grade, UserUpdateDTO updateDto) throws NoSuchAlgorithmException, IOException {
        if (Objects.equals(grade, "parent")) {
            if (!updateDto.getFile().isEmpty()) {
                String post = userFileRepository.saveFile(updateDto.getFile(), PARENT);
                updateDto.updatePost(post);
            }
            User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

            user.updateInfo(updateDto.getEmail(), updateDto.getTel(), updateDto.getBirth(), updateDto.getPost(), updateDto.getNotification());

        } else if (Objects.equals(grade, "teacher")) {
            if (!updateDto.getFile().isEmpty()) {
                String post = userFileRepository.saveFile(updateDto.getFile(), TEACHER);
                updateDto.updatePost(post);
            }
            Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

            teacher.updateInfo(updateDto.getEmail(), updateDto.getTel(), updateDto.getBirth(), updateDto.getPost(), updateDto.getNotification());

        } else if (Objects.equals(grade, "student")) {
            if (!updateDto.getFile().isEmpty()) {
                String post = userFileRepository.saveFile(updateDto.getFile(), STUDENT);
                updateDto.updatePost(post);
            }
            Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id + grade));

            student.updateInfo(updateDto.getEmail(), updateDto.getTel(), updateDto.getBirth(), updateDto.getPost(), updateDto.getNotification());
        } else {
            throw new NullPointerException();
        }


        return id;
    }

    /**
     * 학생과외정보 수정
     **/
    @Transactional
    public String studentUpdate(String id, StudentUpdateDTO updateDTO) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        student.update(updateDTO.getRegistration(), updateDTO.getCost(), updateDTO.getSchool(), updateDTO.getDetail());

        userEntityRepository.userSaveRegion(updateDTO.getId(), updateDTO.getRegionL());
        userEntityRepository.userSaveSubject(updateDTO.getId(), updateDTO.getSubjectL());

        return id;
    }

    /**
     * 선생님과외정보 수정
     **/
    @Transactional
    public String teacherUpdate(String id, TeacherUpdateDTO updateDTO) throws IOException, NoSuchAlgorithmException {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        if (!updateDTO.getAuthFile().isEmpty()) {
            String certification = userFileRepository.saveFile(updateDTO.getAuthFile(), TEACHER);
            updateDTO.updateAuthPost(certification);
        }

        teacher.update(updateDTO.getRegistration(), updateDTO.getCost(), updateDTO.getSchool(), updateDTO.getStatus(), updateDTO.getMajor(), updateDTO.getDetail(), updateDTO.getCertification());

        userEntityRepository.userSaveRegion(updateDTO.getId(), updateDTO.getRegionL());
        userEntityRepository.userSaveSubject(updateDTO.getId(), updateDTO.getSubjectL());

        return id;
    }

    /**
     * 아이디 찾기
     **/
    @Transactional
    public String findId(FindIdDTO findIdDTO, String method) {
        String name = findIdDTO.getName();
        String tel = findIdDTO.getTel();
        String email = findIdDTO.getEmail();

        if (Objects.equals(method, "email")) {
            Optional<User> userC = userRepository.findByNameAndEmail(name, email);
            if (userC.isPresent()) {
                User user = userC.get();
                if (user.getUserDel() != Status.CLOSE) {
                    return user.getId();
                }
                else{
                    return " ";
                }
            } else {
                Optional<Student> studentC = studentRepository.findByNameAndEmail(name, email);
                if (studentC.isPresent()) {
                    Student student = studentC.get();
                    if (student.getUserDel() != Status.CLOSE) {
                        return student.getId();
                    }
                    else{
                        return " ";
                    }
                } else {
                    Optional<Teacher> teacherC = teacherRepository.findByNameAndEmail(name, email);
                    if (teacherC.isPresent()) {
                        Teacher teacher = teacherC.get();
                        if (teacher.getUserDel() != Status.CLOSE) {
                            return teacher.getId();
                        }
                    } else {
                        return " ";
                    }
                }
            }
        }
        else if (Objects.equals(method, "tel")) {
            Optional<User> userC = userRepository.findByNameAndTel(name, tel);
            if (userC.isPresent()) {
                User user = userC.get();
                if (user.getUserDel() != Status.CLOSE) {
                    return user.getId();
                }
            } else {
                Optional<Student> studentC = studentRepository.findByNameAndTel(name, tel);
                if (studentC.isPresent()) {
                    Student student = studentC.get();
                    if (student.getUserDel() != Status.CLOSE) {
                        return student.getId();
                    }
                } else {
                    Optional<Teacher> teacherC = teacherRepository.findByNameAndTel(name, tel);
                    if (teacherC.isPresent()) {
                        Teacher teacher = teacherC.get();
                        if (teacher.getUserDel() != Status.CLOSE) {
                            return teacher.getId();
                        }
                    } else {
                        return " ";
                    }
                }
            }
        }
        else{
            return "not method";
        }
        return " ";
    }

    /**비밀번호 찾기**/
    @Transactional
    public String findPwd(FindIdDTO findIdDTO, String method){
        String id = findIdDTO.getId();
        String name = findIdDTO.getName();
        String tel = findIdDTO.getTel();
        String email = findIdDTO.getEmail();

        if (Objects.equals(method, "email")) {
            Optional<User> userC = userRepository.findByIdAndNameAndEmail(id, name, email);
            if (userC.isPresent()) {
                User user = userC.get();
                if (user.getUserDel() != Status.CLOSE) {
                    return user.getGrade().getStrType();
                }
            } else {
                Optional<Student> studentC = studentRepository.findByIdAndNameAndEmail(id, name, email);
                if (studentC.isPresent()) {
                    Student student = studentC.get();
                    if (student.getUserDel() != Status.CLOSE) {
                        return student.getGrade().getStrType();
                    }
                } else {
                    Optional<Teacher> teacherC = teacherRepository.findByIdAndNameAndEmail(id, name, email);
                    if (teacherC.isPresent()) {
                        Teacher teacher = teacherC.get();
                        if (teacher.getUserDel() != Status.CLOSE) {
                            return teacher.getGrade().getStrType();
                        }
                    } else {
                        return " ";
                    }
                }
            }
        }
        else if (Objects.equals(method, "tel")) {
            Optional<User> userC = userRepository.findByIdAndNameAndTel(id, name, tel);
            if (userC.isPresent()) {
                User user = userC.get();
                if (user.getUserDel() != Status.CLOSE) {
                    return user.getGrade().getStrType();
                }
            } else {
                Optional<Student> studentC = studentRepository.findByIdAndNameAndTel(id, name, tel);
                if (studentC.isPresent()) {
                    Student student = studentC.get();
                    if (student.getUserDel() != Status.CLOSE) {
                        return student.getGrade().getStrType();
                    }
                } else {
                    Optional<Teacher> teacherC = teacherRepository.findByIdAndNameAndTel(id, name, tel);
                    if (teacherC.isPresent()) {
                        Teacher teacher = teacherC.get();
                        if (teacher.getUserDel() != Status.CLOSE) {
                            return teacher.getGrade().getStrType();
                        }
                    } else {
                        return " ";
                    }
                }
            }
        }
        else{
            return "not method";
        }
        return " ";
    }


    /**비밀번호 변경**/
    @Transactional
    public void changePwd(ChangePwdDTO pwdDTO, String id, String grade){

        if(Objects.equals(grade, "parent")){
            User user = userRepository.findById(id).orElseThrow(() ->  new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));

            user.updateP(pwdDTO.getPwd());
        }
        else if(Objects.equals(grade, "teacher")) {
            Teacher teacher = teacherRepository.findById(id).orElseThrow(() ->  new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));

            teacher.updateP(pwdDTO.getPwd());
        }
        else {
            Student student = studentRepository.findById(id).orElseThrow(() ->  new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));

            student.updateP(pwdDTO.getPwd());
        }
    }

    /**자녀 추가**/
    @Transactional
    public String plusChild(String parentId, ChildDTO childDTO){
        String studentId= childDTO.getStudentId();
        String StudentName = childDTO.getStudentName();
        String id = studentRepository.findByIdAndName(studentId, StudentName).getId();
        childDTO.setParentId(parentId);
        childDTO.setStatus(false);
        if(id.equals(" ")) {
            return "확인되지 않은 id";
        }
        else{
            return childRepository.save(childDTO.toEntity()).getStudentId();
        }
    }

    /**자녀수락**/
    @Transactional
    public String acceptChild(String parentId, String studentId){
        Child child = childRepository.findByParentIdAndStudentId(parentId, studentId);
        child.accept();
        User user = userRepository.findById(parentId).orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다. id="+ parentId));
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다. id="+ studentId));;
        student.accept(user);
        return child.getParentId();
    }

    /**요청보기**/
    @Transactional
    public ChildDTO checkChild(String studentId){
        Long id = childRepository.findByStudentId(studentId).getId();
        Child child = childRepository.findById(id).orElseThrow(() ->  new IllegalArgumentException("해당 내용이 없습니다. id="+ id));
        return new ChildDTO(child.getParentId(), child.getStudentId(), child.getStudentName(), child.isStatus());
    }

    /**회원 탈퇴**/ //그냥 회원상태인지 아닌상태인지를 만드는게 더 날듯
    @Transactional
    public String quitUser(String id, String grade){
        if(Objects.equals(grade, "parent")){
            User user = userRepository.findById(id).orElseThrow(() ->  new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));
            user.userDel();
            return user.getUserDel().toString();
        }
        else if(Objects.equals(grade, "teacher")) {
            Teacher teacher = teacherRepository.findById(id).orElseThrow(() ->  new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));
            teacher.userDel();
            return teacher.getUserDel().toString();
        }
        else if(Objects.equals(grade, "student")){
            Student student = studentRepository.findById(id).orElseThrow(() ->  new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));
            student.userDel();
            return student.getUserDel().toString();
        }
        else{
            throw new NullPointerException();
        }
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
