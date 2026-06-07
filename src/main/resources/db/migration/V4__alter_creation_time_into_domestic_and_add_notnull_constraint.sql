alter table daily_goals
    alter column goal_date set not null;

alter table timer_activity
    alter column activity_date set not null;