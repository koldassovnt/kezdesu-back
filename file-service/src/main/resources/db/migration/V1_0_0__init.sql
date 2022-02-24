create table file
(
    file       bigserial primary key not null,
    file_id    varchar(255) unique   not null,
    label      varchar(150),
    mime_type  varchar(50),
    content    text,
    created_at timestamp default now()
);
