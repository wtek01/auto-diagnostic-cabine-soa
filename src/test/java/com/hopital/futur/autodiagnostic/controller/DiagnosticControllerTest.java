package com.hopital.futur.autodiagnostic.controller;

import com.hopital.futur.autodiagnostic.exception.InvalidIndexSanteException;
import com.hopital.futur.autodiagnostic.model.DiagnosticType;
import com.hopital.futur.autodiagnostic.response.DiagnosticResponse;
import com.hopital.futur.autodiagnostic.service.DiagnosticService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
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
            "33, CARD",
            "55, TRAUM",
            "15, CARD|TRAUM",
            "0, NONE"
    })
    void testDiagnostiquerValid(int indexSante, String expectedCodesStr) throws Exception {
        List<String> expectedCodes = Arrays.asList(expectedCodesStr.split("\\|"));

        // Convertir les codes en instances de DiagnosticType
        List<DiagnosticType> diagnosticTypes = expectedCodes.stream()
                .map(String::trim)
                .map(DiagnosticType::fromCode)
                .collect(Collectors.toList());

        // Créer l'instance de DiagnosticResponse avec List<DiagnosticType>
        DiagnosticResponse response = new DiagnosticResponse(indexSante, diagnosticTypes);

        when(diagnosticService.diagnostiquer(indexSante)).thenReturn(response);

        var resultActions = mockMvc.perform(get("/api/diagnostic/{indexSante}", indexSante)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.indexSante").value(indexSante));

        for (int i = 0; i < diagnosticTypes.size(); i++) {
            DiagnosticType diagnosticType = diagnosticTypes.get(i);
            resultActions
                    .andExpect(jsonPath("$.diagnostics[" + i + "].code").value(diagnosticType.getCode()))
                    .andExpect(jsonPath("$.diagnostics[" + i + "].description").value(diagnosticType.getDescription()));
        }

        // Vérifie le nombre de diagnostics
        resultActions.andExpect(jsonPath("$.diagnostics.length()").value(diagnosticTypes.size()));
    }

    @Test
    void testDiagnostiquerInvalidNegative() throws Exception {
        int indexSante = -1;
        when(diagnosticService.diagnostiquer(indexSante))
                .thenThrow(new InvalidIndexSanteException("L'index de santé ne peut pas être négatif"));

        mockMvc.perform(get("/api/diagnostic/{indexSante}", indexSante)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("L'index de santé ne peut pas être négatif"));
    }
}
