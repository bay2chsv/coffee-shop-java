package com.example.restfulapi.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(BadExecption.class)
    public ResponseEntity<Errorrespone> handleBaseExceptioin(BadExecption e){
        Errorrespone errorrespone = new Errorrespone();
        errorrespone.setMessage(e.getMessage());
        return new ResponseEntity<>(errorrespone, HttpStatus.BAD_REQUEST);
    }
    @Getter
    @Setter
    public static class Errorrespone{
        private LocalDate time = LocalDate.from(LocalDateTime.now());
        private String message;
    }
}
