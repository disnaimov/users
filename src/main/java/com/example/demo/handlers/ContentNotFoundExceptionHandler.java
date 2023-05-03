package com.example.demo.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ContentNotFoundExceptionHandler extends ResponseEntityExceptionHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    protected ResponseEntity<Object> handleEntityNotFoundException(RuntimeException ex, WebRequest request){
        AwesomeException awesomeException = new AwesomeException("CONTENT_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(awesomeException, HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class AwesomeException{
        private String error_code;
        private String content;
    }

}
