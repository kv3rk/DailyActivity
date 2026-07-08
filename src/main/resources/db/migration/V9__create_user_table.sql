create table users
(
    id        UUID not null,
    username  text not null unique,
    password  text not null,
    role_name text not null,
    theme     text,
    volume    smallint,
    telegram  text,
    constraint pk_users primary key (id),
    constraint fk_users_to_roles foreign key (role_name) references roles (role)
);