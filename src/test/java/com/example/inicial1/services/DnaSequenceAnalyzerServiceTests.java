package com.example.inicial1.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class DnaSequenceAnalyzerServiceTests {

    @InjectMocks
    private DnaSequenceAnalyzerService dnaSequenceAnalyzerService;

    @Test
    void sequenceCount_dontFourConsecutiveCharacters_ReturnEmptySequence() {
        String sequence = "AAGGTT";

        String result = dnaSequenceAnalyzerService.sequenceCount(sequence);

        assertEquals("", result);
    }

    @Test
    void sequenceCount_fourConsecutiveCharacters_ReturnSequence() {
        String sequence = "GGGGTT";

        String result = dnaSequenceAnalyzerService.sequenceCount(sequence);

        assertEquals("GGGG", result);
    }

    @Test
    void getVerticalSequence_dontFourConsecutiveCharacters_ReturnEmptySequence() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

        String result = dnaSequenceAnalyzerService.getVerticalSequence(dna, 4);

        assertEquals("GGGG", result);
    }

    @Test
    void getVerticalSequence_fourConsecutiveCharacters_ReturnSequence() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

        String result = dnaSequenceAnalyzerService.getVerticalSequence(dna, 1);

        assertEquals("", result);
    }

    @Test
    void getDiagonal_correctMainDiagonal() {
        String[] dna = {"ATGC", "AAGC", "TTTT", "CAGT"};
        String result = dnaSequenceAnalyzerService.getDiagonal(dna, 0, 0, true);
        assertEquals("AATT", result);
    }

    @Test
    void getDiagonal_correctSecondaryDiagonal() {
        String[] dna = {"ATGC", "AAGC", "TTTT", "CAGT"};
        String result = dnaSequenceAnalyzerService.getDiagonal(dna, 0, 0, false);
        assertEquals("CGTC", result);
    }

    @Test
    void processDiagonal_addsDiagonalToList_whenValid() {
        List<String> mutantDna = new ArrayList<>();
        String diagonal = "AAAA";

        dnaSequenceAnalyzerService.processDiagonal(diagonal, mutantDna);

        assertEquals("AAAA", mutantDna.get(0));
    }

    @Test
    void processDiagonal_doesNotAddDiagonalToList_whenInvalid() {
        List<String> mutantDna = new ArrayList<>();
        String diagonal = "ABCD";

        dnaSequenceAnalyzerService.processDiagonal(diagonal, mutantDna);

        assertTrue(mutantDna.isEmpty());
    }
}

