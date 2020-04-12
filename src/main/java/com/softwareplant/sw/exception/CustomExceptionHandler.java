package com.softwareplant.sw.exception;

import com.softwareplant.sw.exception.customErrors.ExternalApiException;
import com.softwareplant.sw.exception.customErrors.QueryReturnedNoDataException;
import com.softwareplant.sw.exception.customErrors.SingleRecordNotFoundException;
import com.softwareplant.sw.exception.customErrors.MultipleRecordsNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ErrorDetails(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex){
        String error = "INTERNAL_SERVER_ERROR";
    return buildResponseEntity(new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    @ExceptionHandler(QueryReturnedNoDataException.class)
    public ResponseEntity<Object> handleQueryReturnedNoDataException(QueryReturnedNoDataException e){
        return buildResponseEntity(new ErrorDetails(HttpStatus.NOT_FOUND, e.getMessage(), e));
    }

    @ExceptionHandler(SingleRecordNotFoundException.class)
    public ResponseEntity<Object> handleRecordNotFoundException(SingleRecordNotFoundException e){
        return buildResponseEntity(new ErrorDetails(HttpStatus.NOT_FOUND, e.getMessage(), e));
    }

    @ExceptionHandler(MultipleRecordsNotFoundException.class)
    public ResponseEntity<Object> handleRecordsNotFoundException(MultipleRecordsNotFoundException e){
        return buildResponseEntity(new ErrorDetails(HttpStatus.NOT_FOUND, e.getMessage(), e));
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<Object> handleExternalApiException(ExternalApiException e) {
        return buildResponseEntity(new ErrorDetails(HttpStatus.NO_CONTENT, e.getMessage(), e));
    }

    @ExceptionHandler(ResourceAccessException.class)
    public  ResponseEntity<Object> handleResourceAccessException(ResourceAccessException e) {
        return buildResponseEntity(new ErrorDetails(HttpStatus.NO_CONTENT, e.getMessage(), e));
    }


    private ResponseEntity<Object> buildResponseEntity(ErrorDetails apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}