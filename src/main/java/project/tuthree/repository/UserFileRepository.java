package project.tuthree.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.domain.file.UserFile;
import project.tuthree.dto.user.StudentRegisterDTO;
import project.tuthree.dto.user.TeacherRegisterDTO;
import project.tuthree.dto.user.UserRegisterDTO;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static project.tuthree.domain.file.QUserFile.userFile;

@Getter
@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class UserFileRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;
    private final ObjectMapper objectMapper;

    @Getter
    public enum FileType{
        PARENT("/parent"),
        STUDENT("/student"),
        TEACHER("/teacher"),
        POSTPAPER("/post");

        private String kortype;

        FileType(String kortype) {
            this.kortype = kortype;
        }
    }

    /** 시험지 답안지 등 모든 파일 저장 */
    public Long userFileSave(UserFile userFile) {
        em.persist(userFile);
        return userFile.getId();
    }


    /** 파일 삭제  */
    public Long deleteUserFile(Long fileId) {
        em.remove(em.find(UserFile.class, fileId));
        return fileId;
    }

    /** 파일 찾기 */
    public UserFile userFileFindById(Long id) {
        return em.find(UserFile.class, id);
    }

    /**
     * post_id로 파일 찾기
     */
    public List<UserFile> userFileFindByPostId(Long id) {
        List<UserFile> fileList = em.createQuery("select f from UserFile f where f.testpaperId.id = :id")
                .setParameter("id", id)
                .getResultList();
        return fileList;
    }

    public String saveJsonFile(Object object, String real, FileType type) throws NoSuchAlgorithmException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        String savePath = "/home/seojaehui/tuthree/var" + type.getKortype();

        if (!new File(savePath).exists()) {
            new File(savePath).mkdir();
        }

        String saveName = saveFileNameToHash(real + new Date());
        savePath += "/" + saveName;

        mapper.writeValue(new File(savePath), object);

        return savePath;
    }

    /** multipartfile을 저장하는 로직 */
    public String saveFile(MultipartFile file, FileType type) throws NoSuchAlgorithmException, IOException {

        String savePath = "/home/seojaehui/tuthree/var" + type.getKortype();

        if (!new File(savePath).exists()) {
            new File(savePath).mkdir();
        }

        String originName = file.getOriginalFilename();
        String saveName = saveFileNameToHash(originName + new Date());
        savePath += "/" + saveName;
        file.transferTo(new File(savePath));

        return savePath;
    }

    /** 파일을 서버에 저장 */
    public List<String> saveFile(List<MultipartFile> files, FileType type) throws NoSuchAlgorithmException, IOException {
        List<String> saveNames = new ArrayList<>();
        String savePath = "/home/seojaehui/tuthree/var" + type.getKortype();

        if (!new File(savePath).exists()) {
            new File(savePath).mkdir();
        }

        for(int i=0; i<files.size(); i++){
            String originName = "";
            String saveName = "";
            String saveFilePath = "";

            originName = files.get(i).getOriginalFilename();
            saveName = saveFileNameToHash(originName + new Date());

            saveFilePath += savePath + "/" + saveName;

            files.get(i).transferTo(new File(saveFilePath));
            saveNames.add(saveFilePath);
        }
        return saveNames;
    }

    /** 파일 저장을 위한 md5 hash 생성 */
    public String saveFileNameToHash(String filename) throws NoSuchAlgorithmException {
        /**
         * 이름이 똑같으면 hash 값이 똑같이 나온다.
         * 확장자를 빼내고 파일 이름만 해싱한다.
         * 이 때 파일 이름을 filename + datetime으로 한다.
         */
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(filename.getBytes(StandardCharsets.UTF_8));

        byte[] mdHash = md.digest();
        StringBuilder hashBuilder = new StringBuilder();
        for(byte b : mdHash) {
            String hexString = String.format("%02x", b);
            hashBuilder.append(hexString);
        }
        return hashBuilder.toString();
    }




    /** 파일 다운로드 */
    public void downloadUserFile(HttpServletResponse response, Long post_id) throws IOException {

        String origin = (String) em.createQuery("select f.realTitle from UserFile f where f.id = :id")
                .setParameter("id", post_id)
                .getSingleResult();

        String path = (String) em.createQuery("select f.saveTitle from UserFile f where f.id = :id")
                .setParameter("id", post_id)
                .getSingleResult();

        byte[] fileByte = FileUtils.readFileToByteArray(new File(path));

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; fileName=" + URLEncoder.encode(origin, "UTF-8") + ";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /** file_id 파일 전송 - byte */
    public byte[] transferUserFile(Long file_id) throws IOException {
        try {
            String path = em.find(UserFile.class, file_id).getSaveTitle();
            FileInputStream in = new FileInputStream(new File(path));
            return IOUtils.toByteArray(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** file_path 파일 전송 - byte */
    public byte[] transferUserFile(String path) throws IOException {
        try {
            FileInputStream in = new FileInputStream(new File(path));
            return IOUtils.toByteArray(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** unix time -> Date */
    public String unixToDate(Date unix) throws ParseException {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        return transFormat.format(unix);
    }
//
//    /** file_id 파일 전송 - byte */
//    public byte[] transferUserFile(Long file_id) throws IOException {
//        try {
//            String path = em.find(UserFile.class, file_id).getSaveTitle();
//            FileInputStream in = new FileInputStream(new File(path));
//            return IOUtils.toByteArray(in);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


//    public void jsonPParse(UserRegisterDTO registerDTO){
//        String savePath = "/home/seojaehui/tuthree/var/parent";
//
//        if (!new File(savePath).exists()) {
//            new File(savePath).mkdir();
//        }
//        //String savePath = "C:/Users/LG/Desktop/새 폴더 (2)/";
//        String jFileName=savePath+"/"+registerDTO.getId()+".json";
//
//        //TeacherRegisterDTO teacherDTO;
//        JSONObject obj = new JSONObject();
//        obj.put("id", registerDTO.getId());
//        obj.put("pwd", registerDTO.getPwd());
//        obj.put("name", registerDTO.getName());
//        obj.put("email", registerDTO.getEmail());
//        obj.put("tel", registerDTO.getTel());
//        obj.put("sex", (registerDTO.getSex()).toString());
//        obj.put("birth", registerDTO.getBirth());
//        obj.put("grade", (registerDTO.getGrade()).toString());
//        obj.put("post", registerDTO.getPost());
//        obj.put("file", registerDTO.getFile());
//
//        try {
//            FileWriter file = new FileWriter(jFileName);
//            file.write(obj.toJSONString());
//            file.flush();
//            file.close();
//
//
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//    //선생님 json
//    public void jsonTParse(TeacherRegisterDTO registerDTO){
//        String savePath = "/home/seojaehui/tuthree/var/teacher";
//
//        if (!new File(savePath).exists()) {
//            new File(savePath).mkdir();
//        }
//        String jFileName=savePath+"/"+registerDTO.getId()+".json";
//
//        //TeacherRegisterDTO teacherDTO;
//        JSONObject obj = new JSONObject();
//        obj.put("id", registerDTO.getId());
//        obj.put("pwd", registerDTO.getPwd());
//        obj.put("name", registerDTO.getName());
//        obj.put("email", registerDTO.getEmail());
//        obj.put("tel", registerDTO.getTel());
//        obj.put("sex", (registerDTO.getSex()).toString());
//        obj.put("birth", registerDTO.getBirth());
//        obj.put("post", registerDTO.getPost());
//        obj.put("grade", (registerDTO.getGrade()).toString());
//
//        JSONArray listRegion = new JSONArray();
//        String region= registerDTO.getRegion();
//        String[] regionA = region.split(",");
//        for(int i=0; i<regionA.length; i++){
//            JSONObject tmpR = new JSONObject();
//            tmpR.put("region", regionA[i]);
//            listRegion.add(tmpR);
//        }
//        obj.put("region", listRegion);
//        obj.put("registeration", (registerDTO.getRegistration()).toString());
//
//        JSONArray listSubject = new JSONArray();
//
//        String subject= registerDTO.getSubject();
//        String[] subjectA = subject.split(",");
//        for(int i=0; i<subjectA.length; i++){
//            JSONObject tmpS = new JSONObject();
//            tmpS.put("subject", subjectA[i]);
//            listSubject.add(tmpS);
//        }
//        obj.put("subject", listSubject);
//        obj.put("cost", registerDTO.getCost());
//        obj.put("school", registerDTO.getSchool());
//        obj.put("major", registerDTO.getMajor());
//        obj.put("detail", registerDTO.getDetail());
//        obj.put("certification", registerDTO.getCertification());
//        obj.put("file", registerDTO.getFile());
//        obj.put("authFile", registerDTO.getAuthFile());
//
//        try {
//            FileWriter file = new FileWriter(jFileName);
//            file.write(obj.toJSONString());
//            file.flush();
//            file.close();
//
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //학생 json
//    public void jsonSParse(StudentRegisterDTO registerDTO){
//        String savePath = "/home/seojaehui/tuthree/var/student";
//
//        if (!new File(savePath).exists()) {
//            new File(savePath).mkdir();
//        }
//        String jFileName=savePath+"/"+registerDTO.getId()+".json";
//
//        //TeacherRegisterDTO teacherDTO;
//        JSONObject obj = new JSONObject();
//        obj.put("id", registerDTO.getId());
//        obj.put("pwd", registerDTO.getPwd());
//        obj.put("name", registerDTO.getName());
//        obj.put("email", registerDTO.getEmail());
//        obj.put("tel", registerDTO.getTel());
//        obj.put("sex", registerDTO.getSex());
//        obj.put("birth", registerDTO.getBirth());
//        obj.put("grade", (registerDTO.getGrade()).toString());
//        obj.put("post", registerDTO.getPost());
//
//        JSONArray listRegion = new JSONArray();
//        String region= registerDTO.getRegion();
//        String[] regionA = region.split(",");
//        for(int i=0; i<regionA.length; i++){
//            JSONObject tmpR = new JSONObject();
//            tmpR.put("region", regionA[i]);
//            listRegion.add(tmpR);
//        }
//        obj.put("region", listRegion);
//        obj.put("registeration", (registerDTO.getRegistration()).toString());
//
//        JSONArray listSubject = new JSONArray();
//        String subject= registerDTO.getSubject();
//        String[] subjectA = subject.split(",");
//        for(int i=0; i<subjectA.length; i++){
//            JSONObject tmpS = new JSONObject();
//            tmpS.put("subject", subjectA[i]);
//            listSubject.add(tmpS);
//        }
//        obj.put("subject", listSubject);
//        obj.put("cost", registerDTO.getCost());
//        obj.put("school", (registerDTO.getSchool()).toString());
//        obj.put("detail", registerDTO.getDetail());
//        obj.put("file", registerDTO.getFile());
//
//        try {
//            FileWriter file = new FileWriter(jFileName);
//            file.write(obj.toJSONString());
//            file.flush();
//            file.close();
//
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        }
//
//    }

}
