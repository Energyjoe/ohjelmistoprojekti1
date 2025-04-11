DROP TABLE IF EXISTS tapahtumaliput CASCADE;
DROP TABLE IF EXISTS liput CASCADE;
DROP TABLE IF EXISTS myynnit CASCADE;
DROP TABLE IF EXISTS tyontekijat CASCADE;
DROP TABLE IF EXISTS asiakastyypit CASCADE;
DROP TABLE IF EXISTS tapahtumat CASCADE;
DROP TABLE IF EXISTS tapahtumapaikat CASCADE;
DROP TABLE IF EXISTS postinumerot CASCADE;
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
    kuvaus TEXT,
    kapasiteetti INTEGER
);
CREATE TABLE tapahtumaliput (
    tapahtumalippu_id SERIAL PRIMARY KEY,
    asiakastyyppi_id INTEGER NOT NULL,
    FOREIGN KEY (asiakastyyppi_id) REFERENCES asiakastyypit(asiakastyyppi_id),
    tapahtuma_id INTEGER NOT NULL,
    FOREIGN KEY (tapahtuma_id) REFERENCES tapahtumat(tapahtuma_id),
    hinta DECIMAL(6, 2) NOT NULL
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
    tapahtumalippu_id INTEGER NOT NULL,
    FOREIGN KEY (tapahtumalippu_id) REFERENCES tapahtumaliput(tapahtumalippu_id),
    tarkistuskoodi CHAR(8) NOT NULL,
    tarkistettu BOOLEAN NOT NULL DEFAULT FALSE
);