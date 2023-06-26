package com.simpleAccountService.exception;

import com.simpleAccountService.dto.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.simpleAccountService.type.ErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountException.class)
    public ErrorResponse handleAccountException(AccountException accountException){
        log.error("{} is occurred.", accountException.getErrorCode());
        return new ErrorResponse(
                accountException.getErrorCode(),
                accountException.getErrorMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e){
        log.error("Exception is occurred.", e);

        return new ErrorResponse(
                INTERNAL_SERVER_ERROR,
                INTERNAL_SERVER_ERROR.getDescription()
        );
    }
}
