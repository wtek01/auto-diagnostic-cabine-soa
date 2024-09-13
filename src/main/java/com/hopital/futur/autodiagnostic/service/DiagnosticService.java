package com.hopital.futur.autodiagnostic.service;

import com.hopital.futur.autodiagnostic.exception.InvalidIndexSanteException;
import com.hopital.futur.autodiagnostic.model.DiagnosticResponse;
import com.hopital.futur.autodiagnostic.model.DiagnosticType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DiagnosticService {

    private static final Logger logger = LoggerFactory.getLogger(DiagnosticService.class);

    public DiagnosticResponse diagnostiquer(int indexSante) {
        logger.info("Début du diagnostic pour l'index de santé : {}", indexSante);
        if (indexSante < 0) {
            logger.error("Index de santé invalide : {}", indexSante);
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

        logger.info("Diagnostic établi : {} pour l'index de santé : {}", diagnostics, indexSante);
        return new DiagnosticResponse(indexSante, diagnostics);
    }
}
