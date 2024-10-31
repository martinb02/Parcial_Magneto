package com.example.inicial1.services.impl;

import com.example.inicial1.dtos.StatsDTO;
import com.example.inicial1.entities.Stats;
import com.example.inicial1.mappers.StatsMapper;
import com.example.inicial1.repositories.StatsRepository;
import com.example.inicial1.services.StatsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final StatsMapper statsMapper;

    @Override
    public void updateStats(boolean isMutant) {
        Stats stats = getFirstStats();
        if (isMutant) {
            stats.setCountMutantDna(stats.getCountMutantDna() + 1);
        } else {
            stats.setCountHumanDna(stats.getCountHumanDna() + 1);
        }

        if (stats.getCountHumanDna() != 0) {
            int ratio = stats.getCountMutantDna() / stats.getCountHumanDna();
            stats.setRatio(ratio);
        }

        statsRepository.save(stats);
    }

    @Override
    public StatsDTO getStats() {
        Stats stats = getFirstStats();
        return statsMapper.toStatsDTO(stats);
    }

    private Stats getFirstStats() {
        return statsRepository.findAll().stream().findFirst().orElseThrow(RuntimeException::new);
    }
}

