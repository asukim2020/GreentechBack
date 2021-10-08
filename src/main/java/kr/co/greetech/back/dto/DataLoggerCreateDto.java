package kr.co.greetech.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class DataLoggerCreateDto {

    @NotNull(message = "DataLoggerCreateDto modelName")
    @Size(min = 4, max = 255)
    String modelName;

    @NotNull(message = "DataLoggerCreateDto unit")
    String unit;

    @NotNull(message = "DataLoggerCreateDto channelName")
    String channelName;
}
