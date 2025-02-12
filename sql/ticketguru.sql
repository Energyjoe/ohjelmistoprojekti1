DROP TABLE IF EXISTS liput;
DROP TABLE IF EXISTS tapahtumat;
DROP TABLE IF EXISTS asiakkaat;
DROP TABLE IF EXISTS tapahtumapaikat;
DROP TABLE IF EXISTS tapahtumat;
DROP TABLE IF EXISTS asiakastyypit;
DROP TABLE IF EXISTS myydytliput;
DROP TABLE IF EXISTS myyntitapahtumat;
DROP TABLE IF EXISTS postinumerot;

-- postgresql tables
CREATE TABLE asiakastyypit (
    asiakastyyppi_id SERIAL PRIMARY KEY,
    asiakastyyppi VARCHAR(20) NOT NULL
);

CREATE TABLE asiakkaat (
    asiakas_id SERIAL PRIMARY KEY,
    etunimi VARCHAR(50),
    sukunimi VARCHAR(50),
    email VARCHAR(254),
    puhelinnumero VARCHAR(15)
);

CREATE TABLE postinumerot (
    postinumero CHAR(5) PRIMARY KEY,
    postitoimipaikka VARCHAR(50) NOT NULL
);

CREATE TABLE tapahtumapaikat (
    tapahtumapaikka_id SERIAL PRIMARY KEY,
    postinumero CHAR(5),
    FOREIGN KEY (postinumero) REFERENCES postinumerot(postinumero),
    tapahtumapaikka VARCHAR(50) NOT NULL,
    kapasiteetti INTEGER NOT NULL,
    katuosoite VARCHAR(50)
);

CREATE TABLE tapahtumat (
    tapahtuma_id SERIAL PRIMARY KEY,
    tapahtumapaikka_id INTEGER,
    FOREIGN KEY (tapahtumapaikka_id) REFERENCES tapahtumapaikat(tapahtumapaikka_id),
    tapahtuma VARCHAR(100) NOT NULL,
    aloitusaika TIMESTAMP NOT NULL,
    lopetusaika TIMESTAMP NOT NULL,
    kuvaus VARCHAR(300)
);

CREATE TABLE myyntitapahtumat (
    myyntitap_id SERIAL PRIMARY KEY,
    asiakas_id INTEGER,
    FOREIGN KEY (asiakas_id) REFERENCES asiakkaat(asiakas_id),
    myyntiaika TIMESTAMP NOT NULL
);

CREATE TABLE liput (
    lippu_id SERIAL PRIMARY KEY,
    asiakastyyppi_id INTEGER,
    FOREIGN KEY (asiakastyyppi_id) REFERENCES asiakastyypit(asiakastyyppi_id),
    tapahtuma_id INTEGER,
    FOREIGN KEY (tapahtuma_id) REFERENCES tapahtumat(tapahtuma_id),
    hinta DECIMAL(6,2) NOT NULL
);

CREATE TABLE myydytliput (
    myyntitap_id INTEGER,
    FOREIGN KEY (myyntitap_id) REFERENCES myyntitapahtumat(myyntitap_id),
    lippu_id INTEGER,
    FOREIGN KEY (lippu_id) REFERENCES liput(lippu_id),
    tarkastuskoodi CHAR(8) NOT NULL
);

INSERT INTO asiakastyypit (asiakastyyppi) VALUES ('Aikuinen'), ('Lapsi'), ('Opiskelija'), ('Eläkeläinen'), ('Varusmies');
INSERT INTO postinumerot (postinumero, postitoimipaikka) VALUES ('00100', 'Helsinki'), ('00200', 'Helsinki'), ('00300', 'Helsinki'), ('00400', 'Helsinki'), ('00500', 'Helsinki');
INSERT INTO asiakkaat (etunimi, sukunimi, email, puhelinnumero) VALUES ('Matti', 'Meikäläinen', 'mattim@matti.com', '0401234567'), ('Maija', 'Mallikas', 'maijam@maija.com', '0501234567');
INSERT INTO tapahtumapaikat (postinumero, tapahtumapaikka, kapasiteetti, katuosoite) VALUES ('00100', 'Helsingin jäähalli', 10000, 'Nordenskiöldinkatu 11-13'), ('00200', 'Helsingin Messukeskus', 5000, 'Messuaukio 1'), ('00300', 'Helsingin Kulttuuritalo', 2000, 'Sturenkatu 4'), ('00400', 'Helsingin Konservatorio', 500, 'Ruoholahdentori 6'), ('00500', 'Helsingin Kaupunginteatteri', 700, 'Helsinginkatu 1');
INSERT INTO tapahtumat (tapahtumapaikka_id, tapahtuma, aloitusaika, lopetusaika, kuvaus) VALUES (1, 'Jääkiekko: HIFK - Kärpät', '2020-10-01 18:30:00', '2020-10-01 21:00:00', 'HIFK:n ja Kärppien välinen ottelu'), (2, 'Kirjamessut', '2020-10-02 10:00:00', '2020-10-04 18:00:00', 'Kirjallisuuden ystävien tapahtuma'), (3, 'Konsertti: Helsingin kaupunginorkesteri', '2020-10-03 19:00:00', '2020-10-03 21:00:00', 'Helsingin kaupunginorkesterin konsertti'), (4, 'Konsertti: Helsingin konservatorion oppilaskonsertti', '2020-10-04 18:00:00', '2020-10-04 20:00:00', 'Helsingin konservatorion oppilaiden konsertti'), (5, 'Teatteri: Kultakuoriainen', '2020-10-05 19:00:00', '2020-10-05 21:00:00', 'Helsingin kaupunginteatterin esitys');
INSERT INTO liput (asiakastyyppi_id, tapahtuma_id, hinta) VALUES (1, 1, 30.00), (2, 1, 15.00), (3, 1, 20.00), (4, 1, 20.00), (5, 1, 20.00), (1, 2, 10.00), (2, 2, 5.00), (3, 2, 7.00), (4, 2, 7.00), (5, 2, 7.00), (1, 3, 25.00), (2, 3, 12.50), (3, 3, 17.50), (4, 3, 17.50), (5, 3, 17.50), (1, 4, 5.00), (2, 4, 2.50), (3, 4, 3.50), (4, 4, 3.50), (5, 4, 3.50), (1, 5, 15.00), (2, 5, 7.50), (3, 5, 10.50), (4, 5, 10.50), (5, 5, 10.50);
INSERT INTO myyntitapahtumat (asiakas_id, myyntiaika) VALUES (1, '2020-09-01 12:00:00'), (2, '2020-09-01 12:00:00');
INSERT INTO myydytliput (myyntitap_id, lippu_id, tarkastuskoodi) VALUES (1, 1, '12345678'), (1, 2, '23456789'), (2, 3, '34567890'), (2, 4, '45678901');