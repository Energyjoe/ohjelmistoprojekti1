-- Postinumerot syötetään erillisellä skriptillä postinumerot.sql,
-- joka on suoritettava ennen tätä skriptiä.
-- -- Insert sample data into postinumerot
--  INSERT INTO postinumerot (postinumero, paikkakunta) VALUES
--  ('00100', 'Helsinki'),
--  ('00180', 'Helsinki'),
--  ('00200', 'Helsinki'),
--  ('02100', 'Espoo'),
--  ('33100', 'Tampere');
-- Insert sample data into asiakastyypit
INSERT INTO asiakastyypit (asiakastyyppi_id, asiakastyyppi)
VALUES (1, 'Opiskelija'),
    (2, 'Aikuinen'),
    (3, 'Eläkeläinen'),
    (4, 'Lapsi'),
    (5, 'Ovilippu');
-- Insert sample data into tapahtumapaikat
INSERT INTO tapahtumapaikat (
        tapahtumapaikka_id,
        postinumero,
        tapahtumapaikka,
        kapasiteetti,
        katuosoite,
        puhnro,
        email
    )
VALUES (
        1,
        '00100',
        'Helsingin Jäähalli',
        8200,
        'Nordenskiöldinkatu 11-13',
        '09476621',
        'info@helsinginjaahalli.fi'
    ),
    (
        2,
        '02100',
        'Espoo Metro Areena',
        6982,
        'Urheilupuistontie 3',
        '0942469030',
        'info@espoometroareena.fi'
    ),
    (
        3,
        '33100',
        'Tampereen Jäähalli',
        7300,
        'Kissanmaankatu 9',
        '032600111',
        'info@tampereenjaahalli.fi'
    );
-- Insert sample data into tapahtumat
INSERT INTO tapahtumat (
        tapahtumapaikka_id,
        tapahtuma,
        aloitusaika,
        lopetusaika,
        kuvaus
    )
VALUES (
        1,
        'HIFK - Kärpät',
        '2024-03-02 17:00:00',
        '2024-03-02 19:30:00',
        'Liiga ottelu'
    ),
    (
        2,
        'Blues - TPS',
        '2024-03-02 17:00:00',
        '2024-03-02 19:30:00',
        'Liiga ottelu'
    ),
    (
        3,
        'Ilves - Lukko',
        '2024-03-02 17:00:00',
        '2024-03-02 19:30:00',
        'Liiga ottelu'
    ),
    (
        1,
        'HIFK - Tappara',
        '2024-03-05 18:30:00',
        '2024-03-05 21:00:00',
        'Liiga ottelu, runkosarja'
    ),
    (
        2,
        'Metallica',
        '2024-06-16 19:00:00',
        '2024-06-16 23:00:00',
        'Metallica M72 World Tour'
    );
-- Insert sample data into tapahtumaliput
INSERT INTO tapahtumaliput (asiakastyyppi_id, tapahtuma_id, hinta)
VALUES (1, 1, 25.00),
    (2, 1, 45.00),
    (3, 1, 30.00),
    (4, 1, 15.00),
    (1, 5, 80.00),
    (2, 5, 120.00),
    (1, 5, 0.00);
-- Insert sample data into tyontekijat
INSERT INTO tyontekijat (
        tyontekija_id,
        postinumero,
        katuosoite,
        etunimi,
        sukunimi,
        email,
        puhnro,
        bcrypthash
    )
VALUES (
        1,
        '00100',
        'Mannerheimintie 1',
        'Matti',
        'Meikäläinen',
        'matti.meikalainen@ticketguru.fi',
        '0401234567',
        '$2a$10$nOUIs5i5LQWJ/jKqIgvjRuWqnDREFUiouIjzEqr9RU9jG3vvzK9G6'
    ),
    (
        2,
        '02100',
        'Urheilupuistontie 3',
        'Liisa',
        'Lahtinen',
        'liisa.lahtinen@ticketguru.fi',
        '0507654321',
        '$2a$10$R69vjJEdj2w2WZe6O4o2Oe9Sp4yExoWOs97YPLmK9LmjJBYAcXyca'
    ),
    (
        3,
        '00100',
        'Adminkuja 1',
        'Admin',
        'Admin',
        'admin@oprojekti1.com',
        '0401234567',
        '$2y$10$BX15rKckXurJqyAZH/mr9embv86c7TsyqeiWaF7OTnVZeaEFZY1ny'
    );
-- Insert sample data into myynti
INSERT INTO myynnit (myynti_id, tyontekija_id, myyntiaika, email)
VALUES (
        1,
        1,
        '2024-02-29 12:00:00',
        'asiakas1@example.com'
    ),
    (
        2,
        2,
        '2024-02-29 13:30:00',
        'asiakas2@example.com'
    ),
    (
        3,
        1,
        '2024-03-01 10:00:00',
        'asiakas3@example.com'
    );
-- Insert sample data into liput
INSERT INTO liput (
        myynti_id,
        tapahtumalippu_id,
        tarkistuskoodi,
        tarkistettu
    )
VALUES (1, 1, 'ABCDEF01', false),
    (1, 2, 'BCDEF012', false),
    (2, 3, 'CDEF0123', false),
    (3, 4, 'DEF01234', false);