package com.hopital.futur.autodiagnostic.controller;

import com.hopital.futur.autodiagnostic.model.DiagnosticResponse;
import com.hopital.futur.autodiagnostic.service.DiagnosticService;
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
    void testDiagnostiquer() throws Exception {
        when(diagnosticService.diagnostiquer(33)).thenReturn(new DiagnosticResponse(33, "Cardiologie"));

        mockMvc.perform(get("/api/diagnostic/33")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.indexSante").value(33))
                .andExpect(jsonPath("$.diagnostic").value("Cardiologie"));
    }
}