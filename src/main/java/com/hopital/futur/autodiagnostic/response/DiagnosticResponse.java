package com.hopital.futur.autodiagnostic.response;

import com.hopital.futur.autodiagnostic.dto.DiagnosticDetail;
import com.hopital.futur.autodiagnostic.model.DiagnosticType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class DiagnosticResponse {
    private int indexSante;
    private List<DiagnosticDetail> diagnostics;

    // Constructeur unique
    public DiagnosticResponse(int indexSante, List<DiagnosticType> diagnosticTypes) {
        this.indexSante = indexSante;
        this.diagnostics = diagnosticTypes.stream()
                .map(DiagnosticDetail::new)
                .collect(Collectors.toList());
    }
}
