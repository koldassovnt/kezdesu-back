create table sms_for_auth
(
    phone   varchar(255) primary key not null,
    code    varchar(100),
    expired_at timestamp default now(),
    is_validate   boolean
);


