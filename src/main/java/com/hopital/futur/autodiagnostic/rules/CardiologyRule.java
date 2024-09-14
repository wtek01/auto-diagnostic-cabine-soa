package com.hopital.futur.autodiagnostic.rules;

import com.hopital.futur.autodiagnostic.model.DiagnosticType;
import org.springframework.stereotype.Component;

@Component
public class CardiologyRule implements DiagnosticRule {
    private static final int DIVISEUR_CARDIOLOGIE = 3;

    @Override
    public boolean applies(int indexSante) {
        return indexSante % DIVISEUR_CARDIOLOGIE == 0 && indexSante != 0;
    }

    @Override
    public DiagnosticType getDiagnosticType() {
        return DiagnosticType.CARDIOLOGIE;
    }
}
