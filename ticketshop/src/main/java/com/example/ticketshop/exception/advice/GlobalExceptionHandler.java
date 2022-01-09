package com.example.ticketshop.exception.advice;

import com.example.ticketshop.exception.NotAvailableSeatsException;
import com.example.ticketshop.exception.NotFoundException;
import org.aspectj.weaver.ast.Not;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<String> handleNotFound(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage() + " at " + LocalDateTime.now());
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<String> handleNotFound(EmptyResultDataAccessException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage() + " at " + LocalDateTime.now());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handle(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getBindingResult().getAllErrors().stream()
                        .map(error -> error.getDefaultMessage())
                        .collect(Collectors.joining(", ")));
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<String> handle(SQLIntegrityConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({NotAvailableSeatsException.class})
    public ResponseEntity<String> handle(NotAvailableSeatsException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

}
