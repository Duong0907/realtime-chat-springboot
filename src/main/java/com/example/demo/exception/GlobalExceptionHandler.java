package com.example.demo.exception;

import com.example.demo.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Response> handleArgumentException(Exception e) {
        Response res = Response
                .builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .message("Invalid argument type") // Should not return exception's details directly
                .build();

        return new ResponseEntity<>(res, res.getStatusCode());
    }

    @ExceptionHandler({AuthenticationException.class })
    public ResponseEntity<Response> handleAuthenticationException(AuthenticationException e) {
        Response res = Response.builder()
                .statusCode(HttpStatus.UNAUTHORIZED)
                .message("Wrong username or password")
                .build();

        return new ResponseEntity<>(res, res.getStatusCode());
    }


    // Handle custom exceptions
    @ExceptionHandler({ CustomException.class })
    public ResponseEntity<Response> handleCustomException(CustomException e) {
        Response res = Response
                .builder()
                .statusCode(e.getCode())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(res, res.getStatusCode());
    }

    // Handle common exceptions
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Response> handleException(Exception e) {
        Response res = Response
                .builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Some thing went wrong")
                .build();

        return new ResponseEntity<>(res, res.getStatusCode());
    }


}
