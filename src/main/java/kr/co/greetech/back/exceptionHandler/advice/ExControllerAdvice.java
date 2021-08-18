package kr.co.greetech.back.exceptionHandler.advice;

import kr.co.greetech.back.exceptionHandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgumentExHandler(IllegalArgumentException e) {
        log.error("[illegalArgumentExHandler] ex", e);
        return new ErrorResult("illegal argument", e.getMessage());
    }

}
