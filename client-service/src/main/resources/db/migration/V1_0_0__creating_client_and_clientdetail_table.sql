create table client
(
    client     bigserial primary key not null,
    client_id  varchar(255) unique   not null,
    phone      varchar(50) unique,
    email      varchar(255) unique,
    password   text,
    created_at timestamp default now(),
    actual     boolean
);

create table client_detail
(
    client      bigint primary key,
    displayName varchar(255),
    name        varchar(255),
    surname     varchar(255),
    img_id      varchar(255),
    birthdate   timestamp
);

