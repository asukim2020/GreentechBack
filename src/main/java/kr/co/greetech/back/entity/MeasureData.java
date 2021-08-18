package kr.co.greetech.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class MeasureData {
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

    public void setDataLogger(DataLogger dataLogger) {
        this.dataLogger = dataLogger;
    }
}
