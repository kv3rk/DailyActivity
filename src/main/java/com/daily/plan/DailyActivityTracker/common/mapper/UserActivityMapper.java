package com.daily.plan.DailyActivityTracker.common.mapper;


import com.daily.plan.DailyActivityTracker.Settings.DTO.UserActivitiesDTO;
import com.daily.plan.DailyActivityTracker.UserActivities.Entity.UserActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserActivityMapper {

    @Mapping(target = "activity1", source = "activity1")
    @Mapping(target = "activity2", source = "activity2")
    @Mapping(target = "activity3", source = "activity3")
    UserActivitiesDTO userActivityToDTO(UserActivity userActivity);

    @Mapping(target = "activity1", source = "activity1")
    @Mapping(target = "activity2", source = "activity2")
    @Mapping(target = "activity3", source = "activity3")
    UserActivity userActivityDTOToEntity(UserActivitiesDTO userActivitiesDTO);
}

