package com.example.inicial1.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class DnaValidationServiceTests {

    @InjectMocks
    private DnaValidationService dnaValidationService;

    @Test
    void validateDna_nullDna_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            dnaValidationService.validateDna(null);
        }, "El ADN no puede ser nulo o vacío.");
    }

    @Test
    void validateDna_emptyDna_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            dnaValidationService.validateDna(new String[]{});
        }, "El ADN no puede ser nulo o vacío.");
    }

    @Test
    void validateDna_dnaSizeLessThanFour_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            dnaValidationService.validateDna(new String[]{"AT", "CG"});
        }, "El ADN debe ser mayor o igual a 4x4).");
    }

    @Test
    void validateDna_nonSquareDna_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            dnaValidationService.validateDna(new String[]{"ATCG", "AAT", "CAGT", "TCTA"});
        }, "El ADN debe ser cuadrado (NxN).");
    }

    @Test
    void validateDna_invalidCharactersInDna_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            dnaValidationService.validateDna(new String[]{"ATCG", "AATX", "CAGT", "TCTA"});
        }, "La cadena de ADN contiene caracteres no válidos. Solo se permiten A, T, C y G.");
    }

    @Test
    void validateDna_validDna_doesNotThrowException() {
        try {
            dnaValidationService.validateDna(new String[]{"ATCG", "AAGT", "CCTA", "GTAC"});
        } catch (IllegalArgumentException e) {
            fail("No debería lanzar excepción para ADN válido");
        }
    }
}

