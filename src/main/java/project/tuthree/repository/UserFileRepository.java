package project.tuthree.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import project.tuthree.domain.file.QUserFile;
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
    public UserFile userFileFindByFileId(Long id) {
        return em.find(UserFile.class, id);
    }

    /** post_id로 파일 찾기 (커뮤니티에 등록된 파일 목록) */
    public List<UserFile> userFileFindByPostId(Long id) {
        return jpaQueryFactory.selectFrom(userFile)
                .where(userFile.testpaperId.id.eq(id))
                .fetch();
    }

    /** object -> json 서버 저장 */
    public String saveJsonFile(Object object, String real, FileType type) throws NoSuchAlgorithmException, IOException {
        String path = System.getProperty("user.dir");
        String savePath = path + "/src/main/resources/var" + type.getKortype();
        ObjectMapper mapper = new ObjectMapper();

        if (!new File(savePath).exists()) {
            new File(savePath).mkdir();
        }
        String saveName = saveFileNameToHash(real + new Date());
        savePath += "/" + saveName;

        mapper.writeValue(new File(savePath), object);
        return savePath;
    }

    /** json -> object */
    public Object changeJsonFile(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        FileInputStream in = new FileInputStream(new File(path));
        Object object = objectMapper.readValue(in, Object.class);
        return object;
    }

    /** multipartfile을 저장하는 로직 - 파일 하나 */
    public String saveFile(MultipartFile file, FileType type) throws NoSuchAlgorithmException, IOException {
        String path = System.getProperty("user.dir");
        String savePath = path + "/src/main/resources/var" + type.getKortype();
        if (!new File(savePath).exists()) {
            new File(savePath).mkdir();
        }
        String originName = file.getOriginalFilename();
        String saveName = saveFileNameToHash(originName + new Date());
        savePath += "/" + saveName;
        file.transferTo(new File(savePath));

        return savePath;
    }

    /** multipartfile을 저장하는 로직 - 파일 리스트 */
    public List<String> saveFile(List<MultipartFile> files, FileType type) throws NoSuchAlgorithmException, IOException {
        List<String> saveNames = new ArrayList<>();
        String path = System.getProperty("user.dir");
        String savePath = path + "/src/main/resources/var" + type.getKortype();

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

    public String unixToTimestamp(Date unix) throws ParseException {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return transFormat.format(unix);
    }

}
