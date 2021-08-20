package kr.co.greetech.back.Auditing;

import jdk.jfr.Timestamp;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class CreateTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;
}
