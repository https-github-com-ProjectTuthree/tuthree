package project.tuthree.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category {

    KOR("국어"),
    KOR$MIDDLE("중등 국어"),
    KOR$HIGH("고등 국어"),
    KOR$HIGH_MUNHAK("고등 국어(문학)"),
    KOR$HIGH_HWAJAK("고등 국어(화작문)"),
    KOR$HIGH_READING("고등 국어(독서문법)"),

    ENG("영어"),
    ENG$MIDDLE("중등 영어"),
    ENG$HIGH("고등 영어"),
    ENG$CSAT("수능 영어"),
    ENG$TRANSFER("편입 영어"),

    MATH("수학"),
    MATH$MIDDLE("중등 수학"),
    MATH$HIGH_COMMON("고등 수학(공통)"),
    MATH$HIGH_LITERATURE("고등 수학(문과)"),
    MATH$HIGH_SCIENCE("고등 수학(이과)"),
    MATH$TRANSFER("편입 수학"),

    SOCIETY("사회"),
    SOCIETY$MIDDLE("중등 사회"),
    SOCIETY$HIGH("고등 사회"),
    SOCIETY$HIGH_WORLD("고등 사회(세계사)"),
    SOCIETY$HIGH_KOR("고등 사회(한국사)"),
    SOCIETY$HIGH_WORLD_GEO("고등 사회(세계 지리)"),
    SOCIETY$HIGH_KOR_GEO("고등 사회(한국 지리)"),
    SOCIETY$HIGH_ETHICS("고등 사회(윤리와 사상)"),
    SOCIETY$HIGH_LIFE("고등 사회(생활과 윤리)"),
    SOCIETY$HIGH_CULTURE("고등 사회(사회문화)"),
    SOCIETY$HIGH_ECONOMY("고등 사회(경제)"),
    SOCIETY$HIGH_LAW("고등 사회(법과 정치)"),
    SOCIETY$HIGH_EASTAISAN("고등 사회(동아시아사)"),

    SCIENCE("과학"),
    SCIENCE$MIDDLE("중등 과학"),
    SCIENCE$HIGH("고등 과학"),
    SCIENCE$HIGH_PHYSIC1("고등 과학(물리1)"),
    SCIENCE$HIGH_PHYSIC2("고등 과학(물리2)"),
    SCIENCE$HIGH_CHEMISTRY1("고등 과학(화학1)"),
    SCIENCE$HIGH_CHEMISTRY2("고등 과학(화학2)"),
    SCIENCE$HIGH_LIFE1("고등 과학(생명과학1)"),
    SCIENCE$HIGH_LIFE2("고등 과학(생명과학2)"),
    SCIENCE$HIGH_EARTH1("고등 과학(지구과학1)"),
    SCIENCE$HIGH_EARTH2("고등 과학(지구과학2)"),

    SECOND_LANG("외국어 회화"),
    SECOND_LANG$CHINESE("중국어"),
    SECOND_LANG$JAPANESE("일본어"),
    SECOND_LANG$CHINESE_CHAR("한문"),
    SECOND_LANG$FRENCH("프랑스어"),
    SECOND_LANG$SPANISH("스페인어"),
    SECOND_LANG$GERMAN("독일어"),
    SECOND_LANG$ARABIC("아랍어"),
    SECOND_LANG$RUSSIAN("러시아어"),
    SECOND_LANG$VIETNAMES("베트남어"),

    FOREIGN_CERTIFY("외국어 공인 인증"),
    FOREIGN_CERTIFY$TOEIC("토익"),
    FOREIGN_CERTIFY$TOSS("토스"),
    FOREIGN_CERTIFY$TEPS("텝스"),
    FOREIGN_CERTIFY$TOEFL("톺르"),
    FOREIGN_CERTIFY$ILS("아이엘츠"),
    FOREIGN_CERTIFY$OPIC("OPic"),
    FOREIGN_CERTIFY$HSK_TSC("HSK/TSC"),
    FOREIGN_CERTIFY$JPT_JLPT("JPT/JLPT"),
    FOREIGN_CERTIFY$DELE("DELE"),
    FOREIGN_CERTIFY$DELE_DALF("DELE/DALF"),
    FOREIGN_CERTIFY$TORFL("TORFL"),

    FOREIGN_CONVERSASTION("외국어 회화"),
    FOREIGN_CONVERSASTION$BUSNINESS_ENG("비즈니스 영어"),
    FOREIGN_CONVERSASTION$ENG("영어"),
    FOREIGN_CONVERSASTION$BUSINESS_CHINESE("비즈니스 중국어"),
    FOREIGN_CONVERSASTION$CHINESE("중국어"),
    FOREIGN_CONVERSASTION$BUSINESS_JAPAN("비즈니스 일본어"),
    FOREIGN_CONVERSASTION$JAPAN("일본어"),
    FOREIGN_CONVERSASTION$GERNAM("독일어"),
    FOREIGN_CONVERSASTION$FRENCH("프랑스어"),
    FOREIGN_CONVERSASTION$SPANISH("스페인어"),
    FOREIGN_CONVERSASTION$RUSSIAN("러시아어"),
    FOREIGN_CONVERSASTION$ARABIC("아랍어"),
    FOREIGN_CONVERSASTION$VIETNAMESE("베트남어"),
    ETC("기타");

    private String korSubject;

    Category(String korSubject) {
        this.korSubject = korSubject;
    }

    class EnumCategory {
        public HashMap<String, List<String>> categoryList() {

            HashMap<String, List<String>> map = new HashMap<>();
            Category[] categories = Category.values();
            List<String> listCategory = new ArrayList<>();
            String category = "";
            String subcategory = "";

            for (int i = 0; i < categories.length; i++) {
                subcategory = String.valueOf(categories[i]);

                if (!subcategory.contains("$")) {
                    if (i != 0) {
                        map.put(category, new ArrayList<>(listCategory));
                        listCategory.clear();
                    }
                    category = categories[i].getKorSubject();
                } else {
                    listCategory.add(categories[i].getKorSubject());
                }
            }
            map.put(category, listCategory);
//        System.out.println("\n\n" + map.toString() + "\n\n");

            return map;
        }
    }
}




