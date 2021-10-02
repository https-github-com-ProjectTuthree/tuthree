package project.tuthree.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Entity;
import org.w3c.dom.stylesheets.LinkStyle;
import project.tuthree.domain.file.UserFile;
import project.tuthree.dto.PostTestPaperDTO;
import project.tuthree.dto.UserfileDTO;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
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
            log.info("make directory\n\n\n\n");
        }
        log.info(savePath + "------\n\n\n");

        String originName = file.getOriginalFilename();
        String saveName = saveFileNameToHash(originName + new Date());
        savePath += "/" + saveName;
        file.transferTo(new File(savePath));

        return saveName;
    }

    public List<String> saveFile(List<MultipartFile> files, FileType type) throws NoSuchAlgorithmException, IOException {
        List<String> saveNames = new ArrayList<>();
        String savePath = "/home/seojaehui/tuthree/var" + type.getKortype();
        if (!new File(savePath).exists()) {
            new File(savePath).mkdir();
            log.info("make directory\n\n\n\n");
        }
        log.info(savePath + "------\n\n\n");

        for(int i=0; i<files.size(); i++){
            String originName = "";
            String saveName = "";
            String saveFilePath = "";
            originName = files.get(i).getOriginalFilename();
            log.info(originName + "\n\n");
            saveName = saveFileNameToHash(originName + new Date());
            log.info(saveName + "  save\n\n");

            saveFilePath += savePath + "/" + saveName;
            log.info("save : " + saveFilePath);

            files.get(i).transferTo(new File(saveFilePath));
            saveNames.add(saveFilePath);
            log.info("endpointttt \n\n\n");
        }
        return saveNames;
    }
//    String path = "/home/seojaehui/testform";
//        if(!new File(path).exists()){
//        new File(path).mkdir();
//    }
//        form.getFile().get(0).transferTo(new File(path +"/" + form.getFile().get(0).getOriginalFilename()));
//


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

}
