package project.tuthree.testlogic;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import project.tuthree.domain.Status;
import project.tuthree.domain.user.Grade;
import project.tuthree.domain.user.School;
import project.tuthree.domain.user.SchoolStatus;
import project.tuthree.domain.user.Sex;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

//json->data
//json을 dto값으로 넣어야 되는지,, 어떻게 해야되는지,,
public class JsonParser {

    public void jsonToTData(String jsonFileN){
        JSONParser parser = new JSONParser();

        try {


            Object obj = parser.parse(new FileReader(jsonFileN));

            JSONObject jsonObject = (JSONObject) obj;

            String id = (String) jsonObject.get("id");
            String pwd = (String) jsonObject.get("pwd");
            String name = (String) jsonObject.get("name");
            String email = (String) jsonObject.get("email");
            String tel = (String) jsonObject.get("tel");
            Sex sex = (Sex) jsonObject.get("sex");
            Integer birth = (Integer) jsonObject.get("birth");
            String post = (String) jsonObject.get("post");
            Grade grade = (Grade) jsonObject.get("grade");
            String region = null;
            JSONArray regionL = (JSONArray) jsonObject.get("region");

            Iterator<String> iteratorR = regionL.iterator();
            while (iteratorR.hasNext()) {
                region=region+","+iteratorR.next();
            }
            //String region = (String) jsonObject.get("region");
            Status registration = (Status) jsonObject.get("registration");

            String subject = null;
            JSONArray subjectL = (JSONArray) jsonObject.get("region");

            Iterator<String> iteratorS = subjectL.iterator();
            while (iteratorS.hasNext()) {
                subject=subject+","+iteratorS.next();
            }
            //String subject = (String) jsonObject.get("subject");
            Integer cost = (Integer) jsonObject.get("cost");
            String school = (String) jsonObject.get("school");
            SchoolStatus status = (SchoolStatus) jsonObject.get("status");
            String major = (String) jsonObject.get("major");
            String detail = (String) jsonObject.get("detail");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    //학생
    public void jsonToSData(String jsonFileN){
        JSONParser parser = new JSONParser();

        try {


            Object obj = parser.parse(new FileReader(jsonFileN));

            JSONObject jsonObject = (JSONObject) obj;

            String id = (String) jsonObject.get("id");
            String pwd = (String) jsonObject.get("pwd");
            String name = (String) jsonObject.get("name");
            String email = (String) jsonObject.get("email");
            String tel = (String) jsonObject.get("tel");
            Sex sex = (Sex) jsonObject.get("sex");
            Integer birth = (Integer) jsonObject.get("birth");
            String post = (String) jsonObject.get("post");
            Grade grade = (Grade) jsonObject.get("grade");
            String region = null;
            JSONArray regionL = (JSONArray) jsonObject.get("region");

            Iterator<String> iteratorR = regionL.iterator();
            while (iteratorR.hasNext()) {
                region=region+","+iteratorR.next();
            }
            //String region = (String) jsonObject.get("region");
            Status registration = (Status) jsonObject.get("registration");

            String subject = null;
            JSONArray subjectL = (JSONArray) jsonObject.get("region");

            Iterator<String> iteratorS = subjectL.iterator();
            while (iteratorS.hasNext()) {
                subject=subject+","+iteratorS.next();
            }
            //String subject = (String) jsonObject.get("subject");
            School school = (School) jsonObject.get("school");
            String detail = (String) jsonObject.get("detail");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    //부모님
    public void jsonToPData(String jsonFileN){
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(jsonFileN));

            JSONObject jsonObject = (JSONObject) obj;

            String id = (String) jsonObject.get("id");
            String pwd = (String) jsonObject.get("pwd");
            String name = (String) jsonObject.get("name");
            String email = (String) jsonObject.get("email");
            String tel = (String) jsonObject.get("tel");
            Sex sex = (Sex) jsonObject.get("sex");
            Integer birth = (Integer) jsonObject.get("birth");
            String post = (String) jsonObject.get("post");
            Grade grade = (Grade) jsonObject.get("grade");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }



    public String jsonp(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(json);

        JSONArray subject = (JSONArray)obj.get("subject");
        String subjectS = null;
        for(int i=0; i<subject.size(); i++){
            JSONObject tmp = (JSONObject)subject.get(i);
            String subjectItem = (String)tmp.get("subject");
            subjectS = subjectS+","+subjectItem;
        }
        return subjectS;
    }
}
