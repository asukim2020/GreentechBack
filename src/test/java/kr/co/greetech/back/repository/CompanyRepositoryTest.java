package kr.co.greetech.back.repository;

import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.entity.Company;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CompanyRepositoryTest {

    @Autowired
    CompanyRepository companyRepository;

    @BeforeEach
    void beforeEach() {
        System.out.println("CompanyRepositoryTest.beforeEach");
        for (int i = 0; i < 5; i++) {
            Company company = Company.create(new CompanyCreateDto("company" + i));
            companyRepository.save(company);
        }
    }

    @AfterEach
    void afterEach() {
        // empty
    }

    @Test
    public void findByName() {
        System.out.println("CompanyRepositoryTest.findByName");

        String findName = "company1";
        List<Company> companies = companyRepository.findByName(findName);

        Company company = companies.get(0);
        assertThat(company.getName()).isEqualTo(findName);
    }
}