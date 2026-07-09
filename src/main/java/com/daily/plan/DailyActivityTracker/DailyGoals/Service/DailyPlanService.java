package com.daily.plan.DailyActivityTracker.DailyGoals.Service;

import com.daily.plan.DailyActivityTracker.Authenticate.Service.AuthenticateService;
import com.daily.plan.DailyActivityTracker.DailyGoals.DTO.GoalDTO;
import com.daily.plan.DailyActivityTracker.DailyGoals.DTO.ToggleFlagDTO;
import com.daily.plan.DailyActivityTracker.DailyGoals.Entity.GoalEntity;
import com.daily.plan.DailyActivityTracker.DailyGoals.Repository.GoalRepository;
import com.daily.plan.DailyActivityTracker.User.Repository.UserRepository;
import com.daily.plan.DailyActivityTracker.common.exception.DuplicateGoalException;
import com.daily.plan.DailyActivityTracker.common.mapper.GoalMapper;
import com.daily.plan.DailyActivityTracker.common.unit.CurrentDateTime;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DailyPlanService {

    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;
    private final CurrentDateTime currentDateTime;
    private final AuthenticateService authenticateService;
    private final UserRepository userRepository;

    public DailyPlanService(GoalRepository goalRepository,
                            GoalMapper goalMapper,
                            CurrentDateTime currentDateTime,
                            AuthenticateService authenticateService,
                            UserRepository userRepository) {

        this.goalRepository = goalRepository;
        this.goalMapper = goalMapper;
        this.currentDateTime = currentDateTime;
        this.authenticateService = authenticateService;

        this.userRepository = userRepository;
    }

    @Transactional
    public GoalDTO save(GoalDTO goalDTO) {
        GoalEntity goalEntity = new GoalEntity();

        goalEntity.setGoalText(goalDTO.goalText());

        goalEntity.setUsername(userRepository.findByUsername(authenticateService.getUsername()));

        boolean exists =
                goalRepository.existsByGoalDateAndGoalTextAndUsername(
                        currentDateTime.getCurrentDate(),
                        goalDTO.goalText(),
                        userRepository.findByUsername(authenticateService.getUsername())
                );

        if (exists) {
            throw new DuplicateGoalException("Goal already exists for today");
        }

        GoalEntity saved = goalRepository.save(goalEntity);

        log.info("Saved entity in DB with text [{}] for user [{}]",
                goalDTO.goalText(), authenticateService.getUsername());

        GoalDTO fullDTO = goalMapper.goalToGoalDTO(saved);

        return fullDTO;
    }

    public List<GoalDTO> getActiveGoals() {
        List<GoalDTO> goalDTOList = goalRepository.findAllByDoneFlagAndGoalDateAndUsername(
                        false,
                        currentDateTime.getCurrentDate(),
                        userRepository.findByUsername(authenticateService.getUsername())
                )
                .stream()
                .map(goalMapper::goalToGoalDTO)
                .toList();

        log.info("Return list of ACTIVE goals in size [{}] for user [{}]",
                goalDTOList.size(), authenticateService.getUsername());

        return goalDTOList;
    }

    public List<GoalDTO> getDoneGoals() {
        List<GoalDTO> goalDTOList = goalRepository.findAllByDoneFlagAndGoalDateAndUsername(
                        true,
                        currentDateTime.getCurrentDate(),
                        userRepository.findByUsername(authenticateService.getUsername())
                )
                .stream()
                .map(goalMapper::goalToGoalDTO)
                .toList();

        log.info("Return list of ACCOMPLISHED goals in size [{}] for user [{}]",
                goalDTOList.size(), authenticateService.getUsername());

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

            return fullGoalDTO;
        }
    }

    @Transactional
    public void deleteAll() {

        goalRepository.deleteAllByGoalDateBefore(
                currentDateTime.getCurrentDate().minusDays(1)
        );
    }

}

