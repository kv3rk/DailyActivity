alter table daily_goals
    add column username text not null,
    add constraint fk_goals_to_users foreign key (username) references users (username);
