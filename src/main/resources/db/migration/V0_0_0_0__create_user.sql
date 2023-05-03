create table if not exists users
(
    id          uuid         not null,
    first_name  varchar(100) not null,
    last_name   varchar(100) not null,
    password    varchar(50)  not null ,
    UNIQUE      (id),
    PRIMARY KEY (id)
);
