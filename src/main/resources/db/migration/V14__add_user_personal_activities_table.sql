create table user_activity
(
    id        UUID not null,
    username  text not null,
    activity1  text default 'Games',
    activity2  text default 'Studying',
    activity3  text default 'Music',
    constraint pk_user_activity primary key (id),
    constraint fk_user_activity_to_users foreign key (username) references users (username)
);