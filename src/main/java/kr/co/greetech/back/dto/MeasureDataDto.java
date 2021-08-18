package kr.co.greetech.back.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeasureDataDto {
    String data;

    @QueryProjection
    public MeasureDataDto(String data) {
        this.data = data;
    }
}
