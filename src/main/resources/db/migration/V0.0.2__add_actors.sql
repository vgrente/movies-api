SET SEARCH_PATH TO "movies-api";

TRUNCATE TABLE actor RESTART IDENTITY CASCADE;

INSERT INTO actor (id, first_name, last_name, birth_date, death_date)
VALUES (1, 'Sean', 'Connory', '1930/08/25', '2020/10/31'),
       (2, 'Roger', 'Moore', '1927/10/14', '2017/05/23'),
       (3, 'George', 'Lazenby', '1939/09/05', null),
       (4, 'Daniel', 'Craig', '1968/03/02', null),
       (5, 'Pierce', 'Brosnan', '1953/05/16', null),
       (6, 'Judi', 'Dench', '1934/12/09', null),
       (7, 'Mads', 'Mikkelsen', '1965/11/22', null),
       (8, 'Alec', 'Trevelyan', '1959/04/17', null),
       (9, 'Eva', 'Green', '1980/07/06', null);
