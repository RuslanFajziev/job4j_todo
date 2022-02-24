CREATE TABLE user
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
);

CREATE TABLE items
(
    id          SERIAL PRIMARY KEY,
    description VARCHAR NOT NULL,
    created     timestamp without time zone,
    done        boolean NOT NULL,
    user_id     INT NOT NULL REFERENCES user (id)
);