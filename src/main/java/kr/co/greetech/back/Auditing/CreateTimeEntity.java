package kr.co.greetech.back.Auditing;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class CreateTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    private Long createdTime;

    @PrePersist
    public void before() {
        createdTime = System.currentTimeMillis();
    }
}
