package com.hopital.futur.autodiagnostic.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DiagnosticType {
    CARDIOLOGIE("Cardiologie"),
    TRAUMATOLOGIE("Traumatologie"),
    AUCUNE_PATHOLOGIE("Aucune pathologie détectée");

    private final String description;
    DiagnosticType(String description) {
        this.description = description;
    }
    @JsonValue
    public String getDescription() {
        return description;
    }
}
