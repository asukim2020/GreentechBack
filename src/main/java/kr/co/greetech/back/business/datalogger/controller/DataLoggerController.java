package kr.co.greetech.back.business.datalogger.controller;

import kr.co.greetech.back.annotation.Auth;
import kr.co.greetech.back.business.datalogger.type.DataLoggerType;
import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.dto.DataLoggerReadDto;
import kr.co.greetech.back.business.datalogger.service.DataLoggerService;
import kr.co.greetech.back.util.ExceptionMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kr.co.greetech.back.annotation.Auth.Role.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dataLogger")
@CrossOrigin
public class DataLoggerController {

    private final DataLoggerService dataLoggerService;

    @PostMapping("/{companyId}")
    public Long create(
            @PathVariable Long companyId,
            @Validated DataLoggerCreateDto dataLoggerCreateDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            log.warn("error={} ", bindingResult);
            throw new IllegalArgumentException(ExceptionMsg.bindingMsg(bindingResult));
        }

        return dataLoggerService.register(companyId, dataLoggerCreateDto);
    }

    @PostMapping("update/{companyId}")
    public Long update(
            @Validated DataLoggerReadDto dataLoggerReadDto,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            log.warn("error={} ", bindingResult);
            throw new IllegalArgumentException(ExceptionMsg.bindingMsg(bindingResult));
        }

        return dataLoggerService.update(dataLoggerReadDto);
    }

    @PostMapping("updateBody/{companyId}")
    public Long updateBody(
            @Validated @RequestBody DataLoggerReadDto dataLoggerReadDto,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            log.warn("error={} ", bindingResult);
            throw new IllegalArgumentException(ExceptionMsg.bindingMsg(bindingResult));
        }

        return dataLoggerService.update(dataLoggerReadDto);
    }

    @Auth(role = NONE)
    @GetMapping
    public DataLoggerReadDto one(@RequestParam Long dataLoggerId) {
        return dataLoggerService.findById(dataLoggerId);
    }

    @GetMapping("/{companyId}")
    public List<DataLoggerReadDto> list(@PathVariable Long companyId) {
        return dataLoggerService.findByCompanyId(companyId);
    }

    @GetMapping("type/{companyId}")
    public List<DataLoggerReadDto> listType(
            @PathVariable Long companyId,
            @RequestParam String type
    ) {
        DataLoggerType dataLoggerType = DataLoggerType.valueOf(type);
        return dataLoggerService.findByCompanyIdAndType(companyId, dataLoggerType);
    }
}
