ALTER TABLE sessions
    ADD COLUMN date_Time TIMESTAMP NOT NULL;
CREATE UNIQUE INDEX uq_session_dateTime_filmId ON sessions (date_Time, film_id);

COMMENT ON COLUMN sessions.date_Time IS 'время сеанса'