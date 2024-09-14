package com.hopital.futur.autodiagnostic.service;

import com.hopital.futur.autodiagnostic.dto.DiagnosticDTO;
import com.hopital.futur.autodiagnostic.exception.InvalidIndexSanteException;
import com.hopital.futur.autodiagnostic.rules.CardiologyRule;
import com.hopital.futur.autodiagnostic.rules.TraumatologyRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Assertions;

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
            "15, CARD|TRAUM, Cardiologie|Traumatologie",
            "9, CARD, Cardiologie",
            "25, TRAUM, Traumatologie",
            "7, NONE, Aucune pathologie détectée",
            "0, NONE, Aucune pathologie détectée"
    })
    void testDiagnostiquer(int indexSante, String expectedCodesStr, String expectedDescriptionsStr) {
        List<String> expectedCodes = Arrays.asList(expectedCodesStr.split("\\|"));
        List<String> expectedDescriptions = Arrays.asList(expectedDescriptionsStr.split("\\|"));

        // Appel du service
        List<DiagnosticDTO> response = diagnosticService.diagnostiquer(indexSante);

        // Vérification que le nombre de diagnostics est correct
        Assertions.assertEquals(expectedCodes.size(), response.size(), "Le nombre de diagnostics ne correspond pas.");

        // Vérification des valeurs des diagnostics
        for (int i = 0; i < expectedCodes.size(); i++) {
            DiagnosticDTO diagnostic = response.get(i);
            Assertions.assertEquals(indexSante, diagnostic.getIndexSante(), "Index de santé incorrect.");
            Assertions.assertEquals(expectedCodes.get(i), diagnostic.getCode(), "Code de diagnostic incorrect.");
            Assertions.assertEquals(expectedDescriptions.get(i), diagnostic.getDescription(), "Description de diagnostic incorrecte.");
        }
    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "-15"
    })
    void testDiagnostiquerNombreNegatif(int indexSante) {
        // Test des index de santé négatifs
        Assertions.assertThrows(InvalidIndexSanteException.class, () -> {
            diagnosticService.diagnostiquer(indexSante);
        }, "Un index de santé négatif devrait déclencher une exception.");
    }
}
