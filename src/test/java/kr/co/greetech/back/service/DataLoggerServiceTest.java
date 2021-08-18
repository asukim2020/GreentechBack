package kr.co.greetech.back.service;

import kr.co.greetech.back.dto.DataLoggerDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.repository.CompanyRepository;
import kr.co.greetech.back.repository.DataLoggerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DataLoggerServiceTest {

    DataLoggerService dataLoggerService;

    CompanyRepository companyRepository;
    DataLoggerRepository dataLoggerRepository;

    @Autowired
    public DataLoggerServiceTest(CompanyRepository companyRepository, DataLoggerRepository dataLoggerRepository) {
        this.companyRepository = companyRepository;
        this.dataLoggerRepository = dataLoggerRepository;
        dataLoggerService = new DataLoggerService(companyRepository, dataLoggerRepository);
    }

    @BeforeEach
    void beforeEach() {
        dataLoggerService = new DataLoggerService(companyRepository, dataLoggerRepository);
    }

    @Test
    public void register() {
        Company company = Company.builder()
                .name("company")
                .build();
        companyRepository.save(company);

        Long dataLoggerId = dataLoggerService.register(company.getId(), new DataLoggerDto(null,"dataLogger"));
        DataLoggerDto dataLoggerDto = dataLoggerService.findById(dataLoggerId);
        assertThat(dataLoggerDto).isNotNull();
    }

    @Test
    public void registerException() {
        assertThrows(IllegalArgumentException.class, () -> {
            dataLoggerService.register(-1L, new DataLoggerDto(null,"dataLogger"));
        });
    }
}