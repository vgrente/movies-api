SET SEARCH_PATH TO "movies-api";

TRUNCATE TABLE director RESTART IDENTITY CASCADE;

INSERT INTO director(first_name, last_name)
VALUES ('Terence', 'Young'),
       ('Martin', 'Campbell'),
       ('John', 'Glen'),
       ('Sam', 'Mendes'),
       ('Cary Joji', 'Fukunaga');

