package kr.co.greetech.back.controller;

import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.service.CompanyService;
import kr.co.greetech.back.session.SessionConst;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyService companyService;

    @Test

    public void signup() throws Exception {
        mockMvc.perform(post("/company")
                .param("name", "company")
                .param("loginId", "abcdef")
                .param("loginPw", "abcdefg1!")
        ).andExpect(status().isOk());
    }

    @Test
    public void illegalSignup() throws Exception {
        mockMvc.perform(post("/company")
                .param("name", "company")
                .param("loginId", "abcdeasdfffsdadfdfasff")
                .param("loginPw", "abasdfcdefgdsafjlsjdasfd"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void login() throws Exception {
        String loginId = "abcdef";
        String loginPw = "abcdefg1!";
        CompanyCreateDto companyDto = new CompanyCreateDto("company", loginId, loginPw);
        companyService.create(companyDto);

        mockMvc.perform(post("/company/login")
                .param("id", loginId)
                .param("pw", loginPw))
                .andExpect(status().isOk());
    }

    @Test
    public void logout() throws Exception {
        String loginId = "abcdef";
        String loginPw = "abcdefg1!";
        CompanyCreateDto companyDto = new CompanyCreateDto("company", loginId, loginPw);
        MockHttpServletRequest request = new MockHttpServletRequest();
        companyService.create(companyDto);
        companyService.login(loginId, loginPw, request);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        assertThrows(IllegalStateException.class, () -> {
            session.getAttribute(SessionConst.LOGIN_COMPANY);
        });
    }
}