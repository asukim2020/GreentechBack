package kr.co.greetech.back.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CompanyCreateDto {
    // TODO: - 생성과 수정, 조회 DTO 나누기

    @Size(min = 4, max = 255)
    String name;
}
