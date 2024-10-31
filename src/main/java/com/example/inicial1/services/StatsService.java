package com.example.inicial1.services;

import com.example.inicial1.dtos.StatsDTO;

public interface StatsService {

    void updateStats(boolean isMutant);

    StatsDTO getStats();
}
