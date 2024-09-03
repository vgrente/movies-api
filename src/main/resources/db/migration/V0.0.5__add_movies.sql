SET SEARCH_PATH TO "movies-api";

TRUNCATE TABLE movie RESTART IDENTITY CASCADE;

INSERT INTO movie(id, title, release_date, director_id)
VALUES (1, 'From Russia With Love', '1964/09/12', 1),
       (2, 'GoldenEye', '1997/08/23', 2),
       (3, 'Casino Royale', '2006/11/16', 2),
       (4, 'Skyfall', '2012/10/31', 4),
       (5, 'No time to die', '2021/10/01', 5);

