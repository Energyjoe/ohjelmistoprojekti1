# TicketGuru

Tiimi: Lotta Gardin, Jenni Järvinen, Anni Lehtimäki, Miika Nordblad, Samuli Tani

## Johdanto

TicketGuru on lipputoimistolle tuleva lipunmyyntijärjestelmä, jossa pystyy myymään sekä tulostamaan pääsylippuja, sekä tarkistamaan pääsylippujen tietoja. Järjestelmässä voidaan määritellä erilaisia lipputyyppejä ja lippujen hintoja sekä lisätä, poistaa ja muokata tapahtumia, joihin lippuja myydään. Järjestelmän kautta saadaan myös kerättyä dataa asiakkaiden ostokäyttäytymisestä myyntiraporttien muodossa.

Järjestelmää käyttävät pääsääntöisesti lipunmyyjät, lipuntarkastajat sekä järjestelmän ylläpitäjä, mutta tulevaisuudessa lipunmyyntijärjestelmä laajentuu verkkokaupaksi, jolloin sen käyttäjiksi tulevat myös lippuja ostavat asiakkaat.

## Järjestelmän määrittely

Lipunmyyntijärjestelmän käyttäjärooleja ovat myyjä, asiakas, lipuntarkastaja ja järjestelmän ylläpitäjä. Myyjä myy ja tulostaa liput asiakkaalle lipunmyyntipisteessä. Lisäksi myyjä tulostaa myymättä jääneet liput tapahtumapäivänä ovella myytäväksi. Asiakas ostaa lipun myyjältä lipunmyyntipisteessä. Lipuntarkastaja tarkastaa liput ovella tapahtuman alkaessa lippuun merkityn koodin avulla ja merkitsee tarkastetut liput käytetyiksi. Järjestelmän ylläpitäjä huolehtii sen toiminnasta lippukaupan jokapäiväisessä toiminnassa.

### Käytettävät teknologiat

- Frontend: React.js
- Backend: Java Spring Boot
- Tietokanta: H2 Database tai PostgreSQL
- Päätelaitteet: Lipunmyyntijärjestelmää pystyy käyttämään tietokoneella, sekä tabletilla ja mobiililaitteella selaimessa.

### Käyttäjätarinat

#### Myyjä

- Myyjänä haluan pystyä myymään ja tulostamaan lipun asiakkaalle muutamalla klikkauksella, jotta myyntitilanne etenee sujuvasti eikä jono kasva liian pitkäksi.
- Myyjänä haluan lisätä useita lippuja samaan myyntitapahtumaan, jotta perhe- ja ryhmäasiakkaat saavat kaikki lippunsa yhdellä kertaa.
- Myyjänä haluan voida myydä opiskelija-, eläkeläis- ja varusmiesalennuksia, jotta mahdollisimman monella olisi mahdollisuus hankkia lippuja tapahtumiin.
- Myyjänä haluan nähdä jäljellä olevien lippujen määrän, jotta voin kertoa asiakkaalle, onko tapahtumaan vielä tilaa.
- Myyjänä haluan, että järjestelmä estää lipun myynnin, kun tapahtuman maksimikapasiteetti on saavutettu, jotta kaikki lipun ostaneet varmasti mahtuvat tapahtumapaikalle.
- Myyjänä haluan pystyä peruuttamaan myyntitapahtuman ennen maksun suorittamista, jotta voin korjata virheelliset syötöt nopeasti.
- Myyjänä haluan tarkastella päivän myyntiraportteja helposti, jotta voin varmistaa, että kassa täsmää ja kaikki myyntitapahtumat on kirjattu oikein.

#### Asiakas

- Asiakkaana haluan, että sopivan tapahtuman etsiminen ja valitseminen on nopeaa, jotta lipun ostaminen olisi sujuvaa.
- Asiakkaana haluan, että eri tapahtumien hinnat ovat selkeästi nähtävissä, jotta voin valita itselleni parhaan vaihtoehdon.
- Asiakkaana haluan, että ostostani jää tiedot sähköpostiini tai lipunmyyjälle siltä varalta, että hukkaan lippuni.
- Asiakkaana haluan saada kuitin ja oston ehdot, jotta tiedän, onko lipuillani palautus- tai vaihto-oikeutta.
- Asiakkaana haluan, että ostoskorin liput ovat selkeästi listattu, jotta voin itse tarkastaa, että ostamani liput ovat juuri haluamiani.
- Asiakkaana haluan, että lipussa on selkeästi näkyvillä olennaisimmat tiedot, kuten aika ja paikka, ettei tule sekaannuksia tapahtumien kesken.
- Asiakkaana haluan, että voin helposti kysyä jäljellä olevien lippujen tilanteen, jotta tiedän, onko minulla kiire kysellä seuraa tapahtumaan.

#### Lipuntarkastaja

- Lipuntarkastajana haluan pystyä tarkastamaan lipun aitouden, ettei tapahtumaan pääse väärennetyillä lipuilla.
- Lipuntarkastajana haluan tarkastaa lipun voimassaolon lukemalla lippuun merkityn koodin ja merkitä sen käytetyksi, jotta voin varmistaa, että vain lipun ostaneet pääsevät sisään tapahtumaan.
- Lipuntarkastajana haluan, että lipussa on QR-koodi, jotta jono ei kasvaisi liian pitkäksi ja asiakkaat eivät turhautuisi.

#### Ylläpitäjä

- Ylläpitäjänä haluan pystyä lisäämään tapahtumia, jotta uusien tapahtumien liput saadaan myyntiin.
- Ylläpitäjänä haluan pystyä muokkaamaan tapahtumia, jotta tapahtumien tiedot pysyvät ajan tasalla lippukaupassa.
- Ylläpitäjänä haluan pystyä poistamaan tapahtumia, jotta menneet tapahtumat eivät näy enää lippukaupassa.
- Ylläpitäjänä haluan nähdä myyntiraportit, jotta voin tarjota dataa asiakkaiden ostokäyttäytymisestä.
- Ylläpitäjänä haluan pystyä muokkaamaan lipputyyppejä, jotta voin vastata asiakkaan mahdolliseen tarpeeseen lipputyyppien lisäämisestä ja poistamisesta, tai lippujen hinnan muutoksista.

## Käyttöliittymä

Esitetään käyttöliittymän tärkeimmät (vain ne!) näkymät sekä niiden väliset siirtymät käyttöliittymäkaaviona.

Jos näkymän tarkoitus ei ole itsestään selvä, se pitää kuvata lyhyesti.

![Käyttöliittymä](kayttoliittyma.png)

## Tietokanta

Järjestelmään säilöttävä ja siinä käsiteltävät tiedot ja niiden väliset suhteet
kuvataan käsitekaaviolla. Käsitemalliin sisältyy myös taulujen välisten viiteyhteyksien ja avainten
määritykset. Tietokanta kuvataan käyttäen jotain kuvausmenetelmää, joko ER-kaaviota ja UML-luokkakaaviota.

Lisäksi kukin järjestelmän tietoelementti ja sen attribuutit kuvataan
tietohakemistossa. Tietohakemisto tarkoittaa yksinkertaisesti vain jokaisen elementin (taulun) ja niiden
attribuuttien (kentät/sarakkeet) listausta ja lyhyttä kuvausta esim. tähän tyyliin:

> ### Asiakastyypit
>
> _Asiakastyypit-taulu sisältää tiedot tapahtumalipun asiakastyypeistä: asiakastyyppi (esimerkiksi lapsi, aikuinen, eläkeläinen, opiskelija) sekä asiakastyyppiId. Jokaisella lipulla on yksi asiakastyyppi, yksi asiakastyyppi voi olla usealla tapahtumalipulla._
>
> | Kenttä          | Tyyppi      | Kuvaus             |
> | --------------- | ----------- | ------------------ |
> | asiakastyyppiId | int PK      | Asiakastyypin id   |
> | asiakastyyppi   | varchar(20) | Asiakastyypin nimi |

> ### Myynnit
>
> _Myynnit-taulu sisältää lipunmyyntiin liittyvät tiedot: myyntiId, tyontekijaId, myyntiaika ja asiakkaan sähköpostin. Jokainen myynti on yhden työntekijän tekemä, yksi työntekijä voi tehdä usean myynnin._
>
> | Kenttä       | Tyyppi      | Kuvaus                                        |
> | ------------ | ----------- | --------------------------------------------- |
> | myyntiId     | int PK      | Myyntitapahtuman id                           |
> | tyontekijaId | int FK      | Työntekijän Id, viittaus työntekijät -tauluun |
> | myyntiaika   | datetime    | Myyntipäivä ja aika                           |
> | email        | varchar(50) | Asiakkaan sähköpostiosoite                    |

> ### Postinumerot
>
> _Postinumerot-taulu sisältää postinumeron tiedot: postinumeron ja paikkakunnan. Postinumeroa käytetään tapahtumapaikan ja työntekijän tiedoissa. Postinumerolla voi olla tapahtumapaikkaa, mutta tapahtumapaikalla voi olla vain yksi postinumero._
>
> | Kenttä      | Tyyppi      | Kuvaus            |
> | ----------- | ----------- | ----------------- |
> | postinumero | int PK      | Postinumero       |
> | paikkakunta | varchar(50) | Paikkakunnan nimi |

> ### Tapahtumaliput
>
> _Tapahtumaliput-taulu sisältää tapahtumalippuun liittyvät tiedot: tapahtumalippuId, hinta, tapahtumaId ja asiakastyyppi. Tapahtumalipulla voi olla yksi asiakastyyppi, asiakastyyppi voi olla usealla tapahtumalipulla._
>
> | Kenttä           | Tyyppi | Kuvaus                                            |
> | ---------------- | ------ | ------------------------------------------------- |
> | tapahtumalippuId | int PK | Tapahtumalipun id                                 |
> | hinta            | int    | Lipunhinta                                        |
> | tapahtumaId      | int FK | Tapahtuman id, viittaus tapahtumat -tauluun       |
> | asiakastyyppiId  | int FK | Asiakastyypin id, viittaus asiakastyypit -tauluun |

> ### Tapahtumapaikat
>
> _Tapahtumapaikat-taulu sisältää tapahtumapaikkaan liittyvät tiedot: tapahtumapaikan osoitteen, postinumeron, tapahtumapaikan nimen ja kapasiteetin, paljonko tilaan mahtuu ihmisiä. Tapahtumapaikalla voi olla yksi postiosoite, sama postiosoite voi olla usealla tapahtumapaikalla._
>
> | Kenttä            | Tyyppi      | Kuvaus                                                     |
> | ----------------- | ----------- | ---------------------------------------------------------- |
> | tapahtumapaikkaId | int PK      | Tapahtumapaikan id                                         |
> | postinumero       | int FK      | Tapahtumapaikan postinumero, viittaus postinumero -tauluun |
> | tapahtumapaikka   | varchar(50) | Tapahtumapaikan nimi                                       |
> | katuosoite        | varchar(50) | Paikan katuosoite                                          |
> | kapasiteetti      | int         | Paljonko ihmisiä tilaan mahtuu                             |

> ### Tapahtumat
>
> _Tapahtumat-taulu sisältää tapahtumaan liittyvät tiedot: tapahtuman nimen, tapahtumapaikan, aloitus- ja lopetusajan ja kuvauksen. Tapahtumalla voi olla yksi tapahtumapaikka, sama tapahtumapaikka voi olla usealla tapahtumalla._
>
> | Kenttä            | Tyyppi      | Kuvaus                                                |
> | ----------------- | ----------- | ----------------------------------------------------- |
> | tapahtumaId       | int PK      | Tapahtuman id                                         |
> | tapahtumapaikkaId | int FK      | Tapahtumapaikan id, viittaus tapahtumapaikka -tauluun |
> | tapahtuma         | varchar(50) | Tapahtuman nimi                                       |
> | aloitusaika       | datetime    | Tapahtuman aloitusaika                                |
> | lopeutusaika      | datetime    | Tapahtuman lopetusaika                                |
> | kuvaus            | text        | Kuvaus tapahtumasta                                   |

> ### Työntekijät
>
> _Työntekijät-taulu sisältää työntekijään liittyvät tiedot: työntekijäId, postinumero, katuosoite, etunimi, sukunimi, email, puhelinnumero ja bcrypthash eli salattu salasana. Työntekijällä voi olla yksi postinumero, sama postinumero voi olla usealla työntekijällä._
>
> | Kenttä       | Tyyppi      | Kuvaus                                                 |
> | ------------ | ----------- | ------------------------------------------------------ |
> | tyontekijaId | int PK      | Työntekijän id                                         |
> | postinumero  | int FK      | Työntekijän postinumero, viittaus postinumero -tauluun |
> | katuosoite   | varchar(50) | Työntekijän katuosoite                                 |
> | etunimi      | varchar(50) | Etunimi                                                |
> | sukunimi     | varchar(50) | Sukunimi                                               |
> | email        | varchar(50) | Sähköpostiosoite                                       |
> | puhnro       | int         | Puhelinnumero                                          |
> | bcrypthash   | varchar(60) | bcrypt-salattu salasana                                |

> ### Liput
>
> _Liput-taulu sisältää lippuun liittyvät tiedot: LippuId, myyntiId, tapahtumalippuId ja tarkastuskoodin, jolla lippu voidaan tarkastaa tapahtumassa. Lipulla voi olla yksi postiosoite, sama postiosoite voi olla usealla tapahtumapaikalla._
>
> | Kenttä           | Tyyppi     | Kuvaus                                              |
> | ---------------- | ---------- | --------------------------------------------------- |
> | lippuId          | int PK     | Lipun id                                            |
> | myyntiId         | int FK     | Myyntitapahtuman id, viittaus myynnit -tauluun      |
> | tapahtumalippuId | int FK     | Tapahtumalipun id, viittaus tapahtumaliput -tauluun |
> | tarkastuskoodi   | varchar(8) | Lipun tarkastuskoodi                                |

## Tekninen kuvaus

Teknisessä kuvauksessa esitetään järjestelmän toteutuksen suunnittelussa tehdyt tekniset
ratkaisut, esim.

- Missä mikäkin järjestelmän komponentti ajetaan (tietokone, palvelinohjelma)
  ja komponenttien väliset yhteydet (vaikkapa tähän tyyliin:
  https://security.ufl.edu/it-workers/risk-assessment/creating-an-information-systemdata-flow-diagram/)
- Palvelintoteutuksen yleiskuvaus: teknologiat, deployment-ratkaisut yms.
- Keskeisten rajapintojen kuvaukset, esimerkit REST-rajapinta. Tarvittaessa voidaan rajapinnan käyttöä täsmentää
  UML-sekvenssikaavioilla.
- Toteutuksen yleisiä ratkaisuja, esim. turvallisuus.

Tämän lisäksi

- ohjelmakoodin tulee olla kommentoitua
- luokkien, metodien ja muuttujien tulee olla kuvaavasti nimettyjä ja noudattaa
  johdonmukaisia nimeämiskäytäntöjä
- ohjelmiston pitää olla organisoitu komponentteihin niin, että turhalta toistolta
  vältytään

## Testaus

Tässä kohdin selvitetään, miten ohjelmiston oikea toiminta varmistetaan
testaamalla projektin aikana: millaisia testauksia tehdään ja missä vaiheessa.
Testauksen tarkemmat sisällöt ja testisuoritusten tulosten raportit kirjataan
erillisiin dokumentteihin.

Tänne kirjataan myös lopuksi järjestelmän tunnetut ongelmat, joita ei ole korjattu.

## Asennustiedot

Järjestelmän asennus on syytä dokumentoida kahdesta näkökulmasta:

- järjestelmän kehitysympäristö: miten järjestelmän kehitysympäristön saisi
  rakennettua johonkin toiseen koneeseen

- järjestelmän asentaminen tuotantoympäristöön: miten järjestelmän saisi
  asennettua johonkin uuteen ympäristöön.

Asennusohjeesta tulisi ainakin käydä ilmi, miten käytettävä tietokanta ja
käyttäjät tulee ohjelmistoa asentaessa määritellä (käytettävä tietokanta,
käyttäjätunnus, salasana, tietokannan luonti yms.).

## Käynnistys- ja käyttöohje

Tyypillisesti tässä riittää kertoa ohjelman käynnistykseen tarvittava URL sekä
mahdolliset kirjautumiseen tarvittavat tunnukset. Jos järjestelmän
käynnistämiseen tai käyttöön liittyy joitain muita toimenpiteitä tai toimintajärjestykseen liittyviä asioita, nekin kerrotaan tässä yhteydessä.

Usko tai älä, tulet tarvitsemaan tätä itsekin, kun tauon jälkeen palaat
järjestelmän pariin !
