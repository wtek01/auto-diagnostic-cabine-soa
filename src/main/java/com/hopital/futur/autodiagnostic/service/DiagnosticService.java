package com.hopital.futur.autodiagnostic.service;

import com.hopital.futur.autodiagnostic.exception.InvalidIndexSanteException;
import com.hopital.futur.autodiagnostic.model.DiagnosticResponse;
import org.springframework.stereotype.Service;

@Service
public class DiagnosticService {

    public DiagnosticResponse diagnostiquer(int indexSante) {
        if (indexSante < 0) {
            throw new InvalidIndexSanteException("L'index de santé ne peut pas être négatif");
        }

        // Si l'index est 0, ne pas diagnostiquer de pathologies
        if (indexSante == 0) {
            return new DiagnosticResponse(0, "Aucune pathologie détectée");
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