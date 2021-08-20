package kr.co.greetech.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.greetech.back.Auditing.BaseTimeEntity;
import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.dto.DataLoggerReadDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class DataLogger extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "DATA_LOGGER_ID")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    @JsonIgnore
    Company company;

    String modelName;

//    Boolean isUpdate;

    public static DataLogger create(DataLoggerCreateDto dataLoggerCreateDto, Company company) {
        DataLogger dataLogger = new DataLogger();
        dataLogger.id = null;
        dataLogger.modelName = dataLoggerCreateDto.getModelName();
        dataLogger.company = company;

        return dataLogger;
    }
}
