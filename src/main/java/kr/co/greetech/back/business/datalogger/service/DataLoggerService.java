package kr.co.greetech.back.business.datalogger.service;

import kr.co.greetech.back.business.datalogger.type.DataLoggerType;
import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.dto.DataLoggerReadDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.entity.DataLogger;
import kr.co.greetech.back.business.login.jwt.repository.CompanyRepository;
import kr.co.greetech.back.business.datalogger.repository.DataLoggerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataLoggerService {

    private final CompanyRepository companyRepository;
    private final DataLoggerRepository dataLoggerRepository;


    public Long register(Long companyId, DataLoggerCreateDto dataLoggerDto) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        optionalCompany.orElseThrow(() -> new IllegalArgumentException("can't found data"));

        Company company = optionalCompany.get();
        DataLogger dataLogger = DataLogger.create(
                dataLoggerDto,
                company
        );
        DataLogger savedDataLogger = dataLoggerRepository.save(dataLogger);
        return savedDataLogger.getId();
    }

    @Transactional
    public Long update(DataLoggerReadDto dataLoggerReadDto) {
        DataLogger dataLogger = findEntityById(dataLoggerReadDto.getId());
        dataLogger.update(dataLoggerReadDto);
        return dataLogger.getId();
    }

    public List<DataLoggerReadDto> findByCompanyId(Long companyId) {
        List<DataLogger> dataLoggers = dataLoggerRepository.findByCompanyId(companyId);
        return dataLoggers.stream()
                .map(DataLoggerReadDto::new)
                .collect(Collectors.toList());
    }

    public List<DataLoggerReadDto> findByCompanyIdAndType(Long companyId, DataLoggerType type) {
        List<DataLogger> dataLoggers = dataLoggerRepository.findByCompanyIdAndType(companyId, type);
        return dataLoggers.stream()
                .map(DataLoggerReadDto::new)
                .collect(Collectors.toList());
    }

    public DataLoggerReadDto findById(Long dataLoggerId) {
        Optional<DataLogger> optionalDataLogger = dataLoggerRepository.findById(dataLoggerId);
        optionalDataLogger.orElseThrow(() -> new IllegalArgumentException("can't found data"));

        DataLogger dataLogger = optionalDataLogger.get();
        return new DataLoggerReadDto(dataLogger);
    }

    private DataLogger findEntityById(Long dataLoggerId) {
        Optional<DataLogger> optionalDataLogger = dataLoggerRepository.findById(dataLoggerId);
        optionalDataLogger.orElseThrow(() -> new IllegalArgumentException("can't found data"));
        return optionalDataLogger.get();
    }
}
