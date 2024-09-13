package com.hopital.futur.autodiagnostic.controller;

import com.hopital.futur.autodiagnostic.exception.InvalidIndexSanteException;
import com.hopital.futur.autodiagnostic.model.DiagnosticResponse;
import com.hopital.futur.autodiagnostic.model.ErrorResponse;
import com.hopital.futur.autodiagnostic.service.DiagnosticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/api")
public class DiagnosticController {

    private static final Logger logger = LoggerFactory.getLogger(DiagnosticController.class);

    private final DiagnosticService diagnosticService;

    @Autowired
    public DiagnosticController(DiagnosticService diagnosticService) {
        this.diagnosticService = diagnosticService;
    }

    @GetMapping("/diagnostic/{indexSante}")
    public ResponseEntity<?> diagnostiquer(@PathVariable int indexSante) {
        logger.info("Requête reçue pour diagnostiquer l'index de santé : {}", indexSante);

        try {
            DiagnosticResponse response = diagnosticService.diagnostiquer(indexSante);
            logger.info("Diagnostic établi avec succès pour l'index de santé : {}", indexSante);
            return ResponseEntity.ok(response);
        } catch (InvalidIndexSanteException e) {
            logger.error("Index de santé invalide : {}", indexSante, e);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("INVALID_INDEX", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erreur inattendue lors du diagnostic pour l'index de santé : {}", indexSante, e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("INTERNAL_ERROR", "Une erreur interne s'est produite"));
        }
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        logger.error("Erreur de type d'argument : {}", ex.getMessage());
        String error = "Le paramètre '" + ex.getName() + "' doit être de type " + ex.getRequiredType().getSimpleName();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_ARGUMENT_TYPE", error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Erreur générique non gérée", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_SERVER_ERROR", "Une erreur interne inattendue s'est produite"));
    }
}