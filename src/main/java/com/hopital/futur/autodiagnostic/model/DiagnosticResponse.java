package com.hopital.futur.autodiagnostic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticResponse {
    private int indexSante;
    private List<DiagnosticType> diagnostics;
}
