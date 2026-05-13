package com.fiap.soa.booking_room.infrastructure.exceptions;

import com.fiap.soa.booking_room.dto.StandardError;
import com.fiap.soa.booking_room.dto.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erros 404 - Não Encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(
                LocalDateTime.now(), status.value(), "Not Found", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    // Erros 400 - Bad Request (Regras de Negócio)
    @ExceptionHandler({InvalidDateRangeException.class, CapacityExceededException.class})
    public ResponseEntity<StandardError> handleBadRequestBusinessRules(RuntimeException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(
                LocalDateTime.now(), status.value(), "Bad Request", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    // Erros 409 - Conflict (Regras de Negócio e Estados)
    @ExceptionHandler({RoomUnavailableException.class, InvalidReservationStateException.class})
    public ResponseEntity<StandardError> handleConflictBusinessRules(RuntimeException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(
                LocalDateTime.now(), status.value(), "Conflict", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    // Erros 400 - Validação de DTOs (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<ValidationError.FieldMessage> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> new ValidationError.FieldMessage(f.getField(), f.getDefaultMessage()))
                .toList();

        ValidationError err = new ValidationError(
                LocalDateTime.now(), status.value(), "Validation Error", "Erro na validação dos campos enviados", request.getRequestURI(), fieldErrors);

        return ResponseEntity.status(status).body(err);
    }
}