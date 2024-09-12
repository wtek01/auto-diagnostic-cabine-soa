package com.hopital.futur.autodiagnostic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticResponse {
    private int indexSante;
    private String diagnostic;
}