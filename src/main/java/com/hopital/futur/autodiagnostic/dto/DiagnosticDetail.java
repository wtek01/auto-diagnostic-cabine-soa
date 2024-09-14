package com.hopital.futur.autodiagnostic.dto;

import com.hopital.futur.autodiagnostic.model.DiagnosticType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticDetail {
    private String code;
    private String description;

    public DiagnosticDetail(DiagnosticType diagnosticType) {
        this.code = diagnosticType.getCode();
        this.description = diagnosticType.getDescription();
    }
}
