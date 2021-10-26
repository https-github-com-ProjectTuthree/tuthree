package project.tuthree.configuration;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class Utils {

    /** JWT UTILS */
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer";
    public static final String CLAIMUSERID = "userId";
    public static final String CLAIMGRADE = "Grade";
    public static final String REFRESHISSUE = "refresh_token";
    public static final String SECRET_KEY = "qweasdzxcqweasdzxc";
    public static final Long EXPIRATION_TIME = 15 * 60 * 4 * 1000L;


}
