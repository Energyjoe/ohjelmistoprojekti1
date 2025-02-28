package kevat25.ohjelmistoprojekti1.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kevat25.ohjelmistoprojekti1.domain.Tapahtuma;
import kevat25.ohjelmistoprojekti1.domain.TapahtumaRepository;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/tapahtumat")
public class tapahtumaController {

    private final TapahtumaRepository tapahtumaRepository;

    public tapahtumaController(TapahtumaRepository tapahtumaRepository) {
        this.tapahtumaRepository = tapahtumaRepository;
    }

    @PostMapping("/lisaaTapahtuma")
    public ResponseEntity<Tapahtuma> uusiTapahtuma(@RequestBody Tapahtuma uusiTapahtuma) {

        // Tallennetaan tapahtuma repositoryyn
        Tapahtuma tallennettuTapahtuma = tapahtumaRepository.save(uusiTapahtuma);

        // Palauttaa HTTP-vastauksen
        return ResponseEntity.status(HttpStatus.CREATED).body(tallennettuTapahtuma);
    }

    @DeleteMapping("/{tapahtuma_id}")
    public ResponseEntity<Void> poistaTapahtuma(@PathVariable Long tapahtuma_id) {

        // Tarkistetaan, löytyykö annetulla id:llä tapahtuma
        if (!tapahtumaRepository.existsById(tapahtuma_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Poistetaan tapahtuma id:n perusteella
        tapahtumaRepository.deleteById(tapahtuma_id);

        // Palautetaan 204 No Content
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/muokkaaTapahtumaa/{tapahtuma_id}")
    public ResponseEntity<Object> muokkaaTapahtumaa(@PathVariable Long tapahtuma_id,
            @RequestBody Tapahtuma uusiTapahtuma) {

        // etsitään muokattava tapahtuma id:n perusteella:
        Tapahtuma muokattavaTapahtuma = tapahtumaRepository.findById(tapahtuma_id).orElse(null);

        // jos tapahtumaa ei löydy:
        if (muokattavaTapahtuma == null) {
            String virheViesti = "Tapahtumaa ei löytynyt ID:llä" + tapahtuma_id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(virheViesti);
        }

        muokattavaTapahtuma.setTapahtumaNimi(uusiTapahtuma.getTapahtumaNimi());
        muokattavaTapahtuma.setTapahtumaKuvaus(uusiTapahtuma.getTapahtumaKuvaus());
        muokattavaTapahtuma.setAloitusaika(uusiTapahtuma.getAloitusaika());
        muokattavaTapahtuma.setLopetusaika(uusiTapahtuma.getLopetusaika());
        muokattavaTapahtuma.setKapasiteetti(uusiTapahtuma.getKapasiteetti());

        // Jos tapahtumapaikka on null, ei päivitetä sitä
        if (uusiTapahtuma.getTapahtumapaikka() != null) {
            muokattavaTapahtuma.setTapahtumapaikka(uusiTapahtuma.getTapahtumapaikka());
        } else {
            // Jos tapahtumapaikkaa ei ole, asetetaan null
            muokattavaTapahtuma.setTapahtumapaikka(null);
        }

        Tapahtuma paivitettyTapahtuma = tapahtumaRepository.save(muokattavaTapahtuma);

        return ResponseEntity.status(HttpStatus.OK).body(paivitettyTapahtuma);

        /*
         * Postman esimerkki Body, tapahtumapaikkaa ei ole koska niitä ei löyty vielä
         * tietokannasta:
         * 
         * {
         * "tapahtumaNimi": "Uudempi Konsertti",
         * "tapahtumaKuvaus": "Uudempi mahtavampi konsertti Helsingissä",
         * "aloitusaika": "2026-04-01T19:00:00",
         * "lopetusaika": "2026-04-01T23:00:00",
         * "kapasiteetti": 10000
         * }
         * 
         */

    }
}
