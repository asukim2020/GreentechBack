package kr.co.greetech.back.Auditing;

import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.dto.MeasureDataDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.entity.DataLogger;
import kr.co.greetech.back.entity.MeasureData;
import kr.co.greetech.back.repository.CompanyRepository;
import kr.co.greetech.back.repository.DataLoggerRepository;
import kr.co.greetech.back.repository.MeasureDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BaseTimeEntityTest {

    @Autowired CompanyRepository companyRepository;
    @Autowired DataLoggerRepository dataLoggerRepository;
    @Autowired MeasureDataRepository measureDataRepository;

    @Test
    public void auditing() {
        String companyName = "company";
        Company company = Company.create(new CompanyCreateDto(companyName));
        Company savedCompany = companyRepository.save(company);
        assertThat(savedCompany.getCreatedTime()).isNotNull();
        assertThat(savedCompany.getLastModifiedTime()).isNotNull();

        System.out.println("savedCompany.getCreatedTime() = " + savedCompany.getCreatedTime());
        System.out.println("savedCompany.getLastModifiedTime() = " + savedCompany.getLastModifiedTime());

        DataLogger dataLogger = DataLogger.create(new DataLoggerCreateDto("dataLogger"), savedCompany);
        DataLogger savedDataLogger = dataLoggerRepository.save(dataLogger);
        assertThat(savedDataLogger.getCreatedTime()).isNotNull();
        assertThat(savedDataLogger.getLastModifiedTime()).isNotNull();

        System.out.println("savedDataLogger.getCreatedTime() = " + savedDataLogger.getCreatedTime());
        System.out.println("savedDataLogger.getLastModifiedTime() = " + savedDataLogger.getLastModifiedTime());

        MeasureData measureData = MeasureData.create(new MeasureDataDto("data"), dataLogger);
        MeasureData savedMeasureData = measureDataRepository.save(measureData);
        assertThat(savedMeasureData.getCreatedTime()).isNotNull();

        System.out.println("savedMeasureData.getCreatedTime() = " + savedMeasureData.getCreatedTime());
    }
}