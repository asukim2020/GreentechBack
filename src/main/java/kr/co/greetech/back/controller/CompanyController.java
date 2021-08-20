package kr.co.greetech.back.controller;

import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.repository.CompanyRepository;
import kr.co.greetech.back.util.ExceptionMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyRepository companyRepository;

    // TODO: - 로그인 로직 및 가입 시 id, pw 추가
    @PostMapping("/company")
    public Long signup(@Validated CompanyCreateDto companyCreateDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.warn("error={} ", bindingResult);
            throw new IllegalArgumentException(ExceptionMsg.bindingMsg(bindingResult));
        }

        Company company = Company.create(companyCreateDto);
        companyRepository.save(company);
        return company.getId();
    }

    // TODO: - 수정 로직 추가
}
