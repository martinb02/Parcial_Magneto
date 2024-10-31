package com.example.inicial1.services;

import com.example.inicial1.repositories.HumansRepository;
import com.example.inicial1.services.impl.DnaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DnaServiceTests {

    @InjectMocks
    private DnaServiceImpl mutantService;

    @Mock
    private HumansRepository humansRepository;

    @Mock
    private StatsService statsService;

    @Mock
    private DnaValidationService dnaValidationService;

    @Mock
    private DnaSequenceAnalyzerService dnaSequenceAnalyzerService;

    @Test
    void isMutant_dnaMutantGreaterThanTwo_ForbiddenException() {
        String[] dna = {"ATGCGA"};

        doNothing().when(dnaValidationService).validateDna(any());
        when(humansRepository.existsHumansByDna(anyString())).thenReturn(false);
        when(dnaSequenceAnalyzerService.sequenceCount(anyString())).thenReturn("CCCC");
        when(dnaSequenceAnalyzerService.getVerticalSequence(any(), anyInt())).thenReturn("AAAA");

        assertThrows(RuntimeException.class, () -> mutantService.isMutant(dna));
        verify(statsService).updateStats(true);
        verify(humansRepository, never()).save(any());
    }

    @Test
    void isMutant_DnaIsValid_OkNotMutant() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

        doNothing().when(dnaValidationService).validateDna(any());
        when(humansRepository.existsHumansByDna(anyString())).thenReturn(false);
        when(dnaSequenceAnalyzerService.sequenceCount(anyString())).thenReturn("");
        when(dnaSequenceAnalyzerService.getVerticalSequence(any(), anyInt())).thenReturn("");
        when(dnaSequenceAnalyzerService.getDiagonal(any(), anyInt(), anyInt(), anyBoolean())).thenReturn("");

        mutantService.isMutant(dna);

        verify(statsService).updateStats(false);
        verify(humansRepository).save(any());
    }

    @Test
    void isMutant_DnaAlreadyExists_Return() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

        doNothing().when(dnaValidationService).validateDna(any());
        when(humansRepository.existsHumansByDna(anyString())).thenReturn(true);

        mutantService.isMutant(dna);

        verifyNoInteractions(statsService);
        verifyNoInteractions(dnaSequenceAnalyzerService);
        verify(humansRepository, never()).save(any());
    }
}
