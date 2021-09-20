//package project.tuthree.testlogic;
//
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import java.io.*;
//
//public class TestJson {
//
//    public void UsingJsonParser() throws IOException, ParseException {
//        Reader reader = new FileReader("./src/main/java/project/tuthree/domain/testlogic/test.json");
//        JSONParser jsonParser = new JSONParser();
//        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
//
//        int prob = Integer.parseInt(jsonObject.get("prob").toString());
//        String first = jsonObject.get("1").toString();
//        String second = jsonObject.get("2").toString();
//        String third = jsonObject.get("3").toString();
//
//        int num = 1;
//
//        while(num <= prob){
//            if(jsonObject.get(String.valueOf(num++))){
//
//            }
//
//            System.out.println(jsonObject.get(String.valueOf(num++)).toString());
//        }
////
////        System.out.println(first);
////        System.out.println(second);
////        System.out.println(third);
//    }
//
//    public static void main(String[] args) {
//
//        try {
//            TestJson tj = new TestJson();
//            tj.UsingJsonParser();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//}
