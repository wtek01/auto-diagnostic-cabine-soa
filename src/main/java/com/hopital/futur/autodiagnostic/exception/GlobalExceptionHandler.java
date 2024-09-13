package com.hopital.futur.autodiagnostic.exception;

import com.hopital.futur.autodiagnostic.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidIndexSanteException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIndexSanteException(InvalidIndexSanteException ex, WebRequest request) {
        log.error("InvalidIndexSanteException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("INVALID_INDEX", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.error("Type d'argument invalide: {}", ex.getMessage());
        String error = "Le type de l'un des paramètres fournis est invalide.";
        ErrorResponse errorResponse = new ErrorResponse("INVALID_ARGUMENT_TYPE", error);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Exception non gérée: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", "Une erreur interne inattendue s'est produite");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
