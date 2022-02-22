create table event
(
    event       bigserial primary key not null,
    event_id    varchar(255) unique   not null,
    label       varchar(150),
    description text,
    started_at  timestamp,
    ended_at    timestamp,
    latitude    float,
    longitude   float,
    category_id varchar(255),
    business    boolean   default false,
    actual      boolean   default false,
    blocked     boolean   default false,
    created_at  timestamp default now()
);

create table event_creator
(
    event_id   varchar(255),
    client_id  varchar(255),
    created_at timestamp default now(),
    primary key (event_id, client_id)
);


create table event_participant
(
    event_id   varchar(255),
    client_id  varchar(255),
    created_at timestamp default now(),
    primary key (event_id, client_id)
);

create table event_content
(
    event_id varchar(255),
    file_id  varchar(255),
    primary key (event_id, file_id)
);

create table city_dictionary
(
    city       bigserial primary key not null,
    city_id    varchar(255) unique   not null,
    city_label varchar(255),
    latitude   float,
    longitude  float,
    actual     boolean default false
);

create table category_dictionary
(
    category       bigserial primary key not null,
    category_id    varchar(255) unique   not null,
    category_label varchar(255),
    actual         boolean default false
);

