package com.student.exception;

import com.student.dto.ResponseWrapper;
import com.student.enums.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleExceptionHandler {
    @ExceptionHandler(StudentException.class)
    public ResponseEntity<ResponseWrapper> handleContactException(StudentException exception) {
        ResponseWrapper responseWrapper = new ResponseWrapper(Status.FAIL, exception.getMessage());
        return new ResponseEntity<>(responseWrapper, HttpStatus.NOT_FOUND);
    }
}
