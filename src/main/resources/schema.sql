create table products(
    id uuid not null primary key,
    name varchar(100) not null,
    description varchar(300) not null,
    price numeric(18, 2),
    created_at timestamp,
    updated_at timestamp
);

create table users(
    id uuid not null primary key,
    name varchar(100) not null,
    email varchar(150) not null unique,
    password varchar(255) not null,
    role varchar(30) not null,
    created_at timestamp,
    updated_at timestamp,
    last_online_at timestamp
);