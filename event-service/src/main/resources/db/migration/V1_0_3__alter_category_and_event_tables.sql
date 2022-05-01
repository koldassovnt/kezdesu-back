alter table category_dictionary
    add column img text,
    add column color varchar(150);

alter table event
    add column address text;
