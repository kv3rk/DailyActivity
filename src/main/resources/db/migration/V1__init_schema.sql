
-- 1. Roles table (must be created before users)
CREATE TABLE roles
(
    id          UUID NOT NULL,
    role        TEXT NOT NULL UNIQUE,
    description TEXT NOT NULL,
    created_at  TIMESTAMP,
    CONSTRAINT pk_daily_roles PRIMARY KEY (id)
);

-- 2. Users table
CREATE TABLE users
(
    id        UUID NOT NULL,
    username  TEXT NOT NULL UNIQUE,
    password  TEXT NOT NULL,
    role_name TEXT NOT NULL,
    theme     TEXT DEFAULT 'light',
    volume    SMALLINT DEFAULT 50,
    telegram  TEXT,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT fk_users_to_roles FOREIGN KEY (role_name) REFERENCES roles (role)
);

-- 3. Daily goals table
CREATE TABLE daily_goals
(
    id        UUID         NOT NULL,
    username  TEXT         NOT NULL,
    goal_date DATE         NOT NULL,
    goal_text VARCHAR(255) NOT NULL,
    done_flag BOOLEAN,
    CONSTRAINT pk_daily_goals PRIMARY KEY (id),
    CONSTRAINT fk_unique_goals UNIQUE (goal_date, goal_text),
    CONSTRAINT fk_goals_to_users FOREIGN KEY (username) REFERENCES users (username)
);

-- 4. Timer activity table
CREATE TABLE timer_activity
(
    id            UUID NOT NULL,
    username      TEXT NOT NULL,
    activity_date DATE NOT NULL,
    activity_type VARCHAR(255),
    comment       VARCHAR(255),
    timer         BIGINT,
    CONSTRAINT pk_timer_activity PRIMARY KEY (id),
    CONSTRAINT fk_timer_to_users FOREIGN KEY (username) REFERENCES users (username)
);

-- 5. Stats storage table
CREATE TABLE stats_storage
(
    id                    UUID         NOT NULL,
    username              TEXT         NOT NULL,
    stats_date            DATE         NOT NULL,
    term                  VARCHAR(255) NOT NULL,
    amount_goals          BIGINT,
    percentage_completion BIGINT,
    amount_activities     BIGINT,
    time_activities       NUMERIC(10,2),
    activity_type_1       NUMERIC(10,2) DEFAULT 0.00,
    activity_type_2       NUMERIC(10,2) DEFAULT 0.00,
    activity_type_3       NUMERIC(10,2) DEFAULT 0.00,
    CONSTRAINT pk_stats_storage PRIMARY KEY (id),
    CONSTRAINT fk_stats_to_users FOREIGN KEY (username) REFERENCES users (username)
);

-- 6. User personal activities table
CREATE TABLE user_activity
(
    id        UUID NOT NULL,
    username  TEXT NOT NULL,
    activity1 TEXT DEFAULT 'Games',
    activity2 TEXT DEFAULT 'Studying',
    activity3 TEXT DEFAULT 'Music',
    CONSTRAINT pk_user_activity PRIMARY KEY (id),
    CONSTRAINT fk_user_activity_to_users FOREIGN KEY (username) REFERENCES users (username)
);