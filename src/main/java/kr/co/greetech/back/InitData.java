package kr.co.greetech.back;

import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.dto.MeasureDataDto;
import kr.co.greetech.back.business.login.jwt.service.JwtUserDetailsService;
import kr.co.greetech.back.business.datalogger.service.DataLoggerService;
import kr.co.greetech.back.business.measuredata.service.MeasureDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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
//        TODO: - 서버 올릴 시에 해당 부분 제거하기
//        initDataService.init();
    }

    @Profile("local")
    @Component
    @RequiredArgsConstructor
    static class InitDataService {

        private final JwtUserDetailsService jwtUserDetailsService;
        private final DataLoggerService dataLoggerService;
        private final MeasureDataService measureDataService;

        @Transactional
        public void init() {
            Long companyId = jwtUserDetailsService.create(new CompanyCreateDto("Asu", "test", "test!"));

            List<Long> dataLoggerIds = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                StringBuilder unit = new StringBuilder();
                StringBuilder channel = new StringBuilder();
                for(int j = 0; j < 10; j++){
                    if (j != 0) {
                        unit.append(",");
                        channel.append(",");
                    }
                    unit.append(i + 1);
                    channel.append(i + 1);
                }
                Long dataLoggerId = dataLoggerService.register(companyId, new DataLoggerCreateDto("dataLogger" + (i + 1) + "(클릭)", unit.toString(), ""));
                dataLoggerIds.add(dataLoggerId);
            }

            int count = 0;
            for (Long dataLoggerId : dataLoggerIds) {
                List<MeasureDataDto> dataDtos = new ArrayList<>();
                for (int i = 0; i < 50; i++) {
                    ArrayList<String> datas = new ArrayList<>();
                    for (int j = 0; j < 5 - count; j++) {
                        int data = (int) (Math.random() * 30);
                        datas.add(Integer.toString(data));
                    }
                    LocalDateTime date = LocalDateTime.now();
                    date = date.minusNanos(date.getNano());
                    date = date.minusMinutes(date.getMinute());
                    date = date.minusSeconds(date.getSecond());
                    date = date.minusHours(i * 2);
                    MeasureDataDto measureDataDto = new MeasureDataDto(String.join(",", datas), date);
                    dataDtos.add(measureDataDto);
                }
                measureDataService.addMeasureDataDtos(dataLoggerId, dataDtos);
                count++;
            }

            Long companyId2 = jwtUserDetailsService.create(new CompanyCreateDto("NGI", "ngi", "ngi!"));

            List<Long> dataLoggerIds2 = new ArrayList<>();
            StringBuilder unit = new StringBuilder();
            for(int j = 0; j < 1; j++){
                if (j != 0) {
                    unit.append(",");
                }
                unit.append(1);
            }
            Long dataLoggerId2 = dataLoggerService.register(companyId2, new DataLoggerCreateDto("데이터로거", unit.toString(), ""));
            dataLoggerIds2.add(dataLoggerId2);

            List<MeasureDataDto> dataDtos2 = new ArrayList<>();
            for (int i = 0; i < 1; i++) {
                ArrayList<String> datas = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    int data = (int) (Math.random() * 30);
                    datas.add(Integer.toString(data));
                }
                LocalDateTime date = LocalDateTime.now();
                date = date.minusNanos(date.getNano());
                date = date.minusMinutes(date.getMinute());
                date = date.minusSeconds(date.getSecond());
                date = date.minusHours(i * 2);
                MeasureDataDto measureDataDto = new MeasureDataDto(String.join(",", datas), date);
                dataDtos2.add(measureDataDto);
            }
            measureDataService.addMeasureDataDtos(dataLoggerId2, dataDtos2);
        }
    }

}
