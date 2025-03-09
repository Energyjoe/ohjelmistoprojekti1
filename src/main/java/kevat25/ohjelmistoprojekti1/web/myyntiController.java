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

@RestController
@RequestMapping("/myynnit") // Vaihoin tän monikkoon -lotta
public class myyntiController {

    @Autowired
    private MyyntiRepository myyntiRepository;

    @Autowired
    private LippuRepository lippuRepository;

    @Autowired
    private TyontekijaRepository tyontekijaRepository;

    // Lippu-entiteetistä LippuDTO:ksi
    private LippuDTO lippuToDTO(Lippu lippu) {
        LippuDTO lippuDTO = new LippuDTO();
        lippuDTO.setLippuId(lippu.getLippuId());
        lippuDTO.setTarkistuskoodi(lippu.getTarkistuskoodi());

        // Asetetaan Myynti-id
        lippuDTO.setMyyntiId(lippu.getMyynti().getMyyntiId()); // Oletetaan, että Lippu on linkitetty Myyntiin

        // Asetetaan tapahtumalippuId
        lippuDTO.setTapahtumalippuId(lippu.getTapahtumalippu().getTapahtumalippuId());

        // Asetetaan tapahtuman nimi ja alkuajankohta
        lippuDTO.setTapahtumanNimi(lippu.getTapahtumalippu().getTapahtuma().getTapahtumaNimi());
        lippuDTO.setAlkuaika(lippu.getTapahtumalippu().getTapahtuma().getAloitusaika());

        // Asetetaan hinta ja asiakastyyppi
        lippuDTO.setHinta(lippu.getTapahtumalippu().getHinta());
        lippuDTO.setAsiakastyyppi(lippu.getTapahtumalippu().getAsiakastyyppi().getAsiakastyyppi());

        return lippuDTO;
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<MyyntiDTO> uusiMyynti(@RequestBody MyyntiDTO myyntiDTO) {

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

        // Asetetaan liput Myynti-olioon
        uusiMyynti.setLiput(liput);

        // Tallennetaan Myynti-olio tietokantaan
        Myynti tallennettuMyynti = myyntiRepository.save(uusiMyynti);

        // Muunna Myynti-olio takaisin DTO:ksi ennen palautusta
        MyyntiDTO tallennettuMyyntiDTO = new MyyntiDTO();
        tallennettuMyyntiDTO.setMyyntiId(tallennettuMyynti.getMyyntiId());
        tallennettuMyyntiDTO.setMyyntiaika(tallennettuMyynti.getMyyntiaika());
        tallennettuMyyntiDTO.setEmail(tallennettuMyynti.getEmail());

        // Muunna liput entiteetistä DTO:ksi
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
