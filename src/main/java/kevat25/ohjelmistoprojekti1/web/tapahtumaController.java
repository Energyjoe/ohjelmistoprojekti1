package kevat25.ohjelmistoprojekti1.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kevat25.ohjelmistoprojekti1.domain.Tapahtuma;
import kevat25.ohjelmistoprojekti1.domain.TapahtumaRepository;

@RestController
@RequestMapping("/tapahtumat")
public class tapahtumaController {

    private final TapahtumaRepository tapahtumaRepository;

    public tapahtumaController(TapahtumaRepository tapahtumaRepository) {
        this.tapahtumaRepository = tapahtumaRepository;
    }

    @PostMapping("/")
    public ResponseEntity<?> uusiTapahtuma(@Valid @RequestBody Tapahtuma uusiTapahtuma) {

        // Tallennetaan tapahtuma repositoryyn
        try {
        Tapahtuma tallennettuTapahtuma = tapahtumaRepository.save(uusiTapahtuma);

        // Palauttaa HTTP-vastauksen
        return ResponseEntity.status(HttpStatus.CREATED).body(tallennettuTapahtuma);
        }

        //Mikäli ylläoleva ei onnistu, palauttaa virhekoodin.
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tapahtuman lisäys ei onnistunut. Syy: " + e.getMessage());
        }
    }

    // Palauttaa Listan tapahtumista, tai virhekoodin, jos lista on tyhjä.
    @GetMapping("/")
    public ResponseEntity<List<Tapahtuma>> haeKaikki() {
        List<Tapahtuma> tapahtumat = (List<Tapahtuma>) tapahtumaRepository.findAll();
        if (tapahtumat.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok(tapahtumat);
        }
    }

    // Palauttaa tapahtuman tiedot, tai virheilmoitusken, jos kyseistä
    // tapahtumaId:tä ei löydy.

    @GetMapping("/{tapahtumaId}")
    public ResponseEntity<Tapahtuma> haeTapahtuma(@Valid @PathVariable Long tapahtumaId) {
        Optional<Tapahtuma> tapahtuma = tapahtumaRepository.findById(tapahtumaId);

        if (tapahtuma.isPresent()) {
            return ResponseEntity.ok(tapahtuma.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{tapahtumaId}")
    public ResponseEntity<Void> poistaTapahtuma(@PathVariable Long tapahtumaId) {
        Optional<Tapahtuma> tapahtuma = tapahtumaRepository.findById(tapahtumaId);

        if (tapahtuma.isPresent()) {
            tapahtumaRepository.deleteById(tapahtumaId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{tapahtumaId}")
    public ResponseEntity<Object> muokkaaTapahtumaa(@Valid @PathVariable Long tapahtumaId,
            @RequestBody Tapahtuma uusiTapahtuma) {

        // etsitään muokattava tapahtuma id:n perusteella:
        Tapahtuma muokattavaTapahtuma = tapahtumaRepository.findById(tapahtumaId).orElse(null);

        // jos tapahtumaa ei löydy:
        if (muokattavaTapahtuma == null) {
            String virheViesti = "Tapahtumaa ei löytynyt ID:llä" + tapahtumaId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(virheViesti);
        }

        muokattavaTapahtuma.setTapahtumaNimi(uusiTapahtuma.getTapahtumaNimi());
        muokattavaTapahtuma.setTapahtumaKuvaus(uusiTapahtuma.getTapahtumaKuvaus());
        muokattavaTapahtuma.setAloitusaika(uusiTapahtuma.getAloitusaika());
        muokattavaTapahtuma.setLopetusaika(uusiTapahtuma.getLopetusaika());
        muokattavaTapahtuma.setKapasiteetti(uusiTapahtuma.getKapasiteetti());
        muokattavaTapahtuma.setTapahtumapaikka(uusiTapahtuma.getTapahtumapaikka());

        Tapahtuma paivitettyTapahtuma = tapahtumaRepository.save(muokattavaTapahtuma);

        return ResponseEntity.status(HttpStatus.OK).body(paivitettyTapahtuma);

        /*
         * {
         * "tapahtumaNimi": "Toinen konsertti",
         * "tapahtumaKuvaus": "Mahtava konsertti Helsingissä",
         * "aloitusaika": "2025-04-01T19:00:00",
         * "lopetusaika": "2025-04-01T23:00:00",
         * "kapasiteetti": 5000,
         * "tapahtumapaikka": {
         * "tapahtumapaikkaId": 1
         * }
         * }
         */

    }
}