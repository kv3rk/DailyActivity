package com.daily.plan.DailyActivityTracker.DailyPlan.Service;

import com.daily.plan.DailyActivityTracker.DailyPlan.DTO.GoalDTO;
import com.daily.plan.DailyActivityTracker.DailyPlan.DTO.ToggleFlagDTO;
import com.daily.plan.DailyActivityTracker.DailyPlan.Entity.GoalEntity;
import com.daily.plan.DailyActivityTracker.DailyPlan.Repository.GoalRepository;
import com.daily.plan.DailyActivityTracker.common.exception.DuplicateGoalException;
import com.daily.plan.DailyActivityTracker.common.mapper.GoalMapper;
import com.daily.plan.DailyActivityTracker.common.unit.CurrentDateTime;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DailyPlanService {

    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;
    private final CurrentDateTime currentDateTime;

    public DailyPlanService(GoalRepository goalRepository, GoalMapper goalMapper, CurrentDateTime currentDateTime) {
        this.goalRepository = goalRepository;
        this.goalMapper = goalMapper;
        this.currentDateTime = currentDateTime;
    }

    @Transactional
    public GoalDTO save(GoalDTO goalDTO) {
        GoalEntity goalEntity = new GoalEntity();

        goalEntity.setGoalText(goalDTO.goalText());

        boolean exists =
                goalRepository.existsByGoalDateAndGoalText(
                        currentDateTime.getCurrentDate(),
                        goalDTO.goalText()
                );

        if (exists) {
            throw new DuplicateGoalException("Goal already exists for today");
        }

        GoalEntity saved = goalRepository.save(goalEntity);

        log.info("Saved entity in DB with text [{}]", goalDTO.goalText());

        GoalDTO fullDTO = goalMapper.goalToGoalDTO(saved);

        log.info("Return full DTO in refresh case: [{}], [{}], [{}]",
                fullDTO.id(), goalDTO.goalText(), fullDTO.doneFlag());

        return fullDTO;
    }

    public List<GoalDTO> getActiveGoals() {
        List<GoalDTO> goalDTOList = goalRepository.findAllByDoneFlagAndGoalDate(
                        false, currentDateTime.getCurrentDate()
                )
                .stream()
                .map(goalMapper::goalToGoalDTO)
                .toList();

        log.info("Return list of ACTIVE goals in size [{}]", goalDTOList.size());

        return goalDTOList;
    }

    public List<GoalDTO> getDoneGoals() {
        List<GoalDTO> goalDTOList = goalRepository.findAllByDoneFlagAndGoalDate(
                        true, currentDateTime.getCurrentDate()
                )
                .stream()
                .map(goalMapper::goalToGoalDTO)
                .toList();

        log.info("Return list of ACCOMPLISHED goals in size [{}]", goalDTOList.size());

        return goalDTOList;
    }

    @Transactional
    public GoalDTO toggleFlag(ToggleFlagDTO toggleFlagDTO) {

        GoalEntity goalEntity = goalRepository.findById(toggleFlagDTO.id()).orElseThrow();

        if (Boolean.TRUE.equals(goalEntity.getDoneFlag())) {
            throw new RuntimeException("Goal already done");
        } else {
            goalEntity.setDoneFlag(
                    Boolean.TRUE.equals(toggleFlagDTO.doneFlag())
            );

            log.info("Updated goal [{}] with text [{}] to flag [{}]",
                    goalEntity.getId(), goalEntity.getGoalText(), goalEntity.getDoneFlag());

            GoalDTO fullGoalDTO = goalMapper.goalToGoalDTO(goalEntity);

            log.info("Return full DTO [{}] with text [{}], flag [{}]",
                    goalEntity.getId(), goalEntity.getGoalText(), goalEntity.getDoneFlag());

            return fullGoalDTO;
        }
    }

    @Transactional
    public void deleteAll() {

        goalRepository.deleteAllByGoalDateBefore(
                currentDateTime.getCurrentDate().minusWeeks(2)
        );
    }

    public String getUsername() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return username;
    }
}

