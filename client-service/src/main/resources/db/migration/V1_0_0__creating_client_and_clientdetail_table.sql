create table client
(
    client   bigserial primary key not null,
    phone    varchar(255) unique,
    email    varchar(255) unique,
    password varchar(255),
    created_at timestamp default now(),
    actual   boolean
);

create table client_detail
(
    client bigint primary key,
    displayName varchar(255),
    name varchar(255),
    surname varchar(255),
    img bytea,
    birthdate timestamp
);

