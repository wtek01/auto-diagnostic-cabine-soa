package com.hopital.futur.autodiagnostic.service;

import com.hopital.futur.autodiagnostic.exception.InvalidIndexSanteException;
import com.hopital.futur.autodiagnostic.response.DiagnosticResponse;
import com.hopital.futur.autodiagnostic.model.DiagnosticType;
import com.hopital.futur.autodiagnostic.rules.DiagnosticRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class DiagnosticService {
    private static final int SEUIL_AVERTISSEMENT = 1000; // Exemple de seuil pour les valeurs inhabituelles
    private final List<DiagnosticRule> rules;
    public DiagnosticService(List<DiagnosticRule> rules) {
        this.rules = rules;
    }
    /**
     * Effectue un diagnostic basé sur l'index de santé fourni.
     * L'index de santé est attendu dans la plage [0, ~1000] selon les spécifications du capteur.
     * Des valeurs plus élevées sont traitées mais pourraient indiquer un problème avec le capteur.
     */
    public DiagnosticResponse diagnostiquer(int indexSante) {
        log.info("Début du diagnostic pour l'index de santé : {}", indexSante);
        if (indexSante < 0) {
            log.error("Index de santé invalide : {}", indexSante);
            throw new InvalidIndexSanteException("L'index de santé ne peut pas être négatif");
        }

        // Si l'index est 0, ne pas diagnostiquer de pathologies
        if (indexSante == 0) {
            return new DiagnosticResponse(0, Collections.singletonList(DiagnosticType.AUCUNE_PATHOLOGIE));
        }

        if (indexSante > SEUIL_AVERTISSEMENT) {
            log.warn("Index de santé inhabituellement élevé détecté : {}", indexSante);
        }

        List<DiagnosticType> diagnostics = new ArrayList<>();

        for (DiagnosticRule rule : rules) {
            if (rule.applies(indexSante)) {
                diagnostics.add(rule.getDiagnosticType());
            }
        }

        if (diagnostics.isEmpty()) {
            diagnostics.add(DiagnosticType.AUCUNE_PATHOLOGIE);
        }

        log.info("Diagnostic établi : {} pour l'index de santé : {}", diagnostics, indexSante);
        return new DiagnosticResponse(indexSante, diagnostics);
    }
}
