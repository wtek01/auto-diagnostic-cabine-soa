package com.hopital.futur.autodiagnostic.controller;

import com.hopital.futur.autodiagnostic.documentation.SwaggerDiagnosticOperation;
import com.hopital.futur.autodiagnostic.dto.DiagnosticDTO;
import com.hopital.futur.autodiagnostic.service.DiagnosticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class DiagnosticController {
    private final DiagnosticService diagnosticService;

    @Autowired
    public DiagnosticController(DiagnosticService diagnosticService) {
        this.diagnosticService = diagnosticService;
    }
    @GetMapping("/diagnostic/{indexSante}")
    public ResponseEntity<List<DiagnosticDTO>> diagnostiquer(@PathVariable int indexSante) {
        log.info("Requête reçue pour diagnostiquer l'index de santé : {}", indexSante);
        List<DiagnosticDTO> diagnostics = diagnosticService.diagnostiquer(indexSante);
        log.info("Diagnostic établi avec succès pour l'index de santé : {}", indexSante);
        return ResponseEntity.ok(diagnostics); // Renvoyer la liste de DiagnosticDTO directement
    }
}

