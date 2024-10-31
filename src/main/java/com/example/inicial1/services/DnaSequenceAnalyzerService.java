package com.example.inicial1.services;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.inicial1.services.DnaValidationService.SEQUENCE_SIZE;

@Service
public class DnaSequenceAnalyzerService {

    public String sequenceCount(String sequence) {
        int consecutive = 0;
        char lastChar = ' ';
        String dna = "";

        for (char c : sequence.toCharArray()) {
            if (c == lastChar) {
                consecutive++;
                if (consecutive == 3) {
                    dna = String.valueOf(lastChar).repeat(SEQUENCE_SIZE);
                }
            } else {
                consecutive = 0;
            }
            lastChar = c;
        }
        return dna;
    }

    public String getVerticalSequence(String[] dna, int i) {
        StringBuilder verticalSequence = new StringBuilder();
        for (String j : dna) {
            verticalSequence.append(j.charAt(i));
        }

        return sequenceCount(verticalSequence.toString());
    }

    public String getDiagonal(String[] dna, int row, int col, boolean main) {
        StringBuilder diagonal = new StringBuilder();
        for (int k = 0; k < SEQUENCE_SIZE; k++) {
            int currentRow = row + k;
            int currentCol = main ? col + k : col + (SEQUENCE_SIZE - 1 - k);
            diagonal.append(dna[currentRow].charAt(currentCol));
        }
        return diagonal.toString();
    }

    public void processDiagonal(String diagonal, List<String> mutantDna) {
        String diagonalSequenceDna = sequenceDiagonalCount(diagonal);
        if (!diagonalSequenceDna.isEmpty()) {
            mutantDna.add(diagonalSequenceDna);
        }
    }

    private String sequenceDiagonalCount(String sequence) {
        char character = sequence.charAt(0);
        for (char c : sequence.toCharArray()) {
            if (c != character) {
                return "";
            }
        }
        return sequence;
    }
}

