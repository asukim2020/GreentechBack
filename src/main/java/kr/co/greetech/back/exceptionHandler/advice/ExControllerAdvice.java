package kr.co.greetech.back.exceptionHandler.advice;

import kr.co.greetech.back.exceptionHandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult illegalArgumentExHandler(IllegalArgumentException e) {
        log.info("[illegalArgumentExHandler] ex: ", e);
        return new ErrorResult(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult usernameNotFoundExHandler(UsernameNotFoundException e) {
        log.info("[usernameNotFoundExHandler] ex: ", e);
        return new ErrorResult(HttpStatus.UNAUTHORIZED.toString(), e.getMessage());
    }
}
