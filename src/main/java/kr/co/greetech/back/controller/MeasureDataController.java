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
@CrossOrigin
public class MeasureDataController {

    private final MeasureDataService measureDataService;

    @PostMapping("/{dataLoggerId}")
    public Long add(
            @PathVariable Long dataLoggerId,
            @RequestBody List<MeasureDataDto> measureDataDtos
    ) {
        return measureDataService.addMeasureDataDtos(dataLoggerId, measureDataDtos);
    }

    @GetMapping("/{dataLoggerId}")
    public Result<List<MeasureDataDto>> select(
            @PathVariable Long dataLoggerId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") LocalDateTime from,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") LocalDateTime to
    ) {
        List<MeasureDataDto> dataDtos = measureDataService.select(dataLoggerId, from, to);
        return new Result<>(dataDtos.size(), dataDtos);
    }

    // TODO: - test 코드 => 지울 것
    @GetMapping("/all/{dataLoggerId}")
    public Result<List<MeasureDataDto>> selectAll(
            @PathVariable Long dataLoggerId
    ) {
        List<MeasureDataDto> dataDtos = measureDataService.selectAll(dataLoggerId);
        return new Result<>(dataDtos.size(), dataDtos);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }
}
