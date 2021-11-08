package kr.co.greetech.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.greetech.back.Auditing.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class FcmToken extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "FCM_TOKEN_ID")
    Long id;

    @NotNull(message = "FcmToken fcmToken")
    @Column(nullable = false)
    String fcmToken;

    @NotNull(message = "FcmToken company")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    @JsonIgnore
    Company company;

    public static FcmToken create(Company company, String fcmToken) {
        FcmToken fcmTokenObj = new FcmToken();
        fcmTokenObj.company = company;
        fcmTokenObj.fcmToken = fcmToken;
        return fcmTokenObj;
    }

    public void updateFcmToken(String fcmToken) {
        log.info("updateFcmToken");
        if (fcmToken != null
                && !fcmToken.equals("null")
                && !fcmToken.equals("")) {
            this.lastModifiedTime = LocalDateTime.now();
        }
    }
}
