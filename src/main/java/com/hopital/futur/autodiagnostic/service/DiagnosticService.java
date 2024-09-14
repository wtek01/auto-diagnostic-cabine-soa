package com.hopital.futur.autodiagnostic.service;

import com.hopital.futur.autodiagnostic.domain.Diagnostic;
import com.hopital.futur.autodiagnostic.dto.DiagnosticDTO;
import com.hopital.futur.autodiagnostic.exception.InvalidIndexSanteException;
import com.hopital.futur.autodiagnostic.model.DiagnosticType;
import com.hopital.futur.autodiagnostic.rules.DiagnosticRule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiagnosticService {
    private final List<DiagnosticRule> rules;

    public DiagnosticService(List<DiagnosticRule> rules) {
        this.rules = rules;
    }

    public List<DiagnosticDTO> diagnostiquer(int indexSante) {
        if (indexSante < 0) {
            throw new InvalidIndexSanteException("L'index de santé ne peut pas être négatif : " + indexSante);
        }

        Diagnostic diagnostic = new Diagnostic(indexSante);

        for (DiagnosticRule rule : rules) {
            if (rule.applies(indexSante)) {
                diagnostic.ajouterPathologie(rule.getDiagnosticType());
            }
        }

        if (!diagnostic.aPathologie()) {
            diagnostic.ajouterPathologie(DiagnosticType.AUCUNE_PATHOLOGIE);
        }

        return mapToDiagnosticDTO(diagnostic);
    }

    private List<DiagnosticDTO> mapToDiagnosticDTO(Diagnostic diagnostic) {
        return diagnostic.getPathologies().stream()
                .map(pathologie -> new DiagnosticDTO(
                        diagnostic.getIndexSante(),
                        pathologie.getCode(),
                        pathologie.getDescription()))
                .collect(Collectors.toList());
    }
}