package com.hopital.futur.autodiagnostic.controller;

import com.hopital.futur.autodiagnostic.response.DiagnosticResponse;
import com.hopital.futur.autodiagnostic.service.DiagnosticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class DiagnosticController {
    private final DiagnosticService diagnosticService;

    @Autowired
    public DiagnosticController(DiagnosticService diagnosticService) {
        this.diagnosticService = diagnosticService;
    }

    @Operation(summary = "Obtenir un diagnostic basé sur l'index de santé")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diagnostic réussi",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DiagnosticResponse.class))),
            @ApiResponse(responseCode = "400", description = "Index de santé invalide",
                    content = @Content)
    })
    @GetMapping("/diagnostic/{indexSante}")
    public ResponseEntity<DiagnosticResponse> diagnostiquer(@PathVariable int indexSante) {
        log.info("Requête reçue pour diagnostiquer l'index de santé : {}", indexSante);
        DiagnosticResponse response = diagnosticService.diagnostiquer(indexSante);
        log.info("Diagnostic établi avec succès pour l'index de santé : {}", indexSante);
        return ResponseEntity.ok(response);
    }
}
