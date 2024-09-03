SET SEARCH_PATH TO "movies-api";

TRUNCATE TABLE director RESTART IDENTITY CASCADE;

INSERT INTO director(id, first_name, last_name)
VALUES (1, 'Terence', 'Young'),
       (2, 'Martin', 'Campbell'),
       (3, 'John', 'Glen'),
       (4, 'Sam', 'Mendes'),
       (5, 'Cary Joji', 'Fukunaga');

