SET SEARCH_PATH TO "movies-api";

TRUNCATE TABLE character RESTART IDENTITY CASCADE;

INSERT INTO character(name)
VALUES ('M'),
       ('James Bond'),
       ('Le Chiffre'),
       ('Aurik Goldfinger'),
       ('Sean Bean'),
       ('Vesper Lynd');

