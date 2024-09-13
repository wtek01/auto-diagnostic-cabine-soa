package com.hopital.futur.autodiagnostic.controller;

import com.hopital.futur.autodiagnostic.model.DiagnosticResponse;
import com.hopital.futur.autodiagnostic.model.DiagnosticType;
import com.hopital.futur.autodiagnostic.service.DiagnosticService;
import com.hopital.futur.autodiagnostic.exception.InvalidIndexSanteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

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
        DiagnosticResponse response = new DiagnosticResponse(33, Arrays.asList(DiagnosticType.CARDIOLOGIE));
        when(diagnosticService.diagnostiquer(33)).thenReturn(response);

        mockMvc.perform(get("/api/diagnostic/33")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.indexSante").value(33))
                .andExpect(jsonPath("$.diagnostics[0]").value("Cardiologie"));
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
        when(diagnosticService.diagnostiquer(0)).thenReturn(new DiagnosticResponse(0, Arrays.asList(DiagnosticType.AUCUNE_PATHOLOGIE)));

        mockMvc.perform(get("/api/diagnostic/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.indexSante").value(0))
                .andExpect(jsonPath("$.diagnostics[0]").value("Aucune pathologie détectée"));
    }

    @Test
    void testDiagnostiquerLargeNumber() throws Exception {
        when(diagnosticService.diagnostiquer(2000)).thenReturn(new DiagnosticResponse(2000, Arrays.asList(DiagnosticType.AUCUNE_PATHOLOGIE)));

        mockMvc.perform(get("/api/diagnostic/2000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.indexSante").value(2000))
                .andExpect(jsonPath("$.diagnostics[0]").value("Aucune pathologie détectée"));
    }

}