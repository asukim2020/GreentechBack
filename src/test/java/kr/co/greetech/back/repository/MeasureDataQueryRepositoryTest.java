package kr.co.greetech.back.repository;

import kr.co.greetech.back.business.datalogger.repository.DataLoggerRepository;
import kr.co.greetech.back.business.measuredata.repository.MeasureDataQueryRepository;
import kr.co.greetech.back.business.measuredata.repository.MeasureDataRepository;
import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.dto.MeasureDataDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.entity.DataLogger;
import kr.co.greetech.back.entity.MeasureData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class MeasureDataQueryRepositoryTest {

    EntityManager em;

    DataLoggerRepository dataLoggerRepository;
    MeasureDataRepository measureDataRepository;
    MeasureDataQueryRepository measureDataQueryRepository;

    @Autowired
    public MeasureDataQueryRepositoryTest(EntityManager em, DataLoggerRepository dataLoggerRepository, MeasureDataRepository measureDataRepository) {
        this.em = em;
        this.dataLoggerRepository = dataLoggerRepository;
        this.measureDataRepository = measureDataRepository;
        measureDataQueryRepository = new MeasureDataQueryRepository(em);
    }

    @BeforeEach
    void beforeEach() throws InterruptedException {
        Company company = Company.create(new CompanyCreateDto("company", "abcdefg", "abcdefg1!"));
        em.persist(company);

        DataLogger dataLogger = DataLogger.create(new DataLoggerCreateDto("dataLogger", "", ""), company);
        dataLoggerRepository.save(dataLogger);

        for (int i = 0; i < 5; i++) {
            Thread.sleep(10);
            MeasureData measureData = MeasureData.create(new MeasureDataDto("data", LocalDateTime.now()), dataLogger);
            measureDataRepository.save(measureData);
        }
    }

    @Test
    public void select() {
        List<DataLogger> dataLoggers = dataLoggerRepository.findAll();
        DataLogger dataLogger = dataLoggers.get(0);
        List<MeasureDataDto> dataDtos = measureDataQueryRepository.search(
                dataLogger.getId(),
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now()
        );

        for (MeasureDataDto dataDto : dataDtos) {
            System.out.println("dataDto = " + dataDto);
        }

        assertThat(dataDtos.size()).isGreaterThan(0);
    }
}