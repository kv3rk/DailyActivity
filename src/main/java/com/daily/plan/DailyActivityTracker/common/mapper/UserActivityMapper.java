package com.daily.plan.DailyActivityTracker.common.mapper;


import com.daily.plan.DailyActivityTracker.Settings.DTO.UserActivityDTOForStats;
import com.daily.plan.DailyActivityTracker.UserActivities.Entity.UserActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserActivityMapper {

    @Mapping(target = "activity1", source = "activity1")
    @Mapping(target = "activity2", source = "activity2")
    @Mapping(target = "activity3", source = "activity3")
    UserActivityDTOForStats userActivityToDTO(UserActivity userActivity);

    @Mapping(target = "activity1", source = "activity1")
    @Mapping(target = "activity2", source = "activity2")
    @Mapping(target = "activity3", source = "activity3")
    UserActivity userActivityDTOToEntity(UserActivityDTOForStats userActivityDTOForStats);
}

