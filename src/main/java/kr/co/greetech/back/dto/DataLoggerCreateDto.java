package kr.co.greetech.back.dto;

import kr.co.greetech.back.business.datalogger.type.DataLoggerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class DataLoggerCreateDto {

    @NotNull(message = "DataLoggerCreateDto modelName")
    @Size(min = 4, max = 255)
    String modelName;

    @NotNull(message = "DataLoggerCreateDto unit")
    String unit;

    @NotNull(message = "DataLoggerCreateDto channelName")
    String channelName;

    DataLoggerType type;

    String ip;

    String request;

    String trigger;

    public DataLoggerCreateDto(String modelName, String unit, String channelName) {
        this.modelName = modelName;
        this.unit = unit;
        this.channelName = channelName;
        this.type = DataLoggerType.STATIC;
        this.ip = "";
        this.request = "";
        this.trigger = "";
    }

    public DataLoggerCreateDto(String modelName, String unit, String channelName, String type) {
        this.modelName = modelName;
        this.unit = unit;
        this.channelName = channelName;
        this.ip = "";
        switch (type) {
            case "dynamic":
            case "DYNAMIC":
                this.type = DataLoggerType.DYNAMIC;
                break;

            case "all":
            case "ALL":
                this.type = DataLoggerType.ALL;
                break;

            default:
                this.type = DataLoggerType.STATIC;
                break;
        }
    }
}
