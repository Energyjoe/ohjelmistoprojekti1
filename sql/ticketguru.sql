DROP TABLE IF EXISTS tapahtumaliput CASCADE;
DROP TABLE IF EXISTS liput CASCADE;
DROP TABLE IF EXISTS myynnit CASCADE;
DROP TABLE IF EXISTS tyontekijat CASCADE;
DROP TABLE IF EXISTS asiakastyypit CASCADE;
DROP TABLE IF EXISTS tapahtumat CASCADE;
DROP TABLE IF EXISTS tapahtumapaikat CASCADE;
DROP TABLE IF EXISTS postinumerot CASCADE;

-- postgresql tables
CREATE TABLE asiakastyypit (
    asiakastyyppi_id SERIAL PRIMARY KEY,
    asiakastyyppi VARCHAR(20) NOT NULL
);

CREATE TABLE postinumerot (
    postinumero CHAR(5) PRIMARY KEY,
    paikkakunta VARCHAR(50) NOT NULL,
    CHECK (char_length(postinumero) = 5)
);

CREATE TABLE tapahtumapaikat (
    tapahtumapaikka_id SERIAL PRIMARY KEY,
    postinumero CHAR(5),
    FOREIGN KEY (postinumero) REFERENCES postinumerot(postinumero),
    tapahtumapaikka VARCHAR(50) NOT NULL,
    kapasiteetti INTEGER NOT NULL,
    katuosoite VARCHAR(50),
    puhnro VARCHAR(15),
    email VARCHAR(254)
);

CREATE TABLE tapahtumat (
    tapahtuma_id SERIAL PRIMARY KEY,
    tapahtumapaikka_id INTEGER NOT NULL,
    FOREIGN KEY (tapahtumapaikka_id) REFERENCES tapahtumapaikat(tapahtumapaikka_id),
    tapahtuma VARCHAR(100) NOT NULL,
    aloitusaika TIMESTAMP NOT NULL,
    lopetusaika TIMESTAMP NOT NULL,
    kuvaus TEXT
);

CREATE TABLE tapahtumaliput (
    tapahtumalippu_id SERIAL PRIMARY KEY,
    asiakastyyppi_id INTEGER NOT NULL,
    FOREIGN KEY (asiakastyyppi_id) REFERENCES asiakastyypit(asiakastyyppi_id),
    tapahtuma_id INTEGER NOT NULL,
    FOREIGN KEY (tapahtuma_id) REFERENCES tapahtumat(tapahtuma_id),
    hinta DECIMAL(6,2) NOT NULL
);

CREATE TABLE tyontekijat (
    tyontekija_id SERIAL PRIMARY KEY,
    postinumero CHAR(5),
    FOREIGN KEY (postinumero) REFERENCES postinumerot(postinumero),
    katuosoite VARCHAR(50) NOT NULL,
    etunimi VARCHAR(50) NOT NULL,
    sukunimi VARCHAR(50) NOT NULL,
    email VARCHAR(254) NOT NULL,
    puhnro VARCHAR(15) NOT NULL,
    bcrypthash VARCHAR(60) NOT NULL
);

CREATE TABLE myynnit (
    myynti_id SERIAL PRIMARY KEY,
    tyontekija_id INTEGER NOT NULL,
    FOREIGN KEY (tyontekija_id) REFERENCES tyontekijat(tyontekija_id),
    myyntiaika TIMESTAMP NOT NULL,
    email VARCHAR(254)
);

CREATE TABLE liput (   
    lippu_id SERIAL PRIMARY KEY,
    myynti_id INTEGER NOT NULL,
    FOREIGN KEY (myynti_id) REFERENCES myynnit(myynti_id),
    tarkastuskoodi CHAR(8) NOT NULL
);

-- Insert sample data into postinumerot
INSERT INTO postinumerot (postinumero, paikkakunta) VALUES
('00100', 'Helsinki'),
('00180', 'Helsinki'),
('00200', 'Helsinki'),
('02100', 'Espoo'),
('33100', 'Tampere');

-- Insert sample data into asiakastyypit
INSERT INTO asiakastyypit (asiakastyyppi) VALUES
('Opiskelija'),
('Aikuinen'),
('Eläkeläinen'),
('Lapsi');

-- Insert sample data into tapahtumapaikat
INSERT INTO tapahtumapaikat (postinumero, tapahtumapaikka, kapasiteetti, katuosoite, puhnro, email) VALUES
('00100', 'Helsingin Jäähalli', 8200, 'Nordenskiöldinkatu 11-13', '09476621', 'info@helsinginjaahalli.fi'),
('02100', 'Espoo Metro Areena', 6982, 'Urheilupuistontie 3', '0942469030', 'info@espoometroareena.fi'),
('33100', 'Tampereen Jäähalli', 7300, 'Kissanmaankatu 9', '032600111', 'info@tampereenjaahalli.fi');

-- Insert sample data into tapahtumat
INSERT INTO tapahtumat (tapahtumapaikka_id, tapahtuma, aloitusaika, lopetusaika, kuvaus) VALUES
(1, 'HIFK - Kärpät', '2024-03-02 17:00:00', '2024-03-02 19:30:00', 'Liiga ottelu'),
(2, 'Blues - TPS', '2024-03-02 17:00:00', '2024-03-02 19:30:00', 'Liiga ottelu'),
(3, 'Ilves - Lukko', '2024-03-02 17:00:00', '2024-03-02 19:30:00', 'Liiga ottelu'),
(1, 'HIFK - Tappara', '2024-03-05 18:30:00', '2024-03-05 21:00:00', 'Liiga ottelu, runkosarja'),
(2, 'Metallica', '2024-06-16 19:00:00', '2024-06-16 23:00:00', 'Metallica M72 World Tour');

-- Insert sample data into tapahtumaliput
INSERT INTO tapahtumaliput (asiakastyyppi_id, tapahtuma_id, hinta) VALUES
(1, 1, 25.00),
(2, 1, 45.00),
(3, 1, 30.00),
(4, 1, 15.00),
(1, 5, 80.00),
(2, 5, 120.00);

-- Insert sample data into tyontekijat
INSERT INTO tyontekijat (postinumero, katuosoite, etunimi, sukunimi, email, puhnro, bcrypthash) VALUES
('00100', 'Mannerheimintie 1', 'Matti', 'Meikäläinen', 'matti.meikalainen@ticketguru.fi', '0401234567', '$2a$10$nOUIs5i5LQWJ/jKqIgvjRuWqnDREFUiouIjzEqr9RU9jG3vvzK9G6'),
('02100', 'Urheilupuistontie 3', 'Liisa', 'Lahtinen', 'liisa.lahtinen@ticketguru.fi', '0507654321', '$2a$10$R69vjJEdj2w2WZe6O4o2Oe9Sp4yExoWOs97YPLmK9LmjJBYAcXyca');

-- Insert sample data into myynti
INSERT INTO myynnit (tyontekija_id, myyntiaika, email) VALUES
(1, '2024-02-29 12:00:00', 'asiakas1@example.com'),
(2, '2024-02-29 13:30:00', 'asiakas2@example.com'),
(1, '2024-03-01 10:00:00', 'asiakas3@example.com');

-- Insert sample data into liput
INSERT INTO liput (myynti_id, tarkastuskoodi) VALUES
(1, 'ABCDEF01'),
(1, 'BCDEF012'),
(2, 'CDEF0123'),
(3, 'DEF01234');
