package kr.co.greetech.back.Auditing;

import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.entity.DataLogger;
import kr.co.greetech.back.entity.MeasureData;
import kr.co.greetech.back.repository.CompanyRepository;
import kr.co.greetech.back.repository.DataLoggerRepository;
import kr.co.greetech.back.repository.MeasureDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BaseTimeEntityTest {

    @Autowired CompanyRepository companyRepository;
    @Autowired DataLoggerRepository dataLoggerRepository;
    @Autowired MeasureDataRepository measureDataRepository;

    @Test
    public void auditing() {
        String companyName = "company";
        Company company = Company.builder()
                .name(companyName)
                .build();
        Company savedCompany = companyRepository.save(company);
        assertThat(savedCompany.getCreatedDate()).isNotNull();
        assertThat(savedCompany.getLastModifiedDate()).isNotNull();

        System.out.println("savedCompany.getCreatedDate() = " + savedCompany.getCreatedDate());
        System.out.println("savedCompany.getLastModifiedDate() = " + savedCompany.getLastModifiedDate());

        DataLogger dataLogger = DataLogger.builder()
                .company(savedCompany)
                .build();
        DataLogger savedDataLogger = dataLoggerRepository.save(dataLogger);
        assertThat(savedDataLogger.getCreatedDate()).isNotNull();
        assertThat(savedDataLogger.getLastModifiedDate()).isNotNull();

        System.out.println("savedDataLogger.getCreatedDate() = " + savedDataLogger.getCreatedDate());
        System.out.println("savedDataLogger.getLastModifiedDate() = " + savedDataLogger.getLastModifiedDate());

        MeasureData measureData = MeasureData.builder()
                .data("data")
                .dataLogger(savedDataLogger)
                .build();
        MeasureData savedMeasureData = measureDataRepository.save(measureData);
        assertThat(savedMeasureData.getCreatedDate()).isNotNull();

        System.out.println("savedMeasureData.getCreatedDate() = " + savedMeasureData.getCreatedDate());
    }
}