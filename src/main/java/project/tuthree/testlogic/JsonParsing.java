package project.tuthree.testlogic;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import project.tuthree.dto.user.StudentRegisterDTO;
import project.tuthree.dto.user.TeacherRegisterDTO;
import project.tuthree.dto.user.UserRegisterDTO;

import java.io.FileWriter;
import java.io.IOException;

//되면 UserFileRepository로 이동하기
//dto->json
public class JsonParsing {


    String savePath = "C:\\Users\\LG\\Desktop\\새 폴더 (2)\\";

    //선생님 json
    public String jsonTParse(TeacherRegisterDTO registerDTO){
        String savePath = "C:\\Users\\LG\\Desktop\\새 폴더 (2)\\";
        String jFileName=savePath+registerDTO.getId()+"json";

        //TeacherRegisterDTO teacherDTO;
        JSONObject obj = new JSONObject();
        obj.put("id", registerDTO.getId());
        obj.put("pwd", registerDTO.getPwd());
        obj.put("name", registerDTO.getName());
        obj.put("email", registerDTO.getEmail());
        obj.put("tel", registerDTO.getTel());
        obj.put("sex", registerDTO.getSex());
        obj.put("birth", registerDTO.getBirth());
        obj.put("post", registerDTO.getPost());
        obj.put("grade", registerDTO.getGrade());

        JSONArray listRegion = new JSONArray();
        for(int i=0; i<registerDTO.getRegionL().size(); i++){
            JSONObject tmpR = new JSONObject();
            tmpR.put("region", registerDTO.getRegionL().get(i));
            listRegion.add(tmpR);
        }
        obj.put("region", listRegion);
        obj.put("registeration", registerDTO.getRegistration());

        JSONArray listSubject = new JSONArray();
        for(int i=0; i<registerDTO.getSubjectL().size(); i++){
            JSONObject tmpS = new JSONObject();
            tmpS.put("region", registerDTO.getSubjectL().get(i));
            listSubject.add(tmpS);
        }
        obj.put("subject", listSubject);
        obj.put("cost", registerDTO.getCost());
        obj.put("school", registerDTO.getSchool());
        obj.put("major", registerDTO.getMajor());
        obj.put("detail", registerDTO.getDetail());
        obj.put("certification", registerDTO.getCertification());
        obj.put("file", registerDTO.getFile());
        obj.put("authFile", registerDTO.getAuthFile());

        try {
            FileWriter file = new FileWriter(jFileName);
            file.write(obj.toJSONString());
            file.flush();
            file.close();

        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return jFileName;
    }

    //학생 json
    public String jsonSParse(StudentRegisterDTO registerDTO){
        String savePath = "C:\\Users\\LG\\Desktop\\새 폴더 (2)\\";
        String jFileName=savePath+registerDTO.getId()+"json";

        //TeacherRegisterDTO teacherDTO;
        JSONObject obj = new JSONObject();
        obj.put("id", registerDTO.getId());
        obj.put("pwd", registerDTO.getPwd());
        obj.put("name", registerDTO.getName());
        obj.put("email", registerDTO.getEmail());
        obj.put("tel", registerDTO.getTel());
        obj.put("sex", registerDTO.getSex());
        obj.put("birth", registerDTO.getBirth());
        obj.put("grade", registerDTO.getGrade());
        obj.put("post", registerDTO.getPost());

        JSONArray listRegion = new JSONArray();
        for(int i=0; i<registerDTO.getRegionL().size(); i++){
            JSONObject tmpR = new JSONObject();
            tmpR.put("region", registerDTO.getRegionL().get(i));
            listRegion.add(tmpR);
        }
        obj.put("region", listRegion);
        obj.put("registeration", registerDTO.getRegistration());

        JSONArray listSubject = new JSONArray();
        for(int i=0; i<registerDTO.getSubjectL().size(); i++){
            JSONObject tmpS = new JSONObject();
            tmpS.put("region", registerDTO.getSubjectL().get(i));
            listSubject.add(tmpS);
        }
        obj.put("subject", listSubject);
        obj.put("cost", registerDTO.getCost());
        obj.put("school", registerDTO.getSchool());
        obj.put("detail", registerDTO.getDetail());
        obj.put("file", registerDTO.getFile());

        try {
            FileWriter file = new FileWriter(jFileName);
            file.write(obj.toJSONString());
            file.flush();
            file.close();

        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return jFileName;
    }

    //부모님 json
    public String jsonPParse(UserRegisterDTO registerDTO){
        String savePath = "C:\\Users\\LG\\Desktop\\새 폴더 (2)\\";
        String jFileName=savePath+registerDTO.getId()+"json";

        //TeacherRegisterDTO teacherDTO;
        JSONObject obj = new JSONObject();
        obj.put("id", registerDTO.getId());
        obj.put("pwd", registerDTO.getPwd());
        obj.put("name", registerDTO.getName());
        obj.put("email", registerDTO.getEmail());
        obj.put("tel", registerDTO.getTel());
        obj.put("sex", registerDTO.getSex());
        obj.put("birth", registerDTO.getBirth());
        obj.put("grade", registerDTO.getGrade());
        obj.put("post", registerDTO.getPost());
        obj.put("file", registerDTO.getFile());

        try {
            FileWriter file = new FileWriter(jFileName);
            file.write(obj.toJSONString());
            file.flush();
            file.close();

        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return jFileName;
    }



}
