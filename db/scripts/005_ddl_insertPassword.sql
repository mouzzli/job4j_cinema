ALTER TABLE users ADD COLUMN password TEXT NOT NULL;

COMMENT ON COLUMN users.password is 'Пароль пользователя';
