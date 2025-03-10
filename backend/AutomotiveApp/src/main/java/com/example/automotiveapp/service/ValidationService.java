package com.example.automotiveapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ValidationService {

    private ValidationService() {
    }

    private static class ValidationServiceHolder {
        private static final ValidationService INSTANCE = new ValidationService();
    }

    public static ValidationService getInstance() {
        return ValidationServiceHolder.INSTANCE;
    }

    public ResponseEntity<?> validate(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorsMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}

