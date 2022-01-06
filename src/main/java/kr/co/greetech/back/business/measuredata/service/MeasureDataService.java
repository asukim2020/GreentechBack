package kr.co.greetech.back.business.measuredata.service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import kr.co.greetech.back.FcmConfig;
import kr.co.greetech.back.business.login.jwt.repository.FcmTokenRepository;
import kr.co.greetech.back.dto.MeasureDataDto;
import kr.co.greetech.back.entity.DataLogger;
import kr.co.greetech.back.entity.FcmToken;
import kr.co.greetech.back.entity.MeasureData;
import kr.co.greetech.back.business.datalogger.repository.DataLoggerRepository;
import kr.co.greetech.back.business.measuredata.repository.MeasureDataQueryRepository;
import kr.co.greetech.back.business.measuredata.repository.MeasureDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeasureDataService {

    private final DataLoggerRepository dataLoggerRepository;
    private final MeasureDataRepository measureDataRepository;
    private final MeasureDataQueryRepository measureDataQueryRepository;
    private final FcmTokenRepository fcmTokenRepository;

    private final FcmConfig fireConfig;

    public Long addMeasureDataDtos(Long dataLoggerId, List<MeasureDataDto> measureDataDtos) {
        Optional<DataLogger> optionalDataLogger = dataLoggerRepository.findById(dataLoggerId);
        optionalDataLogger.orElseThrow(() -> new IllegalArgumentException("can't found data"));

        DataLogger dataLogger = optionalDataLogger.get();
        long count = measureDataQueryRepository.getGroupCount(dataLoggerId);

        List<MeasureData> measureDatas = IntStream.range(0, measureDataDtos.size())
                .mapToObj(i -> MeasureData.create(measureDataDtos.get(i), count, i, dataLogger))
                .collect(Collectors.toList());

//        List<MeasureData> measureDatas = measureDataDtos.stream()
//                .map(measureDataDto -> MeasureData.create(measureDataDto, dataLogger))
//                .collect(Collectors.toList());

        measureDataRepository.saveAll(measureDatas);
        Long companyId = dataLogger.getCompany().getId();
        sendFcm(companyId, dataLogger.getId());

        return dataLoggerId;
    }

    public List<MeasureDataDto> select(
            Long dataLoggerId,
            LocalDateTime from,
            LocalDateTime to
    ) {
        return measureDataQueryRepository.search(
                dataLoggerId,
                from,
                to
        );
    }

    public List<MeasureDataDto> download(
            Long dataLoggerId,
            LocalDateTime from,
            LocalDateTime to,
            Long offset
    ) {
        return measureDataQueryRepository.download(
                dataLoggerId,
                from,
                to,
                offset
        );
    }

    private void sendFcm(Long companyId, Long dataLoggerId) {
        List<FcmToken> fcmTokenList = fcmTokenRepository.findAllByCompanyId(companyId);
        if (fcmTokenList.isEmpty()) return;

        ArrayList<String> tokens = new ArrayList<>();
        for (FcmToken fcmToken : fcmTokenList) {
            tokens.add(fcmToken.getFcmToken());
        }

        MulticastMessage multicastMessage = MulticastMessage.builder()
                .addAllTokens(tokens)
                .putData("title", dataLoggerId.toString())
                .putData("message", "update data")
                .build();

        try {
            BatchResponse multicastResponse = FirebaseMessaging.getInstance(fireConfig.firebaseApp()).sendMulticast(multicastMessage);
            log.info("fcm multicastResponse: " + multicastResponse);
        } catch (FirebaseMessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<MeasureDataDto> last(Long dataLoggerId, int count) {
        return measureDataQueryRepository.last(dataLoggerId, count);
    }

    // TODO: - test 코드 => 지울 것
    public List<MeasureDataDto> selectAll(
            Long dataLoggerId
    ) {
        return measureDataQueryRepository.searchAll(dataLoggerId);
    }
}
