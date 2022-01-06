package kr.co.greetech.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.greetech.back.Auditing.BaseTimeEntity;
import kr.co.greetech.back.business.datalogger.type.DataLoggerType;
import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.dto.DataLoggerReadDto;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class DataLogger extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "DATA_LOGGER_ID")
    Long id;

    @NotNull(message = "DataLogger company")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    @JsonIgnore
    Company company;

    @NotNull(message = "DataLogger modelName")
    String modelName;

    @NotNull(message = "DataLogger unit")
    @Column(length = 1000)
    String unit;

    @NotNull(message = "DataLogger channelName")
    @Column(length = 1000)
    String channelName;

    @Enumerated(EnumType.STRING)
    DataLoggerType type;

    @Column(length = 50)
    String ip;

//    Boolean isUpdate;

    public static DataLogger create(DataLoggerCreateDto dataLoggerCreateDto, Company company) {
        DataLogger dataLogger = new DataLogger();
        dataLogger.id = null;
        dataLogger.modelName = dataLoggerCreateDto.getModelName();
        dataLogger.company = company;
        dataLogger.unit = dataLoggerCreateDto.getUnit();
        dataLogger.channelName = dataLoggerCreateDto.getChannelName();
        dataLogger.type = dataLoggerCreateDto.getType();
        dataLogger.ip = dataLoggerCreateDto.getIp();

        return dataLogger;
    }

    public void update(DataLoggerReadDto dataLoggerReadDto) {
        String modelName = dataLoggerReadDto.getModelName();
        if (modelName != null && !modelName.equals("null")) {
            this.modelName = modelName;
        }

        String unit = dataLoggerReadDto.getUnit();
        if (unit != null && !unit.equals("null")) {
            this.unit = unit;
        }

        String channelName = dataLoggerReadDto.getChannelName();
        if (channelName != null && !channelName.equals("null")) {
            this.channelName = channelName;
        }

        String ip = dataLoggerReadDto.getIp();
        if (ip != null && !ip.equals("null")) {
            this.ip = ip;
        }
    }
}
