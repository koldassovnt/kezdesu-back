create table complain
(
    complain      bigserial primary key not null,
    complain_id   varchar(255) unique   not null,
    client_id     varchar(255),
    complain_text text
);

create table event_complain
(
    event_id    varchar(255),
    complain_id varchar(255),
    created_at  timestamp default now(),
    primary key (event_id, complain_id)
);
