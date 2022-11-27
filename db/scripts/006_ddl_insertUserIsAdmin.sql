ALTER TABLE users
    ADD COLUMN isAdmin TEXT NOT NULL DEFAULT false;

COMMENT ON COLUMN users.isAdmin  IS 'Показывает является ли данный пользователем администратором'