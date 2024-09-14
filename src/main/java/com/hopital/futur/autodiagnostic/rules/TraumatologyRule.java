package com.hopital.futur.autodiagnostic.rules;

import com.hopital.futur.autodiagnostic.model.DiagnosticType;
import org.springframework.stereotype.Component;

@Component
public class TraumatologyRule implements DiagnosticRule {
    @Override
    public boolean applies(int indexSante) {
        return indexSante % 5 == 0 && indexSante != 0;
    }

    @Override
    public DiagnosticType getDiagnosticType() {
        return DiagnosticType.TRAUMATOLOGIE;
    }
}
