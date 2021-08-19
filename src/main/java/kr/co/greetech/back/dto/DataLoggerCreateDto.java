package kr.co.greetech.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class DataLoggerCreateDto {

    @Size(min = 4, max = 255)
    String modelName;
}
