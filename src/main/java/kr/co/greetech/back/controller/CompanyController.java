package kr.co.greetech.back.controller;

import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.service.CompanyService;
import kr.co.greetech.back.util.ExceptionMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
@CrossOrigin
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping()
    public Long signup(@Validated CompanyCreateDto companyCreateDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.warn("error={} ", bindingResult);
            throw new IllegalArgumentException(ExceptionMsg.bindingMsg(bindingResult));
        }

        return companyService.create(companyCreateDto);
    }

    // TODO: - 수정 로직 추가

    // TODO: - 로그인 권한 인터셉터 만들기 - 로그인이 필요한 url에 모두 적용하기
    @PostMapping("/login")
    public Long login(
        @RequestParam String id,
        @RequestParam String pw,
        HttpServletRequest request
    ) {
        return companyService.login(id, pw, request);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
