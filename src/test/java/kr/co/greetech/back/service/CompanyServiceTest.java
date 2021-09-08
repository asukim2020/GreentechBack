package kr.co.greetech.back.service;

import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.repository.CompanyRepository;
import kr.co.greetech.back.session.SessionConst;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class CompanyServiceTest {

    CompanyService companyService;
    EntityManager em;
    CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceTest(EntityManager em, CompanyRepository companyRepository) {
        this.em = em;
        this.companyRepository = companyRepository;
        companyService = new CompanyService(companyRepository);
    }

    @Test
    void create() {
        String loginId = "abcdefg";
        String loginPw = "abcdefg";

        CompanyCreateDto companyCreateDto = new CompanyCreateDto("company", loginId, loginPw);
        Long companyId = companyService.create(companyCreateDto);

        Company company = em.find(Company.class, companyId);
        assertThat(company.getLoginId()).isEqualTo(loginId);
        assertThat(company.getLoginPw()).isEqualTo(loginPw);
    }

    @Test
    void login() {
        String loginId = "abcdefg";
        String loginPw = "abcdefg";

        CompanyCreateDto companyCreateDto = new CompanyCreateDto("company", loginId, loginPw);
        companyService.create(companyCreateDto);
        MockHttpServletRequest request = new MockHttpServletRequest();

        Long companyId = companyService.login(loginId, loginPw, request);
        Company company = em.find(Company.class, companyId);
        assertThat(company.getLoginId()).isEqualTo(loginId);
        assertThat(company.getLoginPw()).isEqualTo(loginPw);

        Long sessionCompanyId = (Long) request.getSession().getAttribute(SessionConst.LOGIN_COMPANY);
    }
}