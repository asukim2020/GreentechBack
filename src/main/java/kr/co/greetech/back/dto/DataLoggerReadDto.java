package kr.co.greetech.back.dto;

import kr.co.greetech.back.entity.DataLogger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataLoggerReadDto {
    Long id;
    String modelName;
    String unit;
    String channelName;

    public DataLoggerReadDto(DataLogger dataLogger) {
        id = dataLogger.getId();
        modelName = dataLogger.getModelName();
        unit = dataLogger.getUnit();
        channelName = dataLogger.getChannelName();
    }
}
