package kr.co.greetech.back.service;

import kr.co.greetech.back.dto.DataLoggerDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.entity.DataLogger;
import kr.co.greetech.back.repository.CompanyRepository;
import kr.co.greetech.back.repository.DataLoggerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataLoggerService {

    private final CompanyRepository companyRepository;
    private final DataLoggerRepository dataLoggerRepository;


    public Long register(Long companyId, DataLoggerDto dataLoggerDto) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        optionalCompany.orElseThrow(() -> new IllegalArgumentException("can't found data"));

        Company company = optionalCompany.get();
        DataLogger dataLogger = DataLogger.builder()
                .company(company)
                .modelName(dataLoggerDto.getModelName())
                .build();
        DataLogger savedDataLogger = dataLoggerRepository.save(dataLogger);
        return savedDataLogger.getId();
    }

    public DataLoggerDto findById(Long dataLoggerId) {
        Optional<DataLogger> optionalDataLogger = dataLoggerRepository.findById(dataLoggerId);
        optionalDataLogger.orElseThrow(() -> new IllegalArgumentException("can't found data"));

        DataLogger dataLogger = optionalDataLogger.get();
        return new DataLoggerDto(
                dataLogger.getId(),
                dataLogger.getModelName()
        );
    }

    private DataLogger findEntityById(Long dataLoggerId) {
        Optional<DataLogger> optionalDataLogger = dataLoggerRepository.findById(dataLoggerId);
        optionalDataLogger.orElseThrow(() -> new IllegalArgumentException("can't found data"));
        return optionalDataLogger.get();
    }

    public Long update(DataLoggerDto dataLoggerDto) {
        DataLogger dataLogger = findEntityById(dataLoggerDto.getId());
        // TODO:- 수정로직 추가 - Entity 내부에 수정함수 정의 -> 어떻게 수정할 건지 정해지고 난 이후에 추가 할 것
        return dataLogger.getId();
    }
}
