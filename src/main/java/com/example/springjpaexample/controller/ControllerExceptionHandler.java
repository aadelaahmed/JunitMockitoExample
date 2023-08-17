package com.example.springjpaexample.controller;

import com.example.springjpaexample.exceptions.CustomMethodArgumentNotValidException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleEntityAlreadyExistException(
            EntityExistsException exc
    ){
        return new ResponseEntity<>(exc.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String,String> handleValidationException(
            MethodArgumentNotValidException exception
    ){
        Map<String,String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String field = error.getObjectName();
            String errorMsg = error.getDefaultMessage();
            errors.put(field,errorMsg);
        });
        return errors;
    }
   /* @ExceptionHandler(CustomMethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String,String> handleCustomMethodArgumentNotValidException(
            CustomMethodArgumentNotValidException exc
    ){
        Map<String,String> errors = new HashMap<>();
        exc.getBindingResult().getAllErrors().forEach(error -> {
            String field = error.getObjectName();
            String errorMsg = error.getDefaultMessage();
            errors.put(field,errorMsg);
        });
        return errors;
    }
   */
   @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleEntityNotFoundException(
            EntityNotFoundException exc
    ){
        return new ResponseEntity<>(exc.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
