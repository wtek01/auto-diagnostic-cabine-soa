package com.hopital.futur.autodiagnostic.rules;

import com.hopital.futur.autodiagnostic.model.DiagnosticType;
import org.springframework.stereotype.Component;

@Component
public class TraumatologyRule implements DiagnosticRule {

    private static final int DIVISEUR_TRAUMATOLOGIE = 5;

    @Override
    public boolean applies(int indexSante) {
        return indexSante % DIVISEUR_TRAUMATOLOGIE == 0 && indexSante != 0;
    }

    @Override
    public DiagnosticType getDiagnosticType() {
        return DiagnosticType.TRAUMATOLOGIE;
    }
}
