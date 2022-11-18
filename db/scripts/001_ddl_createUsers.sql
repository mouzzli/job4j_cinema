CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR not null,
    email VARCHAR NOT NULL UNIQUE,
    phone VARCHAR NOT NULL UNIQUE
);

comment on table users is 'Пользователи';
comment on column users.id is 'Идентификатор пользователя';
comment on column users.username is 'имя пользователя';
comment on column users.email is 'email пользователя';
comment on column users.phone is 'номер телефона пользователя';
