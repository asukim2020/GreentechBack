package kr.co.greetech.back.repository;

import kr.co.greetech.back.dto.MeasureDataDto;
import kr.co.greetech.back.entity.DataLogger;
import kr.co.greetech.back.entity.MeasureData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MeasureDataQueryRepositoryTest {

    @Autowired
    DataLoggerRepository dataLoggerRepository;

    @Autowired
    MeasureDataRepository measureDataRepository;

    @Autowired
    MeasureDataQueryRepository measureDataQueryRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void beforeEach() throws InterruptedException {
        DataLogger dataLogger = DataLogger.create("dataLogger", null);
        dataLoggerRepository.save(dataLogger);

        for (int i = 0; i < 5; i++) {
            Thread.sleep(10);
            MeasureData measureData = MeasureData.builder()
                    .data("1234")
                    .dataLogger(dataLogger)
                    .build();
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

        assertThat(dataDtos.size()).isEqualTo(5);
    }
}