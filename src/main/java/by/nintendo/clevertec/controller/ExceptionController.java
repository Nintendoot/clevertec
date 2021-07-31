package by.nintendo.clevertec.controller;

import by.nintendo.clevertec.exception.CommentNotFoundException;
import by.nintendo.clevertec.exception.NewsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller for intercepting exceptions
 */

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NewsNotFoundException.class)
    public String newsNotFound(RuntimeException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public String commentNotFound(RuntimeException ex) {
        return ex.getMessage();
    }

}
