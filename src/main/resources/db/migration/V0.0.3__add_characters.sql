SET SEARCH_PATH TO "movies-api";

TRUNCATE TABLE character RESTART IDENTITY CASCADE;

INSERT INTO character(id, name)
VALUES (1, 'M'),
       (2, 'James Bond'),
       (3, 'Le Chiffre'),
       (4, 'Aurik Goldfinger'),
       (5, 'Sean Bean'),
       (6, 'Vesper Lynd');

