package com.daily.plan.DailyActivityTracker.DefaultGoals.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "default.goal")
public class DefaultGoalsProperties {

    private String englishAnki1;
    private String englishAnki2;
    private String englishSpeaking;
    private String englishGrammar;
    private String englishWriting;
    private String englishReading;
    private String englishListening;
    private String programmingInterview;
    private String water;
    private String run;
    private String cleaning;
    private String toefl;
    private String weeklyReview;
    private String strength;
    private String weeklyWeigh;
    private String jobSearch;
    private String morningRoutine;
    private String eveningRoutine;
    private String ankiWeekly1;
    private String ankiWeekly2;
}
