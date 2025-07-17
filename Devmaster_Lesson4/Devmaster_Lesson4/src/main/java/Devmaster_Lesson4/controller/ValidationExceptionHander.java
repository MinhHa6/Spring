package Devmaster_Lesson4.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHander {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handmethod(MethodArgumentNotValidException ex){
        Map<String,String>err= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fielName = ((FieldError)error).getField();
            String errMess=error.getDefaultMessage();
            err.put(fielName,errMess);
        });
        return  new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}
