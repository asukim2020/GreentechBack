package kr.co.greetech.back.controller;

import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.dto.MeasureDataDto;
import kr.co.greetech.back.service.CompanyService;
import kr.co.greetech.back.service.DataLoggerService;
import kr.co.greetech.back.service.MeasureDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitData {

    private final InitDataService initDataService;

    @PostConstruct
    public void init() {
        initDataService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitDataService {

        private final CompanyService companyService;
        private final DataLoggerService dataLoggerService;
        private final MeasureDataService measureDataService;

        @Transactional
        public void init() {
            Long companyId = companyService.create(new CompanyCreateDto("Asu", "test", "test!"));

            List<Long> dataLoggerIds = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Long dataLoggerId = dataLoggerService.register(companyId, new DataLoggerCreateDto("dataLogger" + (i + 1)));
                dataLoggerIds.add(dataLoggerId);
            }

            for (Long dataLoggerId : dataLoggerIds) {
                List<MeasureDataDto> dataDtos = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    MeasureDataDto measureDataDto = new MeasureDataDto("data" + (i + 1), LocalDateTime.now());
                    dataDtos.add(measureDataDto);
                }
                measureDataService.addMeasureDataDtos(dataLoggerId, dataDtos);
            }
        }
    }

}
