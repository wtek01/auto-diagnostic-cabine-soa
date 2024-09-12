package com.hopital.futur.autodiagnostic.controller;

import com.hopital.futur.autodiagnostic.model.DiagnosticResponse;
import com.hopital.futur.autodiagnostic.service.DiagnosticService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DiagnosticController {

    private final DiagnosticService diagnosticService;

    public DiagnosticController(DiagnosticService diagnosticService) {
        this.diagnosticService = diagnosticService;
    }

    @GetMapping(value = "/diagnostic/{indexSante}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DiagnosticResponse diagnostiquer(@PathVariable int indexSante) {
        return diagnosticService.diagnostiquer(indexSante);
    }
}