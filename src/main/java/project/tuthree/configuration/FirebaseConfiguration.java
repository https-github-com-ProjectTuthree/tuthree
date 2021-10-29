package project.tuthree.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfiguration {

    private FirebaseApp firebaseApp;

    @PostConstruct
    public void init() {
        try {
            InputStream in = getClass().getResourceAsStream("/project-tuthree-firebase-adminsdk-b508m-21e23c6c8a.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(in))
                    .build();

            firebaseApp = FirebaseApp.initializeApp(options);
            log.info("\n====================\n" +
                    "Firebase Application Initialize" +
                    "\n====================\n");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public FirebaseAuth initFirebaseAuth() {
        return FirebaseAuth.getInstance(firebaseApp);
    }

    @Bean
    public FirebaseMessaging initFirebaseMessaging() {
        return FirebaseMessaging.getInstance(firebaseApp);
    }

    /** 토큰 생성

    public String getAccessToken() throws Exception {

        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(path).getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

        // accessToken 생성
        googleCredentials.refreshIfExpired();

        // GoogleCredential의 getAccessToken으로 토큰 받아온 뒤, getTokenValue로 최종적으로 받음
        // REST API로 FCM에 push 요청 보낼 때 Header에 설정하여 인증을 위해 사용
        log.info("================token : \n" + googleCredentials.getAccessToken().getTokenValue() + "\n");
        return googleCredentials.getAccessToken().getTokenValue();
    }
     */
}
