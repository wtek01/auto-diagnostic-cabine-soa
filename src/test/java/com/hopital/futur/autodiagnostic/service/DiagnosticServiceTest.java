package com.hopital.futur.autodiagnostic.service;

import com.hopital.futur.autodiagnostic.dto.DiagnosticDetail;
import com.hopital.futur.autodiagnostic.exception.InvalidIndexSanteException;
import com.hopital.futur.autodiagnostic.response.DiagnosticResponse;
import com.hopital.futur.autodiagnostic.rules.CardiologyRule;
import com.hopital.futur.autodiagnostic.rules.TraumatologyRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class DiagnosticServiceTest {

    private DiagnosticService diagnosticService;

    @BeforeEach
    void setUp() {
        diagnosticService = new DiagnosticService(Arrays.asList(
                new CardiologyRule(),
                new TraumatologyRule()
        ));
    }

    @ParameterizedTest
    @CsvSource({
            "15, CARD|TRAUM",
            "9, CARD",
            "25, TRAUM",
            "7, NONE",
            "0, NONE"
    })
    void testDiagnostiquer(int indexSante, String expectedCodesStr) {
        DiagnosticResponse response = diagnosticService.diagnostiquer(indexSante);
        Assertions.assertEquals(indexSante, response.getIndexSante());

        List<String> expectedCodes = Arrays.asList(expectedCodesStr.split("\\|"));

        // Extraire les codes réels de la réponse
        List<String> actualCodes = response.getDiagnostics().stream()
                .map(DiagnosticDetail::getCode)
                .collect(Collectors.toList());

        Assertions.assertEquals(expectedCodes.size(), actualCodes.size(), "Le nombre de diagnostics ne correspond pas.");

        for (String expectedCode : expectedCodes) {
            Assertions.assertTrue(actualCodes.contains(expectedCode),
                    "Le code de diagnostic attendu n'est pas présent : " + expectedCode);
        }
    }

    @Test
    void testDiagnostiquerNombreNegatif() {
        Assertions.assertThrows(InvalidIndexSanteException.class, () -> {
            diagnosticService.diagnostiquer(-15);
        });
    }
}
