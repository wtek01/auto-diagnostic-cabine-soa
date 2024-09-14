package com.hopital.futur.autodiagnostic.controller;

import com.hopital.futur.autodiagnostic.dto.DiagnosticDTO;
import com.hopital.futur.autodiagnostic.exception.InvalidIndexSanteException;
import com.hopital.futur.autodiagnostic.service.DiagnosticService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DiagnosticController.class)
class DiagnosticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiagnosticService diagnosticService;

    @ParameterizedTest
    @CsvSource({
            "15, CARD, Cardiologie",
            "25, TRAUM, Traumatologie",
            "0, NONE, Aucune pathologie détectée"
    })
    void testDiagnostiquer(int indexSante, String code, String description) throws Exception {
        DiagnosticDTO diagnosticDTO = new DiagnosticDTO(indexSante, code, description);
        List<DiagnosticDTO> diagnostics = Arrays.asList(diagnosticDTO);

        Mockito.when(diagnosticService.diagnostiquer(eq(indexSante))).thenReturn(diagnostics);

        mockMvc.perform(get("/api/diagnostic/{indexSante}", indexSante)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].indexSante").value(indexSante))
                .andExpect(jsonPath("$[0].code").value(code))
                .andExpect(jsonPath("$[0].description").value(description));
    }

    @Test
    void testDiagnostiquerInvalidNegative() throws Exception {
        int indexSante = -1;
        Mockito.when(diagnosticService.diagnostiquer(indexSante))
                .thenThrow(new InvalidIndexSanteException("L'index de santé ne peut pas être négatif"));

        mockMvc.perform(get("/api/diagnostic/{indexSante}", indexSante)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("L'index de santé ne peut pas être négatif"));
    }
}
