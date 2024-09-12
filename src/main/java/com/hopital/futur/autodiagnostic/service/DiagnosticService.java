package com.hopital.futur.autodiagnostic.service;

import com.hopital.futur.autodiagnostic.exception.InvalidHealthIndexException;
import com.hopital.futur.autodiagnostic.model.DiagnosticResponse;
import org.springframework.stereotype.Service;

@Service
public class DiagnosticService {

    public DiagnosticResponse diagnostiquer(int indexSante) {
        // Validation: Rejeter les valeurs négatives
        if (indexSante < 0) {
            throw new InvalidHealthIndexException("L'index de santé ne peut pas être négatif.");
        }

        String diagnostic;
        if (indexSante % 3 == 0 && indexSante % 5 == 0) {
            diagnostic = "Cardiologie, Traumatologie";
        } else if (indexSante % 3 == 0) {
            diagnostic = "Cardiologie";
        } else if (indexSante % 5 == 0) {
            diagnostic = "Traumatologie";
        } else {
            diagnostic = "Aucune pathologie détectée";
        }
        return new DiagnosticResponse(indexSante, diagnostic);
    }
}
