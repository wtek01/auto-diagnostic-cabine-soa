package com.hopital.futur.autodiagnostic.service;

import com.hopital.futur.autodiagnostic.exception.InvalidIndexSanteException;
import com.hopital.futur.autodiagnostic.model.DiagnosticResponse;
import com.hopital.futur.autodiagnostic.model.DiagnosticType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DiagnosticService {

    public DiagnosticResponse diagnostiquer(int indexSante) {
        if (indexSante < 0) {
            throw new InvalidIndexSanteException("L'index de santé ne peut pas être négatif");
        }

        // Si l'index est 0, ne pas diagnostiquer de pathologies
        if (indexSante == 0) {
            return new DiagnosticResponse(0, Collections.singletonList(DiagnosticType.AUCUNE_PATHOLOGIE));
        }

        List<DiagnosticType> diagnostics = new ArrayList<>();

        if (indexSante % 3 == 0) {
            diagnostics.add(DiagnosticType.CARDIOLOGIE);
        }
        if (indexSante % 5 == 0) {
            diagnostics.add(DiagnosticType.TRAUMATOLOGIE);
        }
        if (diagnostics.isEmpty()) {
            diagnostics.add(DiagnosticType.AUCUNE_PATHOLOGIE);
        }

        return new DiagnosticResponse(indexSante, diagnostics);
    }
}
