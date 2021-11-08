package kr.co.greetech.back;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Profile("local")
@Slf4j
@RestController
@RequiredArgsConstructor
public class RootController {

//    private final FirebaseApp firebaseApp;

    @RequestMapping
    public String defaultResult() throws FirebaseMessagingException {
        // 한 기기에 전송
//        String token = "c6wEutrLVgHuhCguSdVcgk:APA91bGL37GKOknFyAvV9Lj9dZKvdaoTAoGc8ID-kQ-Qo6iPwE0E--fUqZIV6vNkPmm8jYbiHmnA912knG3wKdc-hJplplPTDRZlnmz2kieSg01P6o46ittt0YwDe01nZBYRIqNOEh46";
//        Message message = Message.builder()
//                .setToken(token)
//                // Notification 방식
////                .setNotification(
////                        Notification.builder()
////                                .setTitle("title")
////                                .setBody("body")
////                                .build()
////                )
//
//                // Data 방식
//                .putData("title", "Postman")
//                .putData("message", "Hello, World!")
//                .build();
////        String response = FirebaseMessaging.getInstance(firebaseApp).send(message);
////        log.info("fcm response: " + response);
//
//        // 여러 기기에 전송
//        MulticastMessage multicastMessage = MulticastMessage.builder()
//                .addAllTokens(Arrays.asList(token, "token2"))
//                .putData("title", "Postman")
//                .putData("message", "Hello, World!")
//                .build();
//
//        BatchResponse multicastResponse = FirebaseMessaging.getInstance(firebaseApp).sendMulticast(multicastMessage);
//        log.info("fcm multicastResponse: " + multicastResponse);

        return "OK";
    }
}
