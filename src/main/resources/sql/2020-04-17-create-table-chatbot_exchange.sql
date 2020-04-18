-- This extension should be used if UUID is necessary
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS chatbot_exchange (
    id SERIAL PRIMARY KEY,
    message_requested VARCHAR(8190) NULL,
    message_response VARCHAR(8190) NULL,
    in_error boolean NULL,
    user_id varchar NOT NULL,
    date_of_creation TIMESTAMP NOT NULL
);
