package project.tuthree.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RestController;
import project.tuthree.controller.RedisTestService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FirebaseConfiguration {

    private FirebaseApp firebaseApp;
    private final RedisTestService service;
    @PostConstruct
    public void init() {
        try {
            InputStream in = getClass().getResourceAsStream("/project-tuthree-firebase-adminsdk-b508m-21e23c6c8a.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(in))
                    .build();

            firebaseApp = FirebaseApp.initializeApp(options);
//            FirebaseInstanceId.getInstance(firebaseApp).
//            ImplFirebaseTrampolines.getService(firebaseApp, FirebaseInstanceId.class.getName(),
//                    FirebaseInstanceId.FirebaseInstanceIdService.class);


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


    public String getAccessToken() throws Exception {

        String path = "project-tuthree-firebase-adminsdk-b508m-21e23c6c8a.json";


        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(path).getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
//                .createScoped(Arrays.asList("https://oauth2.googleapis.com/token"));

        // accessToken ??????
        googleCredentials.refreshIfExpired();
//        googleCredentials.refreshAccessToken();

        // GoogleCredential??? getAccessToken?????? ?????? ????????? ???, getTokenValue??? ??????????????? ??????
        // REST API??? FCM??? push ?????? ?????? ??? Header??? ???????????? ????????? ?????? ??????
        log.info("================token : \n" + googleCredentials.getAccessToken().getTokenValue() + "\n");
        return googleCredentials.getAccessToken().getTokenValue();
    }


}
