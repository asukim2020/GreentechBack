package kr.co.greetech.back.service;

import kr.co.greetech.back.business.login.jwt.repository.FcmTokenRepository;
import kr.co.greetech.back.business.measuredata.service.MeasureDataService;
import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.dto.MeasureDataDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.entity.DataLogger;
import kr.co.greetech.back.business.datalogger.repository.DataLoggerRepository;
import kr.co.greetech.back.business.measuredata.repository.MeasureDataQueryRepository;
import kr.co.greetech.back.business.measuredata.repository.MeasureDataRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class MeasureDataServiceTest {

    @Autowired MeasureDataService measureDataService;

    @Autowired EntityManager em;

    @Autowired DataLoggerRepository dataLoggerRepository;
//    MeasureDataRepository measureDataRepository;
//    MeasureDataQueryRepository measureDataQueryRepository;

    Long dataLoggerId = 0L;

//    @Autowired
//    public MeasureDataServiceTest(EntityManager entityManager, DataLoggerRepository dataLoggerRepository, MeasureDataRepository measureDataRepository, FcmTokenRepository fcmTokenRepository, FirebaseApp firebaseApp) {
//        this.em = entityManager;
//        this.dataLoggerRepository = dataLoggerRepository;
//        this.measureDataRepository = measureDataRepository;
//        this.measureDataQueryRepository = new MeasureDataQueryRepository(em);
//
//        measureDataService = new MeasureDataService(dataLoggerRepository, measureDataRepository, measureDataQueryRepository, fcmTokenRepository, firebaseApp);
//    }

    @BeforeEach
    void beforeEach() {
        Company company = Company.create(new CompanyCreateDto("company", "abcdefg", "abcdefg1!"));
        em.persist(company);

        DataLogger dataLogger = DataLogger.create(new DataLoggerCreateDto("dataLogger", "", ""), company);
        dataLoggerRepository.save(dataLogger);
        dataLoggerId = dataLogger.getId();

        List<MeasureDataDto> measureDataDtos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            measureDataDtos.add(
                    new MeasureDataDto("data", LocalDateTime.now())
            );
        }
        measureDataService.addMeasureDataDtos(dataLogger.getId(), measureDataDtos);
    }

    @Test
    void addMeasureDataDtos() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        List<MeasureDataDto> dataDtos = measureDataService.select(
                dataLoggerId,
                start,
                end
        );

        Assertions.assertThat(dataDtos.size()).isGreaterThan(0);
    }

    @Test
    void last() {
        List<MeasureDataDto> dataDtos = measureDataService.last(dataLoggerId, 10);
        Assertions.assertThat(dataDtos.size()).isGreaterThan(0);
    }
}