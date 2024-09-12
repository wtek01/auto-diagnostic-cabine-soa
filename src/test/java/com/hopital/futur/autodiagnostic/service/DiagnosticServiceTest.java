package com.hopital.futur.autodiagnostic.service;

import com.hopital.futur.autodiagnostic.model.DiagnosticResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiagnosticServiceTest {
    @Mock
    private DiagnosticService diagnosticService;

    @BeforeEach
    void setUp() {
        diagnosticService = new DiagnosticService();
    }

    @Test
    void testDiagnostiquerMultipleDe3Et5() {
        DiagnosticResponse response = diagnosticService.diagnostiquer(15);
        assertEquals(15, response.getIndexSante());
        assertEquals("Cardiologie, Traumatologie", response.getDiagnostic());
    }

    @Test
    void testDiagnostiquerMultipleDe3() {
        DiagnosticResponse response = diagnosticService.diagnostiquer(9);
        assertEquals(9, response.getIndexSante());
        assertEquals("Cardiologie", response.getDiagnostic());
    }

    @Test
    void testDiagnostiquerMultipleDe5() {
        DiagnosticResponse response = diagnosticService.diagnostiquer(25);
        assertEquals(25, response.getIndexSante());
        assertEquals("Traumatologie", response.getDiagnostic());
    }

    @Test
    void testDiagnostiquerAucunePathologie() {
        DiagnosticResponse response = diagnosticService.diagnostiquer(7);
        assertEquals(7, response.getIndexSante());
        assertEquals("Aucune pathologie détectée", response.getDiagnostic());
    }

    @Test
    void testDiagnostiquerZero() {
        DiagnosticResponse response = diagnosticService.diagnostiquer(0);
        assertEquals(0, response.getIndexSante());
        assertEquals("Cardiologie, Traumatologie", response.getDiagnostic());
    }

    @Test
    void testDiagnostiquerNombreNegatif() {
        DiagnosticResponse response = diagnosticService.diagnostiquer(-15);
        assertEquals(-15, response.getIndexSante());
        assertEquals("Cardiologie, Traumatologie", response.getDiagnostic());
    }
}