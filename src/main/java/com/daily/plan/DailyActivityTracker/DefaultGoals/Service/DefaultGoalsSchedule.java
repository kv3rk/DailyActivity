package com.daily.plan.DailyActivityTracker.DefaultGoals.Service;

import com.daily.plan.DailyActivityTracker.DailyGoals.Entity.GoalEntity;
import com.daily.plan.DailyActivityTracker.DailyGoals.Repository.GoalRepository;
import com.daily.plan.DailyActivityTracker.DefaultGoals.config.DefaultGoalsProperties;
import com.daily.plan.DailyActivityTracker.User.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.IsoFields;

@Service
@EnableScheduling
@Slf4j
@Profile({"dev", "prod"})
public class DefaultGoalsSchedule {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final DefaultGoalsProperties goals;

    public DefaultGoalsSchedule(
            GoalRepository goalRepository,
            UserRepository userRepository,
            DefaultGoalsProperties goals) {

        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
        this.goals = goals;
    }

    public void createGoal(String text) {
        GoalEntity goal = new GoalEntity();

        goal.setGoalText(text);
        goal.setDoneFlag(false);
        goal.setUsername(
                userRepository.findByUsername("kv3rk")
        );

        goalRepository.save(goal);

        log.info("Created DEFAULT goal with text [{}] for user [{}]", text, "kv3rk");
    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Moscow")
    public void addDailyGoals() {
        createGoal(goals.getWater());
        createGoal(goals.getMorningRoutine());
        createGoal(goals.getEveningRoutine());
    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 0 0 * * MON-SAT", zone = "Europe/Moscow")
    public void addMonToSatGoals() {
        createGoal(goals.getEnglishSpeaking());
        createGoal(goals.getEnglishGrammar());
        createGoal(goals.getProgrammingInterview());
        createGoal(goals.getJobSearch());
    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 0 0 * * SUN", zone = "Europe/Moscow")
    public void addSundayGoals() {
        createGoal(goals.getCleaning());
        createGoal(goals.getWeeklyReview());
        createGoal(goals.getWeeklyWeigh());
    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 0 0 * * SUN", zone = "Europe/Moscow")
    public void addAlternatingSundayGoals() {
        int weekOfYear = LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        if (weekOfYear % 2 != 0) {
            createGoal(goals.getAnkiWeekly1());
        } else {
            createGoal(goals.getAnkiWeekly2());
        }
    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 0 0 * * SUN", zone = "Europe/Moscow")
    public void addEvery8WeeksGoal() {
        int weekOfYear = LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        if (weekOfYear % 8 == 0) {
            createGoal(goals.getToefl()); // Если нужна другая задача, просто замени getToefl()
        }
    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 0 0 * * MON,WED,FRI", zone = "Europe/Moscow")
    public void addMonWedFriGoals() {
        createGoal(goals.getEnglishAnki1());
        createGoal(goals.getRun());
    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 0 0 * * TUE,THU,SAT", zone = "Europe/Moscow")
    public void addTueThuSatGoals() {
        createGoal(goals.getEnglishAnki2());
        createGoal(goals.getStrength());
    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 0 0 * * MON,THU", zone = "Europe/Moscow")
    public void addMonThuGoals() {
        createGoal(goals.getEnglishWriting());
    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 0 0 * * TUE,FRI", zone = "Europe/Moscow")
    public void addTueFriGoals() {
        createGoal(goals.getEnglishReading());
    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 0 0 * * WED,SAT", zone = "Europe/Moscow")
    public void addWedSatGoals() {
        createGoal(goals.getEnglishListening());
    }
}