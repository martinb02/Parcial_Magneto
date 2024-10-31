package com.example.inicial1.mappers;

import com.example.inicial1.dtos.StatsDTO;
import com.example.inicial1.entities.Stats;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatsMapper {

    StatsDTO toStatsDTO(Stats stats);
}
