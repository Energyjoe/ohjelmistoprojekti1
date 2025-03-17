package kevat25.ohjelmistoprojekti1.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import kevat25.ohjelmistoprojekti1.domain.Lippu;
import kevat25.ohjelmistoprojekti1.domain.LippuDTO;
import kevat25.ohjelmistoprojekti1.domain.LippuRepository;
import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.MyyntiDTO;
import kevat25.ohjelmistoprojekti1.domain.MyyntiRepository;
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

    @Transactional
    @PostMapping("/")
    public ResponseEntity<MyyntiDTO> uusiMyynti(@Valid @RequestBody MyyntiDTO myyntiDTO) {
        // Haetaan työntekijä, heitetään poikkeus jos ei löydy
        Tyontekija tyontekija = tyontekijaRepository.findById(myyntiDTO.getTyontekijaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Virhe: Työntekijää ei löytynyt ID:llä " + myyntiDTO.getTyontekijaId()));

        // Luodaan uusi myynti
        Myynti uusiMyynti = new Myynti();
        uusiMyynti.setMyyntiaika(myyntiDTO.getMyyntiaika());
        uusiMyynti.setTyontekija(tyontekija);
        uusiMyynti.setEmail(myyntiDTO.getEmail());

        // Tallennetaan myynti
        Myynti tallennettuMyynti = myyntiRepository.save(uusiMyynti);

        // Muunnetaan tallennettu myynti DTO:ksi ja palautetaan
        MyyntiDTO tallennettuMyyntiDTO = myyntiService.getMyyntiById(tallennettuMyynti.getMyyntiId());

        // Palautetaan oikein muodostettu vastaus
        return ResponseEntity.status(HttpStatus.CREATED).body(tallennettuMyyntiDTO);
    }

    /*
     * {
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
    public ResponseEntity<?> muokkaaMyyntia(@Valid @PathVariable Long myyntiId, @RequestBody MyyntiDTO myyntiDTO) {
        Myynti paivitettyMyynti = myyntiService.muokkaaMyyntia(myyntiId, myyntiDTO);
        return ResponseEntity.ok(paivitettyMyynti);
    }

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
