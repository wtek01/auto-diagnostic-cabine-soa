package com.hopital.futur.autodiagnostic.dto;

import lombok.Data;

@Data
public class DiagnosticDTO {
    private int indexSante;
    private String code;
    private String description;

    public DiagnosticDTO(int indexSante, String code, String description) {
        this.indexSante = indexSante;
        this.code = code;
        this.description = description;
    }
}