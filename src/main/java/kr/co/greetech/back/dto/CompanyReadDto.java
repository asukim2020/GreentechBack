package kr.co.greetech.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class CompanyReadDto {
    @NotNull(message = "CompanyReadDto name")
    @Size(min = 2, max = 30, message = "회사명은 2~30자이어야 합니다.")
    String name;

    @NotNull(message = "CompanyReadDto loginPw")
    @Size(min = 4, max = 20, message = "비밀번호는 4~20자이어야 합니다.")
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
//            message = "비밀번호는 영문 대,소문자와 숫자와 특수기호가 적어도 1개 이상씩 포함되어야 합니다.")
    String loginPw;
}
