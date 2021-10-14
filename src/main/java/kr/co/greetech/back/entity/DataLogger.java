package kr.co.greetech.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.greetech.back.Auditing.BaseTimeEntity;
import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.dto.DataLoggerReadDto;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

//    Boolean isUpdate;

    public static DataLogger create(DataLoggerCreateDto dataLoggerCreateDto, Company company) {
        DataLogger dataLogger = new DataLogger();
        dataLogger.id = null;
        dataLogger.modelName = dataLoggerCreateDto.getModelName();
        dataLogger.company = company;
        dataLogger.unit = dataLoggerCreateDto.getUnit();
        dataLogger.channelName = dataLoggerCreateDto.getChannelName();

        return dataLogger;
    }

    public void update(DataLoggerReadDto dataLoggerReadDto) {
        if (dataLoggerReadDto.getModelName() != null
                && !dataLoggerReadDto.getModelName().equals("null")) {
            this.modelName = dataLoggerReadDto.getModelName();
        }

        if (dataLoggerReadDto.getUnit() != null
                && !dataLoggerReadDto.getUnit().equals("null")) {
            this.unit = dataLoggerReadDto.getUnit();
        }

        if (dataLoggerReadDto.getChannelName() != null
                && !dataLoggerReadDto.getChannelName().equals("null")) {
            this.channelName = dataLoggerReadDto.getChannelName();
        }
    }
}
