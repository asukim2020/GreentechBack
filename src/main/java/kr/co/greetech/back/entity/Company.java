package kr.co.greetech.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.greetech.back.Auditing.BaseTimeEntity;
import kr.co.greetech.back.dto.CompanyCreateDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Company extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "COMPANY_ID")
    Long id;

    @NotNull(message = "Company name")
    @Column(unique = true)
    String name;

    @NotNull(message = "Company loginId")
    @Column(unique = true)
    String loginId;

    @NotNull(message = "Company loginPw")
    String loginPw;

    private Company(CompanyCreateDto companyCreateDto) {
        name = companyCreateDto.getName();
        loginId = companyCreateDto.getLoginId();
        loginPw = companyCreateDto.getLoginPw();
    }

    public static Company create(CompanyCreateDto companyCreateDto) {
        return new Company(companyCreateDto);
    }
}

/*
// JSON 형태
[
  {
    "createdDate": "2021-08-19T10:46:53.375383",
    "lastModifiedDate": "2021-08-19T10:46:53.375383",
    "id": 1,
    "name": "1234"
  },
  {
    "createdDate": "2021-08-19T10:47:01.084938",
    "lastModifiedDate": "2021-08-19T10:47:01.084938",
    "id": 3,
    "name": "12355"
  },
  {
    "createdDate": "2021-08-19T10:47:04.734144",
    "lastModifiedDate": "2021-08-19T10:47:04.734144",
    "id": 4,
    "name": "1235"
  }
]
 */