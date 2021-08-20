package kr.co.greetech.back.service;

import kr.co.greetech.back.dto.MeasureDataDto;
import kr.co.greetech.back.entity.DataLogger;
import kr.co.greetech.back.entity.MeasureData;
import kr.co.greetech.back.repository.DataLoggerRepository;
import kr.co.greetech.back.repository.MeasureDataQueryRepository;
import kr.co.greetech.back.repository.MeasureDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeasureDataService {

    private final DataLoggerRepository dataLoggerRepository;
    private final MeasureDataRepository measureDataRepository;
    private final MeasureDataQueryRepository measureDataQueryRepository;

    public Long addMeasureDataDtos(Long dataLoggerId, List<MeasureDataDto> measureDataDtos) {
        Optional<DataLogger> optionalDataLogger = dataLoggerRepository.findById(dataLoggerId);
        optionalDataLogger.orElseThrow(() -> new IllegalArgumentException("can't found data"));

        DataLogger dataLogger = optionalDataLogger.get();
        List<MeasureData> measureDatas = measureDataDtos.stream()
                .map(measureDataDto -> MeasureData.create(measureDataDto, dataLogger))
                .collect(Collectors.toList());

        for (MeasureData measureData : measureDatas) {
            measureDataRepository.save(measureData);
        }

        return dataLoggerId;
    }

    public List<MeasureDataDto> select(
            Long dataLoggerId,
            LocalDateTime start,
            LocalDateTime end
    ) {
        return measureDataQueryRepository.search(
                dataLoggerId,
                start,
                end
        );
    }
}
