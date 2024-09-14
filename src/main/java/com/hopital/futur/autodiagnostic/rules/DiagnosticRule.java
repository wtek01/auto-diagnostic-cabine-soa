package com.hopital.futur.autodiagnostic.rules;

import com.hopital.futur.autodiagnostic.model.DiagnosticType;

public interface DiagnosticRule {
    boolean applies(int indexSante);
    DiagnosticType getDiagnosticType();
}
