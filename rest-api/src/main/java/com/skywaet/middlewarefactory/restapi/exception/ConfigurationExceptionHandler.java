package com.skywaet.middlewarefactory.restapi.exception;

import com.skywaet.middlewarefactory.restapi.exception.notfound.BaseNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class ConfigurationExceptionHandler {
    @ExceptionHandler({BaseNotFoundException.class})
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    public String handleNotFoundException(BaseNotFoundException exception) {
        return exception.getMessage();

    }
}
