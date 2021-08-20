package kr.co.greetech.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.greetech.back.Auditing.CreateTimeEntity;
import kr.co.greetech.back.dto.MeasureDataDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(exclude = {"dataLogger"})
public class MeasureData extends CreateTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "MEASURE_DATA_ID")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DATA_LOGGER_ID")
    @JsonIgnore
    DataLogger dataLogger;

    @Column(nullable = false)
    String data;

    public static MeasureData create(MeasureDataDto measureDataDto, DataLogger dataLogger) {
        MeasureData measureData = new MeasureData();
        measureData.data = measureDataDto.getData();
        measureData.dataLogger = dataLogger;
        return measureData;
    }
}
