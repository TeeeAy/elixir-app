CREATE TABLE "user"
(
    id       VARCHAR(200) PRIMARY KEY,
    name     VARCHAR(200) NOT NULL UNIQUE,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    coins INTEGER NOT NULL
);

CREATE TABLE ingredient
(
    id   VARCHAR(200) PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    type VARCHAR(100) NOT NULL,
    cost INTEGER      NOT NULL CHECK (cost >= 0)
);

CREATE TABLE elixir
(
    id    VARCHAR(200) PRIMARY KEY,
    name  VARCHAR(100) NOT NULL UNIQUE,
    cost  INTEGER      NOT NULL CHECK (cost >= 0),
    level INTEGER      NOT NULL CHECK (level > 0)
);

CREATE TABLE recipe
(
    elixir_id     VARCHAR(200) NOT NULL REFERENCES elixir,
    ingredient_id VARCHAR(200) NOT NULL REFERENCES ingredient,
    PRIMARY KEY (elixir_id, ingredient_id)
);
CREATE TABLE user_ingredients
(
    user_id       VARCHAR(200) NOT NULL REFERENCES "user",
    ingredient_id VARCHAR(200) NOT NULL REFERENCES ingredient,
    PRIMARY KEY (user_id, ingredient_id)
);
CREATE TABLE user_elixirs
(
    user_id   VARCHAR(200) NOT NULL REFERENCES "user",
    elixir_id VARCHAR(200) NOT NULL REFERENCES elixir,
    PRIMARY KEY (user_id, elixir_id)
);

INSERT INTO ingredient
VALUES ('id_ingr1','Stone', 'SOLID', 2),
       ('id_ingr2','Water', 'LIQUID', 2),
       ('id_ingr3','Mint', 'HERB', 1),
       ('id_ingr4','Moon dust', 'POWDER', 1);
INSERT INTO elixir
VALUES ('id_elx1','Love elixir', 10, 1),
       ('id_elx2','Magic elixir', 15, 2),
       ('id_elx3','Electro elixir', 13, 3);
INSERT INTO recipe
VALUES ('id_elx1', 'id_ingr1'),
       ('id_elx1', 'id_ingr2'),
       ('id_elx2', 'id_ingr1'),
       ('id_elx2', 'id_ingr2'),
       ('id_elx2', 'id_ingr4'),
       ('id_elx3', 'id_ingr2'),
       ('id_elx3', 'id_ingr3'),
       ('id_elx3', 'id_ingr4');