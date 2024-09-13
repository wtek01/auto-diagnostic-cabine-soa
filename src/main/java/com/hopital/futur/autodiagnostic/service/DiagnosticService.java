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
    private static final int SEUIL_AVERTISSEMENT = 1000; // Exemple de seuil pour les valeurs inhabituelles

    // Constantes pour les seuils de diagnostic
    private static final int SEUIL_CARDIOLOGIE = 3;
    private static final int SEUIL_TRAUMATOLOGIE = 5;

    /**
     * Effectue un diagnostic basé sur l'index de santé fourni.
     * L'index de santé est attendu dans la plage [0, ~1000] selon les spécifications du capteur.
     * Des valeurs plus élevées sont traitées mais pourraient indiquer un problème avec le capteur.
     */
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

        if (indexSante > SEUIL_AVERTISSEMENT) {
            logger.warn("Index de santé inhabituellement élevé détecté : {}", indexSante);
        }

        List<DiagnosticType> diagnostics = new ArrayList<>();

        if (indexSante % SEUIL_CARDIOLOGIE  == 0) {
            diagnostics.add(DiagnosticType.CARDIOLOGIE);
        }
        if (indexSante % SEUIL_TRAUMATOLOGIE  == 0) {
            diagnostics.add(DiagnosticType.TRAUMATOLOGIE);
        }
        if (diagnostics.isEmpty()) {
            diagnostics.add(DiagnosticType.AUCUNE_PATHOLOGIE);
        }

        logger.info("Diagnostic établi : {} pour l'index de santé : {}", diagnostics, indexSante);
        return new DiagnosticResponse(indexSante, diagnostics);
    }
}
