package kr.co.greetech.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Company {
    @Id
    @GeneratedValue
    @Column(name = "COMPANY_ID")
    Long id;

    @Column(unique = true)
    String name;

//    @JsonIgnore
//    @Column(unique = true)
//    String loginId;
//
//    @JsonIgnore
//    String loginPw;
}
