package kr.co.greetech.back.Auditing;

import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseTimeEntity extends CreateTimeEntity {
    @LastModifiedDate
    private LocalDateTime lastModifiedTime;
}
