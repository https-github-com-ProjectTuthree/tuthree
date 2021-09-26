package project.tuthree.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category {
    KOR("국어"),
    KOR_MIDDLE("중등 국어"),
    KOR_HIGH("고등 국어"),
    KOR_HIGH_MUNHAK("고등 국어(문학)"),
    KOR_HIGH_HWAJAK("고등 국어(화작문)"),
    KOR_HIGH_READING("고등 국어(독서문법)"),

    ENG("영어"),
    ENG_MIDDLE("중등 영어"),
    ENG_HIGH("고등 영어"),
    ENG_CSAT("수능 영어"),
    ENG_TRANSFER("편입 영어"),

    MATH("수학"),
    MATH_MIDDLE("중등 수학"),
    MATH_HIGH_COMMON("고등 수학(공통)"),
    MATH_HIGH_LITERATURE("고등 수학(문과)"),
    MATH_HIGH_SCIENCE("고등 수학(이과)"),
    MATH_TRANSFER("편입 수학"),

    SOCIETY("사회"),
    SOCIETY_MIDDLE("중등 사회"),
    SOCIETY_HIGH("고등 사회"),
    SOCIETY_HIGH_WORLD("고등 사회(세계사)"),
    SOCIETY_HIGH_KOR("고등 사회(한국사)"),
    SOCIETY_HIGH_WORLD_GEO("고등 사회(세계 지리)"),
    SOCIETY_HIGH_KOR_GEO("고등 사회(한국 지리)"),
    SOCIETY_HIGH_ETHICS("고등 사회(윤리와 사상)"),
    SOCIETY_HIGH_LIFE("고등 사회(생활과 윤리)"),
    SOCIETY_HIGH_CULTURE("고등 사회(사회문화)"),
    SOCIETY_HIGH_ECONOMY("고등 사회(경제)"),
    SOCIETY_HIGH_LAW("고등 사회(법과 정치)"),
    SOCIETY_HIGH_EASTAISAN("고등 사회(동아시아사)"),

    SCIENCE("과학"),
    SCIENCE_MIDDLE("중등 과학"),
    SCIENCE_HIGH("고등 과학"),
    SCIENCE_HIGH_PHYSIC1("고등 과학(물리1)"),
    SCIENCE_HIGH_PHYSIC2("고등 과학(물리2)"),
    SCIENCE_HIGH_CHEMISTRY1("고등 과학(화학1)"),
    SCIENCE_HIGH_CHEMISTRY2("고등 과학(화학2)"),
    SCIENCE_HIGH_LIFE1("고등 과학(생명과학1)"),
    SCIENCE_HIGH_LIFE2("고등 과학(생명과학2)"),
    SCIENCE_HIGH_EARTH1("고등 과학(지구과학1)"),
    SCIENCE_HIGH_EARTH2("고등 과학(지구과학2)"),

    SECOND_LANG("외국어 회화"),
    SECOND_LANG_CHINESE("중국어"),
    SECOND_LANG_JAPANESE("일본어"),
    SECOND_LANG_CHINESE_CHAR("한문"),
    SECOND_LANG_FRENCH("프랑스어"),
    SECOND_LANG_SPANISH("스페인어"),
    SECOND_LANG_GERMAN("독일어"),
    SECOND_LANG_ARABIC("아랍어"),
    SECOND_LANG_RUSSIAN("러시아어"),
    SECOND_LANG_VIETNAMES("베트남어"),

    FOREIGN_CERTIFY("외국어 공인 인증"),
    FOREIGN_CERTIFY_TOEIC("토익"),
    FOREIGN_CERTIFY_TOSS("토스"),
    FOREIGN_CERTIFY_TEPS("텝스"),
    FOREIGN_CERTIFY_TOEFL("톺르"),
    FOREIGN_CERTIFY_ILS("아이엘츠"),
    FOREIGN_CERTIFY_OPIC("OPic"),
    FOREIGN_CERTIFY_HSK_TSC("HSK/TSC"),
    FOREIGN_CERTIFY_JPT_JLPT("JPT/JLPT"),
    FOREIGN_CERTIFY_DELE("DELE"),
    FOREIGN_CERTIFY_DELE_DALF("DELE/DALF"),
    FOREIGN_CERTIFY_TORFL("TORFL"),

    FOREIGN_CONVERSASTION("외국어 회화"),
    FOREIGN_CONVERSASTION_BUSNINESS_ENG("비즈니스 영어"),
    FOREIGN_CONVERSASTION_ENG("영어"),
    FOREIGN_CONVERSASTION_BUSINESS_CHINESE("비즈니스 중국어"),
    FOREIGN_CONVERSASTION_CHINESE("중국어"),
    FOREIGN_CONVERSASTION_BUSINESS_JAPAN("비즈니스 일본어"),
    FOREIGN_CONVERSASTION_JAPAN("일본어"),
    FOREIGN_CONVERSASTION_GERNAM("독일어"),
    FOREIGN_CONVERSASTION_FRENCH("프랑스어"),
    FOREIGN_CONVERSASTION_SPANISH("스페인어"),
    FOREIGN_CONVERSASTION_RUSSIAN("러시아어"),
    FOREIGN_CONVERSASTION_ARABIC("아랍어"),
    FOREIGN_CONVERSASTION_VIETNAMESE("베트남어"),
    ETC("기타");


    private String korSubject;

    Category(String korSubject) {
        this.korSubject = korSubject;
    }

    public Map<String, Category> categoryOf() {
        Map<String, Category> map = null;
        for (Category category : Category.values()) {
            map.put("과목", category);
        }
        return map;
    }
}
