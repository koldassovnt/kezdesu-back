create table account
(
    account      bigserial primary key not null,
    account_id   varchar(255) unique   not null,
    display_name varchar(150),
    phone        varchar(50),
    email        varchar(255) unique,
    password     text,
    created_at   timestamp default now(),
    actual       boolean default false
);

