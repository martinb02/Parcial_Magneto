package com.example.inicial1.services.impl;

import com.example.inicial1.entities.Dna;
import com.example.inicial1.repositories.HumansRepository;
import com.example.inicial1.services.DnaSequenceAnalyzerService;
import com.example.inicial1.services.DnaValidationService;
import com.example.inicial1.services.DnaService;
import com.example.inicial1.services.StatsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.example.inicial1.services.DnaValidationService.SEQUENCE_SIZE;

@Service
@AllArgsConstructor
public class DnaServiceImpl implements DnaService {

    private final HumansRepository humansRepository;
    private final StatsService statsService;
    private final DnaValidationService dnaValidationService;
    private final DnaSequenceAnalyzerService dnaSequenceAnalyzerService;

    public static final int MIN_MUTANT_SEQUENCE = 1;

    public void isMutant(String[] dna) {
        dnaValidationService.validateDna(dna);

        String dnaString = Arrays.toString(dna);
        if (humansRepository.existsHumansByDna(dnaString)) return;

        List<String> mutantDna = new ArrayList<>();
        int size = dna.length;

        // Comprobar horizontal y vertical
        for (int i = 0; i < size; i++) {
            // Horizontal
            String sequenceDna = dnaSequenceAnalyzerService.sequenceCount(dna[i]);
            if (!sequenceDna.isEmpty()) {
                mutantDna.add(sequenceDna);
            }

            // Vertical
            String verticalSequenceDna = dnaSequenceAnalyzerService.getVerticalSequence(dna, i);
            if (!verticalSequenceDna.isEmpty()) {
                mutantDna.add(verticalSequenceDna);
            }
        }

        // Comprobar diagonal
        for (int i = 0; i <= (size - SEQUENCE_SIZE); i++) {
            for (int j = 0; j <= (size - SEQUENCE_SIZE); j++) {
                String mainDiagonal = dnaSequenceAnalyzerService.getDiagonal(dna, i, j, true);
                String secondaryDiagonal = dnaSequenceAnalyzerService.getDiagonal(dna, i, j, false);

                dnaSequenceAnalyzerService.processDiagonal(mainDiagonal, mutantDna);
                dnaSequenceAnalyzerService.processDiagonal(secondaryDiagonal, mutantDna);
            }
        }

        if (new HashSet<>(mutantDna).size() > MIN_MUTANT_SEQUENCE) {
            statsService.updateStats(true);
            throw new RuntimeException();
        }

        Dna human = Dna.builder()
                .dna(dnaString)
                .build();
        humansRepository.save(human);
        statsService.updateStats(false);
    }
}

