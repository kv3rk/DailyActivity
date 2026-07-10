alter table stats_storage
    add column username text not null,
    add column stats_date date not null,
    add constraint fk_stats_to_users foreign key (username) references users (username);

alter table timer_activity
    alter column username set not null;