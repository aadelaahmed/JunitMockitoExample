package com.example.springjpaexample.exceptions;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class CustomMethodArgumentNotValidException extends RuntimeException{
    BindingResult bindingResult;
    public CustomMethodArgumentNotValidException(BindingResult bindingResult){
        this.bindingResult = bindingResult;
    }
}
