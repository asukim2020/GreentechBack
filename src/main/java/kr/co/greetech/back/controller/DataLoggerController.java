package kr.co.greetech.back.controller;

import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.dto.DataLoggerReadDto;
import kr.co.greetech.back.service.DataLoggerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dataLogger")
public class DataLoggerController {

    private final DataLoggerService dataLoggerService;

    @PostMapping("/{companyId}")
    public Long create(
            @PathVariable Long companyId,
            @Validated DataLoggerCreateDto dataLoggerCreateDto
    ) {
        return dataLoggerService.register(companyId, dataLoggerCreateDto);
    }

    @GetMapping
    public DataLoggerReadDto one(@RequestParam Long dataLoggerId) {
        return dataLoggerService.findById(dataLoggerId);
    }

    @GetMapping("/{companyId}")
    public List<DataLoggerReadDto> list(@PathVariable Long companyId) {
        return dataLoggerService.findByCompanyId(companyId);
    }
}
