package com.hopital.futur.autodiagnostic.controller;

import com.hopital.futur.autodiagnostic.model.DiagnosticResponse;
import com.hopital.futur.autodiagnostic.service.DiagnosticService;
import com.hopital.futur.autodiagnostic.exception.InvalidIndexSanteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DiagnosticController.class)
class DiagnosticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiagnosticService diagnosticService;

    @Test
    void testDiagnostiquerValid() throws Exception {
        when(diagnosticService.diagnostiquer(33)).thenReturn(new DiagnosticResponse(33, "Cardiologie"));

        mockMvc.perform(get("/api/diagnostic/33")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.indexSante").value(33))
                .andExpect(jsonPath("$.diagnostic").value("Cardiologie"));
    }

    @Test
    void testDiagnostiquerInvalidNegative() throws Exception {
        when(diagnosticService.diagnostiquer(-1)).thenThrow(new InvalidIndexSanteException("L'index de santé ne peut pas être négatif"));

        mockMvc.perform(get("/api/diagnostic/-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("L'index de santé ne peut pas être négatif"));
    }

    @Test
    void testDiagnostiquerZero() throws Exception {
        when(diagnosticService.diagnostiquer(0)).thenReturn(new DiagnosticResponse(0, "Aucune pathologie détectée"));

        mockMvc.perform(get("/api/diagnostic/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.indexSante").value(0))
                .andExpect(jsonPath("$.diagnostic").value("Aucune pathologie détectée"));
    }

    @Test
    void testDiagnostiquerLargeNumber() throws Exception {
        when(diagnosticService.diagnostiquer(1000000)).thenReturn(new DiagnosticResponse(1000000, "Aucune pathologie détectée"));

        mockMvc.perform(get("/api/diagnostic/1000000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.indexSante").value(1000000))
                .andExpect(jsonPath("$.diagnostic").value("Aucune pathologie détectée"));
    }
}