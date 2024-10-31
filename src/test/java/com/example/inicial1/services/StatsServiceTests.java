package com.example.inicial1.services;

import com.example.inicial1.dtos.StatsDTO;
import com.example.inicial1.entities.Stats;
import com.example.inicial1.mappers.StatsMapper;
import com.example.inicial1.repositories.StatsRepository;
import com.example.inicial1.services.impl.StatsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTests {

    @InjectMocks
    private StatsServiceImpl statsService;

    @Mock
    private StatsRepository statsRepository;

    @Mock
    private StatsMapper statsMapper;

    private Stats stats;

    @BeforeEach
    void setUp() {
        stats = new Stats();
        stats.setCountMutantDna(0);
        stats.setCountHumanDna(0);
        stats.setRatio(0);
    }

    @Test
    void updateStats_whenIsMutantIncreasesMutantCount() {
        when(statsRepository.findAll()).thenReturn(Collections.singletonList(stats));

        statsService.updateStats(true);

        assertEquals(1, stats.getCountMutantDna());
        assertEquals(0, stats.getCountHumanDna());
        verify(statsRepository).save(stats);
    }

    @Test
    void updateStats_whenIsHumanIncreasesHumanCount() {
        when(statsRepository.findAll()).thenReturn(Collections.singletonList(stats));

        statsService.updateStats(false);

        assertEquals(0, stats.getCountMutantDna());
        assertEquals(1, stats.getCountHumanDna());
        verify(statsRepository).save(stats);
    }

    @Test
    void updateStats_calculatesRatioCorrectly() {
        stats.setCountMutantDna(2);
        stats.setCountHumanDna(2);
        when(statsRepository.findAll()).thenReturn(Collections.singletonList(stats));

        statsService.updateStats(false);

        assertEquals(2, stats.getCountMutantDna());
        assertEquals(3, stats.getCountHumanDna());
        assertEquals(0, stats.getRatio());
        verify(statsRepository).save(stats);
    }

    @Test
    void getStats_returnsCorrectStatsDTO() {
        when(statsRepository.findAll()).thenReturn(Collections.singletonList(stats));
        StatsDTO statsDTO = new StatsDTO(0, 0, new BigDecimal("0.0"));
        when(statsMapper.toStatsDTO(stats)).thenReturn(statsDTO);

        StatsDTO result = statsService.getStats();

        assertNotNull(result);
        assertEquals(0, result.getCountMutantDna());
        assertEquals(0, result.getCountHumanDna());
        assertEquals(new BigDecimal("0.0"), result.getRatio());
    }

    @Test
    void getFirstStats_whenNoStatsFound_throwsException() {
        when(statsRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> statsService.getStats());
    }
}

