CREATE TABLE tickets
(
    id         SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions (id),
    pos_row    INT NOT NULL,
    cell       INT NOT NULL,
    user_id    INT NOT NULL REFERENCES users (id),
    UNIQUE (pos_row, cell, session_id)
);

COMMENT ON TABLE tickets is 'билеты';
COMMENT ON COLUMN tickets.id is 'идентификатор билета';
COMMENT ON COLUMN tickets.session_id is 'идентификатор сеанса';
COMMENT ON COLUMN tickets.pos_row is 'номер ряда';
COMMENT ON COLUMN tickets.cell is 'номер места';
COMMENT ON COLUMN tickets.user_id is 'идентификатор пользователя купившего билет';





