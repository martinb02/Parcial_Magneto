package com.example.inicial1.services;

import org.springframework.stereotype.Service;

@Service
public class DnaValidationService {

    public static final int SEQUENCE_SIZE = 4;

    public void validateDna(String[] dna) {
        if (dna == null || dna.length == 0) {
            throw new IllegalArgumentException("El ADN no puede ser nulo o vacío.");
        }

        int size = dna.length;

        if (size < SEQUENCE_SIZE) {
            throw new IllegalArgumentException("El ADN debe ser mayor o igual a 4x4).");
        }

        for (String strDna : dna) {
            if (strDna.length() != size) {
                throw new IllegalArgumentException("El ADN debe ser cuadrado (NxN).");
            }

            if (!strDna.matches("[ATCG]+")) {
                throw new IllegalArgumentException("La cadena de ADN contiene caracteres no válidos. Solo se permiten A, T, C y G.");
            }
        }
    }
}
