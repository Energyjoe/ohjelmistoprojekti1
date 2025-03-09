package kevat25.ohjelmistoprojekti1.web;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kevat25.ohjelmistoprojekti1.domain.Lippu;
import kevat25.ohjelmistoprojekti1.domain.LippuRepository;
import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.MyyntiDTO;
import kevat25.ohjelmistoprojekti1.domain.MyyntiRepository;
import kevat25.ohjelmistoprojekti1.domain.Tapahtuma;
import kevat25.ohjelmistoprojekti1.domain.Tyontekija;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaRepository;


@RestController
@RequestMapping("/myynnit") //Vaihoin tän monikkoon -lotta
public class myyntiController {

    @Autowired
    private MyyntiRepository myyntiRepository;

    @Autowired
    private LippuRepository lippuRepository;

    @Autowired
    private TyontekijaRepository tyontekijaRepository;


    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // Luo uusi myyntitapahtuma (ilman lippuja)
    @PostMapping
    public ResponseEntity<Myynti> uusiMyynti(@RequestBody MyyntiDTO myyntiDTO) {
        Optional<Tyontekija> tyontekijaOpt = tyontekijaRepository.findById(myyntiDTO.getTyontekijaId());

        if (tyontekijaOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Myynti myynti = new Myynti(LocalDateTime.now(), myyntiDTO.getEmail(), tyontekijaOpt.get());
        Myynti savedMyynti = myyntiRepository.save(myynti);
        return ResponseEntity.ok(savedMyynti);
    }

    // Lisää lippu myyntiin
    @PostMapping("/{myyntiId}/lisaaLippu")
    public ResponseEntity<Myynti> lisaaLippuToMyynti(@PathVariable Long myyntiId, @RequestBody Long lippuId) {
        Optional<Myynti> myyntiOpt = myyntiRepository.findById(myyntiId);
        Optional<Lippu> lippuOpt = lippuRepository.findById(lippuId);

        if (myyntiOpt.isEmpty() || lippuOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Myynti myynti = myyntiOpt.get();
        Lippu lippu = lippuOpt.get();
        lippu.setMyynti(myynti);
        lippuRepository.save(lippu);

        return ResponseEntity.ok(myynti);
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

    //Muokkaa myyntitapahtuman lippuja (työn alla)

}
