-- This script handle the refactoring of chatbot_exchange table to move to a new paradigm.
-- We indeed need to handle list of item, that's what this script is about.

DROP TABLE IF EXISTS chatbot_exchange;

CREATE TABLE IF NOT EXISTS chatbot_message
(
    id               SERIAL PRIMARY KEY,
    text             VARCHAR(8190) NULL,
    conversation_id  varchar       NOT NULL,
    user_id          int           NULL,
    date_of_creation TIMESTAMP     NOT NULL
);

CREATE TABLE IF NOT EXISTS chatbot_message_in_error
(
    id                SERIAL PRIMARY KEY,
    message_requested VARCHAR(8190) NOT NULL,
    message_response  VARCHAR(8190) NOT NULL,
    conversation_id   varchar       NOT NULL,
    date_of_creation  TIMESTAMP     NOT NULL
);

CREATE TABLE IF NOT EXISTS app_user
(
    id        SERIAL PRIMARY KEY,
    privilege varchar NULL,
    name      varchar NULL,
    password  varchar NULL
);

INSERT INTO app_user(privilege, name, password)
VALUES ('user', 'chatbot', '');

ALTER TABLE chatbot_message
    ADD CONSTRAINT chatbot_message_owner_id
        FOREIGN KEY (user_id) REFERENCES app_user (id);

