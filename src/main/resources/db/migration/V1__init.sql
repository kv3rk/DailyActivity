CREATE TABLE daily_goals (
                             id UUID PRIMARY KEY,
                             goal_date DATE,
                             goal_text TEXT NOT NULL UNIQUE,
                             done_flag BOOLEAN DEFAULT FALSE
);