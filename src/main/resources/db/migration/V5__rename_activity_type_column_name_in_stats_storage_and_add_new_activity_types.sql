alter table stats_storage
    rename column backend to activity_type_1;

alter table stats_storage
    rename column games to activity_type_2;

alter table stats_storage
    rename column english to activity_type_3;