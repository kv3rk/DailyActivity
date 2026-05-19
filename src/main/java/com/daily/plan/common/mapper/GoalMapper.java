package com.daily.plan.common.mapper;

import com.daily.plan.DailyPlan.DTO.GoalDTO;
import com.daily.plan.DailyPlan.Entity.GoalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper (componentModel = "spring")
public interface GoalMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "goalText", source = "goalText")
    @Mapping(target = "doneFlag", source = "doneFlag")
    GoalDTO goalToGoalDTO(GoalEntity goalEntity);
}
