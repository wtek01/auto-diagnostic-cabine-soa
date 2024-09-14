package com.hopital.futur.autodiagnostic.domain;

import com.hopital.futur.autodiagnostic.model.DiagnosticType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Diagnostic {
    private final int indexSante;
    private final List<DiagnosticType> pathologies;

    public Diagnostic(int indexSante) {
        this.indexSante = indexSante;
        this.pathologies = new ArrayList<>();
    }

    public void ajouterPathologie(DiagnosticType pathologie) {
        this.pathologies.add(pathologie);
    }

    public boolean aPathologie() {
        return !pathologies.isEmpty();
    }

    public List<String> getDescriptionsPathologies() {
        return pathologies.stream()
                .map(DiagnosticType::getDescription)
                .collect(Collectors.toList());
    }
}