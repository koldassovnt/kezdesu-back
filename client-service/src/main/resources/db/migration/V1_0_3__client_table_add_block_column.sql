alter table client
add column blocked boolean default false,
add column blocked_reason text;