package com.daily.plan.DailyPlan.Service;

import com.daily.plan.DailyPlan.DTO.GoalDTO;
import com.daily.plan.DailyPlan.DTO.ToggleFlagDTO;
import com.daily.plan.DailyPlan.Entity.GoalEntity;
import com.daily.plan.DailyPlan.Repository.GoalRepository;
import com.daily.plan.common.mapper.GoalMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DailyPlanService {
    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;
    private final OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor;
    private final PersistenceManagedTypes persistenceManagedTypes;

    public DailyPlanService(GoalRepository goalRepository, GoalMapper goalMapper, OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor, PersistenceManagedTypes persistenceManagedTypes) {
        this.goalRepository = goalRepository;
        this.goalMapper = goalMapper;
        this.openEntityManagerInViewInterceptor = openEntityManagerInViewInterceptor;
        this.persistenceManagedTypes = persistenceManagedTypes;
    }

    @Transactional
    public GoalDTO save(GoalDTO goalDTO) {
        GoalEntity goalEntity = new GoalEntity();

        goalEntity.setGoalText(goalDTO.goalText());

        goalRepository.save(goalEntity);

        log.info("Saved entity in DB with text [{}]", goalDTO.goalText());

        GoalDTO fullDTO = goalMapper.goalToGoalDTO(goalEntity);

        log.info("Return full DTO in refresh case: [{}], [{}], [{}]",
                fullDTO.id(), goalDTO.goalText(), fullDTO.doneFlag());

        return fullDTO;
    }

    public List<GoalDTO> getActiveGoals() {
        List<GoalDTO> goalDTOList = goalRepository.findAllByDoneFlag(false)
                .stream()
                .map(goalMapper::goalToGoalDTO)
                .toList();

        log.info("Return list of ACTIVE goals in size [{}]", goalDTOList.size());

        return goalDTOList;
    }

    public List<GoalDTO> getDoneGoals() {
        List<GoalDTO> goalDTOList = goalRepository.findAllByDoneFlag(true)
                .stream()
                .map(goalMapper::goalToGoalDTO)
                .toList();

        log.info("Return list of ACCOMPLISHED goals in size [{}]", goalDTOList.size());

        return goalDTOList;
    }

    @Transactional
    public GoalDTO toggleFlag(ToggleFlagDTO toggleFlagDTO) {

        GoalEntity goalEntity = goalRepository.findById(toggleFlagDTO.id()).orElseThrow();

        if (goalEntity.getDoneFlag() == true) {
            throw new RuntimeException("Goal already done");
        } else {
            goalEntity.setDoneFlag(toggleFlagDTO.doneFlag());

            log.info("Updated goal [{}] with text [{}] to flag [{}]",
                    goalEntity.getId(), goalEntity.getGoalText(), goalEntity.getDoneFlag());

            goalRepository.flush();

            log.info("Flushed data in DB after update");

            GoalDTO fullGoalDTO = goalMapper.goalToGoalDTO(goalEntity);

            log.info("Return full DTO [{}] with text [{}], flag [{}]",
                    goalEntity.getId(), goalEntity.getGoalText(), goalEntity.getDoneFlag());

            return fullGoalDTO;
        }
    }

    @Transactional
    public void deleteAll() {
        goalRepository.deleteAll();
    }
}

