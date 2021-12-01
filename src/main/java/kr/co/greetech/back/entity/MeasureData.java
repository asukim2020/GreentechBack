package kr.co.greetech.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.greetech.back.Auditing.CreateTimeEntity;
import kr.co.greetech.back.dto.MeasureDataDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(exclude = {"dataLogger"})
public class MeasureData {
    @Id
    @GeneratedValue
    @Column(name = "MEASURE_DATA_ID")
    Long id;

    @NotNull(message = "MeasureData dataLogger")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DATA_LOGGER_ID")
    @JsonIgnore
    DataLogger dataLogger;

    @NotNull(message = "MeasureData data")
    @Column(nullable = false)
    String data;

    Long groupCount;

    @NotNull(message = "MeasureData createdTime")
    @Column(updatable = false)
    private LocalDateTime createdTime;

    public static MeasureData create(MeasureDataDto measureDataDto, DataLogger dataLogger) {
        MeasureData measureData = new MeasureData();
        measureData.id = null;
        measureData.data = measureDataDto.getData();
        measureData.dataLogger = dataLogger;
        measureData.createdTime = measureDataDto.getTime();
        return measureData;
    }

    public static MeasureData create(MeasureDataDto measureDataDto, Long count, int index, DataLogger dataLogger) {
        MeasureData measureData = new MeasureData();
        measureData.id = null;
        measureData.data = measureDataDto.getData();
        measureData.dataLogger = dataLogger;
        measureData.createdTime = measureDataDto.getTime();
        measureData.groupCount = count + index;
        return measureData;
    }
}
