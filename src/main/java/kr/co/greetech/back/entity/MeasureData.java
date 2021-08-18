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
@Builder
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

    public MeasureData(MeasureDataDto measureDataDto) {
        data = measureDataDto.getData();
    }

    public void setDataLogger(DataLogger dataLogger) {
        this.dataLogger = dataLogger;
    }
}
