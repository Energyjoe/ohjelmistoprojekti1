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

        // Liput, jotka luodaan MyyntiDTO:n perusteella
        List<Lippu> liput = new ArrayList<>();

        for (LippuDTO lippuDTO : myyntiDTO.getLiput()) {
            // Luodaan uusi lippu ilman lippuId:tä (ID generoituu automaattisesti)
            Lippu lippu = new Lippu();

            // Asetetaan tarkistuskoodi
            lippu.setTarkistuskoodi(lippuDTO.getTarkistuskoodi());

            // Jos lippu liittyy tapahtumalippuun, liitetään tapahtumalippu
            if (lippuDTO.getTapahtumalippuId() != null) {
                Tapahtumalippu tapahtumalippu = tapahtumalippuRepository.findById(lippuDTO.getTapahtumalippuId())
                        .orElseThrow(() -> new RuntimeException("Tapahtumalippu ei löytynyt"));
                lippu.setTapahtumalippu(tapahtumalippu); // Liitetään tapahtumalippu lippuun
            }

            // Liitetään lippu myyntiin
            lippu.setMyynti(uusiMyynti);

            // Lisää lippu listalle
            liput.add(lippu);
        }
        // Liitetään liput myyntiin
        uusiMyynti.setLiput(liput);

        // Tallennetaan Myynti-olio tietokantaan
        Myynti tallennettuMyynti = myyntiRepository.save(uusiMyynti);

        // Tallennetaan myös luodut liput, jolloin niiden ID:t generoituu
        // automaattisesti
        lippuRepository.saveAll(liput);

        // Muunnetaan tallennettu Myynti DTO:ksi käyttäen MyyntiServiceä
        MyyntiDTO tallennettuMyyntiDTO = myyntiService.getMyyntiById(tallennettuMyynti.getMyyntiId());

        List<LippuDTO> lippuDTOList = new ArrayList<>();
        for (Lippu lippu : tallennettuMyynti.getLiput()) {
            lippuDTOList.add(lippuToDTO(lippu));
        }
        tallennettuMyyntiDTO.setLiput(lippuDTOList);

        // Palautetaan HTTP 201-vastaus, jossa on MyyntiDTO
        return ResponseEntity.status(HttpStatus.CREATED).body(tallennettuMyyntiDTO);
    }

    /*
     * POST JSON data Postmania varten:
     * {
     * "myyntiaika": "2024-02-29T12:00:00",
     * "tyontekijaId": 1,
     * "email": "asiakas1@example.com",
     * "liput": [
     * {
     * "tapahtumalippuId": 1,
     * "tarkistuskoodi": "ABCDEF01"
     * },
     * {
     * "tapahtumalippuId": 2,
     * "tarkistuskoodi": "BCDEF012"
     * }
     * ]
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
