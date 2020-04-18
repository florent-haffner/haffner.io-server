-- This extension should be used if UUID is necessary
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS MESSAGE (
    id SERIAL PRIMARY KEY,
    message VARCHAR NULL,
    in_error boolean NULL
);
