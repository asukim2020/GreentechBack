package kr.co.greetech.back.repository;

import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.dto.MeasureDataDto;
import kr.co.greetech.back.entity.DataLogger;
import kr.co.greetech.back.entity.MeasureData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        DataLogger dataLogger = DataLogger.create(new DataLoggerCreateDto("dataLogger"), null);
        dataLoggerRepository.save(dataLogger);

        for (int i = 0; i < 5; i++) {
            Thread.sleep(10);
            MeasureData measureData = MeasureData.create(new MeasureDataDto("data"), dataLogger);
            measureDataRepository.save(measureData);
        }
    }

    @Test
    public void select() {
        List<DataLogger> dataLoggers = dataLoggerRepository.findAll();
        DataLogger dataLogger = dataLoggers.get(0);
        List<MeasureDataDto> dataDtos = measureDataQueryRepository.search(
                dataLogger.getId(),
                System.currentTimeMillis() - (1000 * 3600 * 24),
                System.currentTimeMillis()
        );

        for (MeasureDataDto dataDto : dataDtos) {
            System.out.println("dataDto = " + dataDto);
        }

        assertThat(dataDtos.size()).isEqualTo(5);
    }
}