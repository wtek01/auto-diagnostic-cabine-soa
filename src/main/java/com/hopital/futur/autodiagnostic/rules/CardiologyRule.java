package com.hopital.futur.autodiagnostic.rules;

import com.hopital.futur.autodiagnostic.model.DiagnosticType;
import org.springframework.stereotype.Component;

@Component
public class CardiologyRule implements DiagnosticRule {
    @Override
    public boolean applies(int indexSante) {
        return indexSante % 3 == 0 && indexSante != 0;
    }

    @Override
    public DiagnosticType getDiagnosticType() {
        return DiagnosticType.CARDIOLOGIE;
    }
}
