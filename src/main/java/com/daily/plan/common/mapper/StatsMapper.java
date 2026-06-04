package com.daily.plan.common.mapper;

import com.daily.plan.StatsStorage.DTO.StatsDTO;
import com.daily.plan.StatsStorage.Entity.StatsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("disabled")
@Component
@Mapper(componentModel = "spring")
public interface StatsMapper {

    @Mapping(target = "term", source = "term")
    @Mapping(target = "amountGoals", source = "amountGoals")
    @Mapping(target = "percentageCompletion", source = "percentageCompletion")
    @Mapping(target = "amountActivities", source = "amountActivities")
    @Mapping(target = "backend", source = "backend")
    @Mapping(target = "games", source = "games")
    @Mapping(target = "english", source = "english")
    @Mapping(target = "timeActivities", source = "timeActivities")
    StatsDTO statsToStatsDTO(StatsEntity statsEntity);
}
