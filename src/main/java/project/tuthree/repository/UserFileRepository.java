package project.tuthree.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import project.tuthree.domain.file.UserFile;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Repository
@Slf4j
@RequiredArgsConstructor
public class UserFileRepository {

    private final EntityManager em;

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

    public void userFileSave(UserFile userFile) {
        em.persist(userFile);
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

        return saveName;
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

    /**
     * 파일 삭제
     */
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

}
