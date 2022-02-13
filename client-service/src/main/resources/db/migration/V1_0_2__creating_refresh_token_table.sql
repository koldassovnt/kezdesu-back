create table refresh_token
(
    id         bigserial primary key not null,
    client_id  varchar(255) unique   not null,
    token      text,
    expired_at timestamp
)