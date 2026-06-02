CREATE TABLE daily_goals
(
    id        UUID         NOT NULL,
    goal_date date,
    goal_text VARCHAR(255) NOT NULL,
    done_flag BOOLEAN,
    CONSTRAINT pk_daily_goals PRIMARY KEY (id)
);

CREATE TABLE timer_activity
(
    id            UUID NOT NULL,
    activity_date date,
    activity_type VARCHAR(255),
    comment       VARCHAR(255),
    timer         BIGINT,
    CONSTRAINT pk_timer_activity PRIMARY KEY (id)
);

ALTER TABLE daily_goals
    ADD CONSTRAINT uc_3f50efbf08cef3f66428ba4c5 UNIQUE (goal_date, goal_text);

create table stats_storage
(
    id                    UUID         NOT NULL,
    term                  VARCHAR(255) not null,
    amount_goals          BIGINT,
    percentage_completion BIGINT,
    amount_activities     BIGINT,
    time_activities       NUMERIC(10,2),
    CONSTRAINT pk_stats_storage PRIMARY KEY (id)
);