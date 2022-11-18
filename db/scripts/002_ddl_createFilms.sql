CREATE TABLE films (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    poster BYTEA NOT NULL
);

COMMENT ON TABLE films is 'фильмы';
COMMENT ON COLUMN films.id is 'идентификатор фильма';
COMMENT ON COLUMN films.name is 'название фильма';
COMMENT ON COLUMN films.description is 'описание фильма';
COMMENT ON COLUMN films.poster is 'постер фильма';
