alter table timer_activity
    add column username text,
    add constraint fk_timer_to_users foreign key (username) references users (username);
