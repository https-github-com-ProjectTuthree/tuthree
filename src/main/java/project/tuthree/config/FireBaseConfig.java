package project.tuthree.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Service
public class FireBaseConfig {

    private static final String path = "project-tuthree-firebase-adminsdk-b508m-21e23c6c8a.json";

    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(path).getInputStream())).build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase Application Initialize");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Bean
//    public FirebaseAuth initFirebaseAuth() {
//        return FirebaseAuth.getInstance(firebaseApp);
//    }
//
//    @Bean
//    public FirebaseMessaging initFirebaseMessaging() {
//        return FirebaseMessaging.getInstance(firebaseApp);
//    }
//

//    private String getAccessToken() throws Exception {
//        String firebaseConfigPath = "key_path";
//
//        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
//                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
//
//        // accessToken 생성
//        googleCredentials.refreshIfExpired();
//
//        // GoogleCredential의 getAccessToken으로 토큰 받아온 뒤, getTokenValue로 최종적으로 받음
//        // REST API로 FCM에 push 요청 보낼 때 Header에 설정하여 인증을 위해 사용
//        return googleCredentials.getAccessToken().getTokenValue();
//    }
}
