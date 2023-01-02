create table player
(
    id          serial primary key,
    name        varchar(255) not null,
    age         numeric,
    club        varchar(255) not null,
    nationality varchar(255) not null
)
