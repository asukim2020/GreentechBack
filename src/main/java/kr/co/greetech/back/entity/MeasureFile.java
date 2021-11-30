package kr.co.greetech.back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.greetech.back.Auditing.CreateTimeEntity;
import kr.co.greetech.back.business.file.MeasureFileType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "MEASURE_FILE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeasureFile extends CreateTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "MEASURE_FILE_ID")
    Long id;

    @NotNull(message = "MeasureFile company")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    @JsonIgnore
    Company company;

    @NotNull(message = "MeasureFile dataLogger")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DATA_LOGGER_ID")
    @JsonIgnore
    DataLogger dataLogger;

    String url;

    @Enumerated(EnumType.STRING)
    MeasureFileType type;

    public static MeasureFile create(
            String url,
            MeasureFileType type,
            Company company,
            DataLogger dataLogger
    ) {
        MeasureFile measureFile = new MeasureFile();
        measureFile.url = url;
        measureFile.type = type;
        measureFile.company = company;
        measureFile.dataLogger = dataLogger;
        return measureFile;
    }
}
