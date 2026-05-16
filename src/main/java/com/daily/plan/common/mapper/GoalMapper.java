package com.daily.plan.DailyPlan.DTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface GoalMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "goalText", source = "goalText")
    @Mapping(target = "doneFlag", source = "doneFlag")
    GoalDTO 
}
