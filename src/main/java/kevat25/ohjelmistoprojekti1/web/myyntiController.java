package kevat25.ohjelmistoprojekti1.web;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import kevat25.ohjelmistoprojekti1.domain.Tyontekija;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaRepository;

@RestController
@RequestMapping("/myynti")
public class myyntiController {

    @Autowired
    private MyyntiRepository myyntiRepository;

    @Autowired
    private LippuRepository lippuRepository;

    @Autowired
    private TyontekijaRepository tyontekijaRepository;

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

}
