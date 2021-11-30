package kr.co.greetech.back.dto;

import com.querydsl.core.annotations.QueryProjection;
import kr.co.greetech.back.business.file.MeasureFileType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MeasureFileDto {

    @NotNull(message = "MeasureFileDto time")
    private LocalDateTime time;

    @NotNull(message = "MeasureFileDto url")
    private String url;

    @NotNull(message = "MeasureFileDto type")
    private MeasureFileType type;

    @QueryProjection
    public MeasureFileDto(LocalDateTime time, String url, MeasureFileType type) {
        this.time = time;
        this.url = url;
        this.type = type;
    }
}
