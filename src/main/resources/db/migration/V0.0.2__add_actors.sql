SET SEARCH_PATH TO "movies-api";

TRUNCATE TABLE actor RESTART IDENTITY CASCADE;

INSERT INTO actor (first_name, last_name, birth_date, death_date)
VALUES ('Sean', 'Connory', '1930/08/25', '2020/10/31'),
       ('Roger', 'Moore', '1927/10/14', '2017/05/23'),
       ('George', 'Lazenby', '1939/09/05', null),
       ('Daniel', 'Craig', '1968/03/02', null),
       ('Pierce', 'Brosnan', '1953/05/16', null),
       ('Judi', 'Dench', '1934/12/09', null),
       ('Mads', 'Mikkelsen', '1965/11/22', null),
       ('Alec', 'Trevelyan', '1959/04/17', null),
       ('Eva', 'Green', '1980/07/06', null);
