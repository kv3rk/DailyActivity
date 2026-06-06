alter table daily_goals
    add constraint not_nullable_date not null (goal_date);

alter table timer_activity
    add constraint not_nullable_date not null (activity_date);