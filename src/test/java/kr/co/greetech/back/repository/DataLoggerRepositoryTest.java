package kr.co.greetech.back.repository;

import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.entity.DataLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DataLoggerRepositoryTest {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    DataLoggerRepository dataLoggerRepository;

    @BeforeEach
    void beforeEach() {
        Company company = Company.builder()
                .name("company")
                .build();

        companyRepository.save(company);

        for (int i = 0; i < 5; i++) {
            DataLogger dataLogger = DataLogger.builder()
                    .company(company)
                    .build();

            dataLoggerRepository.save(dataLogger);
        }
    }

    @Test
    public void findByCompanyId() {
        List<Company> companies = companyRepository.findAll();
        Company company = companies.get(0);

        List<DataLogger> dataLoggers = dataLoggerRepository.findByCompanyId(company.getId());
        assertThat(dataLoggers.size()).isGreaterThan(0);
    }
}