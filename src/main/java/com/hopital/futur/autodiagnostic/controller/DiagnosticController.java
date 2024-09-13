package com.hopital.futur.autodiagnostic.controller;

import com.hopital.futur.autodiagnostic.exception.InvalidIndexSanteException;
import com.hopital.futur.autodiagnostic.model.DiagnosticResponse;
import com.hopital.futur.autodiagnostic.service.DiagnosticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DiagnosticController {

    private final DiagnosticService diagnosticService;

    @Autowired
    public DiagnosticController(DiagnosticService diagnosticService) {
        this.diagnosticService = diagnosticService;
    }

    @GetMapping("/diagnostic/{indexSante}")
    public ResponseEntity<DiagnosticResponse> diagnostiquer(@PathVariable int indexSante) {
        if (indexSante < 0) {
            throw new InvalidIndexSanteException("L'index de santé ne peut pas être négatif");
        }
        DiagnosticResponse response = diagnosticService.diagnostiquer(indexSante);
        return ResponseEntity.ok(response);
    }

}