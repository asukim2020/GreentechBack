package kr.co.greetech.back.controller;

import kr.co.greetech.back.dto.MeasureDataDto;
import kr.co.greetech.back.service.MeasureDataService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("measureData")
public class MeasureDataController {

    private final MeasureDataService measureDataService;

    @PostMapping("/{dataLoggerId}")
    public Long add(
            @PathVariable Long dataLoggerId,
            @RequestBody List<MeasureDataDto> measureDataDtos
    ) {
        return measureDataService.addMeasureDataDtos(dataLoggerId, measureDataDtos);
    }

    @GetMapping
    public Result<List<MeasureDataDto>> select(
            @RequestParam Long dataLoggerId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") LocalDateTime end
    ) {
        List<MeasureDataDto> dataDtos = measureDataService.select(dataLoggerId, start, end);
        return new Result<>(dataDtos.size(), dataDtos);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }
}
