create table roles
(
    id          UUID not null,
    role        text not null unique,
    description text not null,
    created_at  timestamp,
    constraint pk_daily_roles primary key (id)
);