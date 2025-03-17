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
import kevat25.ohjelmistoprojekti1.domain.TapahtumaRepository;
import kevat25.ohjelmistoprojekti1.domain.Tapahtumalippu;
import kevat25.ohjelmistoprojekti1.domain.TapahtumalippuRepository;
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
    private TyontekijaRepository tyontekijaRepository;

    @Autowired
    private LippuRepository lippuRepository;

    @Autowired
    private MyyntiService myyntiService; // Injektio MyyntiService-luokasta

    @Autowired
    private LippuService lippuService;

    @Autowired
    private TapahtumalippuRepository tapahtumalippuRepository;

    // Lippu-entiteetistä LippuDTO:ksi
    private LippuDTO lippuToDTO(Lippu lippu) {
        return lippuService.getLipputiedot(lippu.getLippuId());
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<?> uusiMyynti(@RequestBody MyyntiDTO myyntiDTO) {
        // Tarkistetaan, että myyntiaika on annettu
        if (myyntiDTO.getMyyntiaika() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Virhe: Myyntiaika on pakollinen.");
        }

        // Haetaan työntekijä, palautetaan 404 jos ei löydy
        Tyontekija tyontekija = tyontekijaRepository.findById(myyntiDTO.getTyontekijaId())
                .orElse(null);
        if (tyontekija == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Virhe: Työntekijää ei löytynyt ID:llä " + myyntiDTO.getTyontekijaId());
        }

        // Luodaan uusi myynti
        Myynti uusiMyynti = new Myynti();
        uusiMyynti.setMyyntiaika(myyntiDTO.getMyyntiaika());
        uusiMyynti.setTyontekija(tyontekija);
        uusiMyynti.setEmail(myyntiDTO.getEmail());

        // Tallennetaan myynti
        Myynti tallennettuMyynti = myyntiRepository.save(uusiMyynti);

        // Muunnetaan DTO:ksi ja palautetaan
        MyyntiDTO tallennettuMyyntiDTO = myyntiService.getMyyntiById(tallennettuMyynti.getMyyntiId());
        return ResponseEntity.status(HttpStatus.CREATED).body(tallennettuMyyntiDTO);
    }

    /*
     * {
     * "myyntiaika": "2024-03-17T14:30:00",
     * "tyontekijaId": 1,
     * "email": "asiakas@example.com"
     * }
     * 
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

    // Poista myyntitapahtuma
    @DeleteMapping("/{myyntiId}")
    public ResponseEntity<Void> poistaMyynti(@PathVariable Long myyntiId) {
        Optional<Myynti> myynti = myyntiRepository.findById(myyntiId);

        if (myynti.isPresent()) {
            myyntiRepository.deleteById(myyntiId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
