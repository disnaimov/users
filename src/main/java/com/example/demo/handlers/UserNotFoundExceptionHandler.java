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

//Не message a error code
//типизированный ответ = content: и в нем уже этот юзер лист
//6d5ba80a-54d7-4b74-b676-c53677083411
@ControllerAdvice
public class UserNotFoundExceptionHandler extends ResponseEntityExceptionHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    protected ResponseEntity<Object> handleEntityNotFoundException(RuntimeException ex, WebRequest request){
        AwesomeException awesomeException = new AwesomeException("USER_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(awesomeException, HttpStatus.NOT_FOUND);
    }

   /* @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected ResponseEntity<Object> handleBadRequestException(RuntimeException ex, WebRequest request){
        AwesomeException awesomeException = new AwesomeException("BAD_ID", ex.getMessage());
        return new ResponseEntity<>(awesomeException, HttpStatus.BAD_REQUEST);
    }*/


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class AwesomeException{
        private String error_code;
        private String content;
    }

}
