package kr.co.greetech.back.Auditing;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private Long createdTime;

    @LastModifiedDate
    private Long lastModifiedTime;

    @PrePersist
    public void before() {
        Long time = System.currentTimeMillis();
        createdTime = time;
        lastModifiedTime = time;
    }

    @PreUpdate
    public void always() {
        lastModifiedTime = System.currentTimeMillis();
    }
}
