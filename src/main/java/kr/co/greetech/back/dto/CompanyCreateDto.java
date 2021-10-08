package kr.co.greetech.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class CompanyCreateDto {
    // TODO: - 생성과 수정, 조회 DTO 나누기

    @NotNull(message = "CompanyCreateDto name")
    @Size(min = 2, max = 30, message = "회사명은 2~30자이어야 합니다.")
    String name;

    @NotNull(message = "CompanyCreateDto loginId")
    @Size(min = 6, max = 20, message = "아이디는 6~20자이어야 합니다.")
    @Pattern(regexp = "[a-zA-Z0-9]{6,20}", message = "아이디는 영문 대,소문자와 숫자만 가능합니다.")
    String loginId;

    @NotNull(message = "CompanyCreateDto loginPw")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자이어야 합니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자와 특수기호가 적어도 1개 이상씩 포함되어야 합니다.")
    String loginPw;
}
