SET SEARCH_PATH TO "movies-api";

TRUNCATE TABLE movie RESTART IDENTITY CASCADE;

INSERT INTO movie(title, release_date, director_id)
VALUES ('From Russia With Love', '1964/09/12', 1),
       ('GoldenEye', '1997/08/23', 2),
       ('Casino Royale', '2006/11/16', 2),
       ('Skyfall', '2012/10/31', 4),
       ('No time to die', '2021/10/01', 5);

