CREATE TABLE sessions (
    id SERIAL PRIMARY KEY,
    film_id INT NOT NULL REFERENCES films (id)
);

COMMENT ON TABLE sessions is 'сеансы';
COMMENT ON COLUMN sessions.id is 'идентификатор сеанса';
COMMENT ON COLUMN sessions.film_id is 'идентификатор фильма';
