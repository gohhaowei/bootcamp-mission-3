package com.btkbootcamp.mission3;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<RestErrorResponse> handleException(CarNotFoundException e){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        RestErrorResponse restErrorResponse = new RestErrorResponse();
        restErrorResponse.setStatus(httpStatus.value());
        restErrorResponse.setMessage(e.getMessage());
        restErrorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<RestErrorResponse>(restErrorResponse, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<RestErrorResponse> handleException(Exception e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        RestErrorResponse restErrorResponse = new RestErrorResponse();
        restErrorResponse.setStatus(httpStatus.value());
        restErrorResponse.setMessage(e.getMessage());
        restErrorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<RestErrorResponse>(restErrorResponse, httpStatus);
    }
}
