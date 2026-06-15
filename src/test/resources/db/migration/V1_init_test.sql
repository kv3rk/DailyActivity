CREATE TABLE daily_goals
(
    id        UUID         NOT NULL,
    goal_date date         NOT NULL,
    goal_text VARCHAR(255) NOT NULL,
    done_flag BOOLEAN,
    CONSTRAINT pk_daily_goals PRIMARY KEY (id),
    CONSTRAINT uc_3f50efbf08cef3f66428ba4c5 UNIQUE (goal_date, goal_text)
);

CREATE TABLE timer_activity
(
    id            UUID NOT NULL,
    activity_date date NOT NULL,
    activity_type VARCHAR(255),
    comment       VARCHAR(255),
    timer         BIGINT,
    CONSTRAINT pk_timer_activity PRIMARY KEY (id)
);

CREATE TABLE stats_storage
(
    id                    UUID         NOT NULL,
    term                  VARCHAR(255) NOT NULL,
    amount_goals          BIGINT,
    percentage_completion BIGINT,
    amount_activities     BIGINT,
    time_activities       NUMERIC(10, 2),
    activity_type_1       NUMERIC(10, 2) DEFAULT 0.00,
    activity_type_2       NUMERIC(10, 2) DEFAULT 0.00,
    activity_type_3       NUMERIC(10, 2) DEFAULT 0.00,
    activity_type_4       NUMERIC(10, 2) DEFAULT 0.00,
    activity_type_5       NUMERIC(10, 2) DEFAULT 0.00,
    activity_type_6       NUMERIC(10, 2) DEFAULT 0.00,
    CONSTRAINT pk_stats_storage PRIMARY KEY (id)
);