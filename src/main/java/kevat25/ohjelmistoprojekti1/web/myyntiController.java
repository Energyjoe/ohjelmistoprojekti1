package kevat25.ohjelmistoprojekti1.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Transactional
    @PostMapping("/")
    public ResponseEntity<Myynti> uusiMyynti(@RequestBody MyyntiDTO myyntiDTO) {

        // Luodaan uusi Myynti-olio, joka saadaan MyyntiDTO:sta
        Myynti uusiMyynti = new Myynti();

        // Asetetaan MyyntiDTO:n tiedot Myynti-olioon
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

            // Liitä lippu oikeaan entiteettiin
            liput.add(lippu);
        }
        uusiMyynti.setLiput(liput);

        // Tallennetaan Myynti-olio tietokantaan
        Myynti tallennettuMyynti = myyntiRepository.save(uusiMyynti);

        // Palautetaan HTTP 201-vastaus
        return ResponseEntity.status(HttpStatus.CREATED).body(tallennettuMyynti);
    }

}
