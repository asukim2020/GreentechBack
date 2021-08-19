package kr.co.greetech.back.dto;

import kr.co.greetech.back.entity.DataLogger;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataLoggerReadDto {
    Long id;
    String modelName;

    public DataLoggerReadDto(DataLogger dataLogger) {
        id = dataLogger.getId();
        modelName = dataLogger.getModelName();
    }
}
