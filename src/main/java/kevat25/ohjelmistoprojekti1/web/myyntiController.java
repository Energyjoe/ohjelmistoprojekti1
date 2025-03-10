package kevat25.ohjelmistoprojekti1.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import kevat25.ohjelmistoprojekti1.domain.Lippu;
import kevat25.ohjelmistoprojekti1.domain.LippuDTO;
import kevat25.ohjelmistoprojekti1.domain.LippuRepository;
import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.MyyntiDTO;
import kevat25.ohjelmistoprojekti1.domain.MyyntiRepository;
import kevat25.ohjelmistoprojekti1.domain.Tapahtuma;
import kevat25.ohjelmistoprojekti1.domain.Tyontekija;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaRepository;
import kevat25.ohjelmistoprojekti1.service.LippuService;
import kevat25.ohjelmistoprojekti1.service.MyyntiService;

@RestController
@RequestMapping("/myynnit") // Vaihoin tän monikkoon -lotta
public class myyntiController {

    @Autowired
    private MyyntiRepository myyntiRepository;

    @Autowired
    private MyyntiService myyntiService;

    @Autowired
    private LippuRepository lippuRepository;

    @Autowired
    private TyontekijaRepository tyontekijaRepository;

    @Autowired
    private LippuService lippuService; // Injektio LippuService-luokasta

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<MyyntiDTO> uusiMyynti(@RequestBody MyyntiDTO myyntiDTO) {

        // Luodaan uusi Myynti-olio, joka saadaan MyyntiDTO:sta
        Myynti uusiMyynti = new Myynti();
        uusiMyynti.setMyyntiaika(myyntiDTO.getMyyntiaika());

        // Hae työntekijä id:llä
        Tyontekija tyontekija = tyontekijaRepository.findById(myyntiDTO.getTyontekijaId())
                .orElseThrow(() -> new RuntimeException("Työntekijää ei löytynyt"));
        uusiMyynti.setTyontekija(tyontekija);

        // Asetetaan email, mikäli se on tarpeen
        uusiMyynti.setEmail(myyntiDTO.getEmail());

        // Liput, jotka saadaan MyyntiDTO:n listalta
        List<Lippu> liput = new ArrayList<>();
        for (LippuDTO lippuDTO : myyntiDTO.getLiput()) {
            // Oletetaan, että lippu on jo tallennettu tietokantaan ja siihen on viite
            Lippu lippu = lippuRepository.findById(lippuDTO.getLippuId())
                    .orElseThrow(() -> new RuntimeException("Lippu ei löytynyt"));

            lippu.setMyynti(uusiMyynti);
            // Liitä lippu oikeaan entiteettiin
            liput.add(lippu);
        }

        // Asetetaan liput Myynti-olioon
        uusiMyynti.setLiput(liput);

        // Tallennetaan Myynti-olio tietokantaan
        Myynti tallennettuMyynti = myyntiRepository.save(uusiMyynti);

        // Muunnetaan tallennettu Myynti DTO:ksi käyttäen MyyntiServiceä
        MyyntiDTO tallennettuMyyntiDTO = myyntiService.getMyyntiById(tallennettuMyynti.getMyyntiId());

        // Palautetaan HTTP 201-vastaus, jossa on MyyntiDTO
        return ResponseEntity.status(HttpStatus.CREATED).body(tallennettuMyyntiDTO);
    }

    /*
     * POST JSON data Postmania varten:
     * {
     * "myyntiaika": "2025-03-09T14:00:00",
     * "tyontekijaId": 1,
     * "email": "esimerkki@domain.com",
     * "liput": [
     * {
     * "lippuId": 1,
     * "tarkistuskoodi": "ABCDEF01"
     * },
     * {
     * "lippuId": 2,
     * "tarkistuskoodi": "BCDEF012"
     * }
     * ]
     * }
     */

    // Hakee kaikki myyntitapahtumat
    @GetMapping("/")
    public List<MyyntiDTO> getAllMyynnit() {
        return myyntiService.getAllMyynnit();
    }

    // Hakee yksittäisen myyntitapahtuman tiedot
    @GetMapping("/{myyntiId}")
    public MyyntiDTO getMyyntiById(@PathVariable Long myyntiId) {
        return myyntiService.getMyyntiById(myyntiId);
    }

    // Muokkaa myyntitapahtumaa
    @PatchMapping("/{myyntiId}")
    public ResponseEntity<?> muokkaaMyyntia(@PathVariable Long myyntiId, @RequestBody MyyntiDTO myyntiDTO) {
        Optional<Myynti> myyntiOpt = myyntiRepository.findById(myyntiId);
        if (myyntiOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Myyntitapahtumaa ei löytynyt");
        }

        Myynti myynti = myyntiOpt.get();

        if (!isValidEmail(myyntiDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Virheellinen sähköpostiosoite.");
        } else {
            myynti.setEmail(myyntiDTO.getEmail());
        }

        if (myyntiDTO.getTyontekijaId() != null) {
            Optional<Tyontekija> tyontekijaOpt = tyontekijaRepository.findById(myyntiDTO.getTyontekijaId());
            if (tyontekijaOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Työntekijää ei löytynyt.");
            }
            myynti.setTyontekija(tyontekijaOpt.get());
        }

        myyntiRepository.save(myynti);
        return ResponseEntity.ok(myynti);
    }

    // Muokkaa myyntitapahtuman lippuja (työn alla)

}
