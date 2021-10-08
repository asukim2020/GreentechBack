package kr.co.greetech.back.repository;

import kr.co.greetech.back.business.login.jwt.repository.CompanyRepository;
import kr.co.greetech.back.business.datalogger.repository.DataLoggerRepository;
import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.entity.DataLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class DataLoggerRepositoryTest {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    DataLoggerRepository dataLoggerRepository;

    @BeforeEach
    void beforeEach() {
        Company company = Company.create(new CompanyCreateDto("company", "abcdef", "abcdefg1!"));

        companyRepository.save(company);

        for (int i = 0; i < 5; i++) {
            DataLogger dataLogger = DataLogger.create(new DataLoggerCreateDto("dataLogger" + i, "", ""), company);

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