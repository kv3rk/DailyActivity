alter table stats_storage
    add column if not exists activity_type_4 NUMERIC (10, 2);

alter table stats_storage
    add column if not exists activity_type_5 NUMERIC (10, 2);