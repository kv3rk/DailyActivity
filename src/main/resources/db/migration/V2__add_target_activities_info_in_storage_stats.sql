alter table stats_storage
    add column if not exists backend NUMERIC (10, 2);

alter table stats_storage
    add column if not exists games NUMERIC (10, 2);

alter table stats_storage
    add column if not exists english NUMERIC (10, 2);