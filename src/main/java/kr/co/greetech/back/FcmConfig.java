package kr.co.greetech.back;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

//@Profile("local")
@Configuration
public class FcmConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        FirebaseApp firebaseApp = null;

        List<FirebaseApp> apps = FirebaseApp.getApps();
        if (apps != null && !apps.isEmpty()) {
            for (FirebaseApp app : apps) {
                if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                    firebaseApp = app;
                }
            }
        } else {
            FirebaseOptions options = FirebaseOptions
                    .builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("static/multiscan-fcm.json").getInputStream()))
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
        }
        return firebaseApp;
    }
}
