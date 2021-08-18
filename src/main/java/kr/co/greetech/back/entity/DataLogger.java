package kr.co.greetech.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class DataLogger {
    @Id
    @GeneratedValue
    @Column(name = "DATA_LOGGER_ID")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    @JsonIgnore
    Company company;

//    Boolean isUpdate;

    public void setCompany(Company company) {
        this.company = company;
    }
}
