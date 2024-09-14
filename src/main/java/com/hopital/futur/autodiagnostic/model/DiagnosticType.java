package com.hopital.futur.autodiagnostic.model;

public enum DiagnosticType {
    CARDIOLOGIE("CARD", "Cardiologie"),
    TRAUMATOLOGIE("TRAUM", "Traumatologie"),
    AUCUNE_PATHOLOGIE("NONE", "Aucune pathologie détectée");

    private final String code;
    private final String description;

    DiagnosticType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() { return code; }

    public String getDescription() { return description; }

    // Add this method
    public static DiagnosticType fromCode(String code) {
        for (DiagnosticType type : DiagnosticType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}

