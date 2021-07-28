package by.nintendo.clevertec.controller;

import by.nintendo.clevertec.exception.NewsNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NewsNotFoundException.class)
    public String newsNotFound(RuntimeException ex) {
        log.warn("NewsNotFoundException: {}",ex.getMessage());
        return ex.getMessage();
    }

}