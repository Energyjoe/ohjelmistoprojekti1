package kevat25.ohjelmistoprojekti1.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import kevat25.ohjelmistoprojekti1.domain.Asiakastyyppi;
import kevat25.ohjelmistoprojekti1.domain.AsiakastyyppiRepository;
import kevat25.ohjelmistoprojekti1.domain.Lippu;
import kevat25.ohjelmistoprojekti1.domain.LippuDTO;
import kevat25.ohjelmistoprojekti1.domain.LippuPostDTO;
import kevat25.ohjelmistoprojekti1.domain.LippuRepository;
import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.Tapahtuma;
import kevat25.ohjelmistoprojekti1.domain.TapahtumaRepository;
import kevat25.ohjelmistoprojekti1.domain.Tapahtumalippu;
import kevat25.ohjelmistoprojekti1.domain.TapahtumalippuRepository;
import kevat25.ohjelmistoprojekti1.domain.Tapahtumapaikka;
import kevat25.ohjelmistoprojekti1.domain.TapahtumapaikkaRepository;
import kevat25.ohjelmistoprojekti1.domain.Tyontekija;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaRepository;
import kevat25.ohjelmistoprojekti1.service.LippuService;

@RestController
@RequestMapping("/tapahtumat")
public class tapahtumaController {

    @Autowired
    private final TapahtumaRepository tapahtumaRepository; //Täältä haetaan tapahtuman tiedot
    
    @Autowired
    private LippuService lippuService; //Tämän kautta haetaan tapahtuman myytyjen lippujen määrä

    @Autowired
    private TapahtumapaikkaRepository tapahtumapaikkaRepository; //Täältä haetaan tapahtumapaikan kapasiteetti

    public tapahtumaController(TapahtumaRepository tapahtumaRepository) {
        this.tapahtumaRepository = tapahtumaRepository;
    }

    @Autowired
    private AsiakastyyppiRepository asiakastyyppiRepository; //Täältä haetaan ovilippujen asiakastyyppi-entity

    @Autowired
    private LippuRepository lippuRepository; //Tämän kautta luodaan oviliput

    @Autowired TapahtumalippuRepository tapahtumaLippuRepository; //Täältä haetaan tapahtuman tapahtumaliput

    @Autowired TyontekijaRepository tyontekijaRepository; //Täältä haetaan oviliput luovan työntekijän tiedot


    @PostMapping("/")
    public ResponseEntity<?> uusiTapahtuma(@Valid @RequestBody Tapahtuma uusiTapahtuma) {

        // Tallennetaan tapahtuma repositoryyn
        try {

            //Jos tapahtuman kapasiteettiä ei määritellä, se korvataan tapahtumapaikan kapasiteetilla
            if (uusiTapahtuma.getKapasiteetti()==null) {

                Long tapahtumapaikkaId = uusiTapahtuma.getTapahtumapaikka().getTapahtumapaikkaId();
                Optional<Tapahtumapaikka> paikkaOpt= tapahtumapaikkaRepository.findById(tapahtumapaikkaId);

                if (paikkaOpt.isPresent()) {
                Tapahtumapaikka paikka = paikkaOpt.get();
                Integer paikkaKapasiteetti = paikka.getKapasiteetti();

                uusiTapahtuma.setKapasiteetti(paikkaKapasiteetti);
                }
                else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tapahtumapaikkaa ID:llä " + tapahtumapaikkaId + " ei löytynyt.");
                }
            }

            Tapahtuma tallennettuTapahtuma = tapahtumaRepository.save(uusiTapahtuma);

            // Palauttaa HTTP-vastauksen
            return ResponseEntity.status(HttpStatus.CREATED).body(tallennettuTapahtuma);
        }

        // Mikäli ylläoleva ei onnistu, palauttaa virhekoodin.
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tapahtuman lisäys ei onnistunut. Syy: " + e.getMessage());
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

    // Palauttaa tapahtuman tiedot, tai virheilmoituksen, jos kyseistä
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

    //Palauttaa jäljellä olevien lippujen määrän
    @GetMapping("/{tapahtumaId}/jaljella")
    public ResponseEntity<Integer> haeJaljellaOlevatLiput(@PathVariable Long tapahtumaId) {
        Optional<Tapahtuma> tapahtuma = tapahtumaRepository.findById(tapahtumaId);
        if (tapahtuma.isPresent()) {
            int jaljella = lippuService.liputJaljella(tapahtumaId);
            return ResponseEntity.ok(jaljella);
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
            @Valid @RequestBody Tapahtuma uusiTapahtuma) {

        // etsitään muokattava tapahtuma id:n perusteella:
        Tapahtuma muokattavaTapahtuma = tapahtumaRepository.findById(tapahtumaId).orElse(null);

        // jos tapahtumaa ei löydy:
        if (muokattavaTapahtuma == null) {
            String virheViesti = "Tapahtumaa ei löytynyt ID:llä" + tapahtumaId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(virheViesti);
        }

        muokattavaTapahtuma.setTapahtumaNimi(uusiTapahtuma.getTapahtumaNimi());
        if (uusiTapahtuma.getTapahtumaNimi() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tapahtumalla on oltava nimi");
        }

        muokattavaTapahtuma.setTapahtumaKuvaus(uusiTapahtuma.getTapahtumaKuvaus());
        if (uusiTapahtuma.getTapahtumaKuvaus() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tapahtumalla on oltava kuvaus");
        }

        muokattavaTapahtuma.setAloitusaika(uusiTapahtuma.getAloitusaika());
        if (uusiTapahtuma.getAloitusaika() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tapahtumalla on oltava aloitusaika");
        } 

        muokattavaTapahtuma.setLopetusaika(uusiTapahtuma.getLopetusaika());
      
        muokattavaTapahtuma.setKapasiteetti(uusiTapahtuma.getKapasiteetti());

        muokattavaTapahtuma.setTapahtumapaikka(uusiTapahtuma.getTapahtumapaikka());
        if (uusiTapahtuma.getTapahtumapaikka() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tapahtumalla on oltava tapahtumapaikka");
        }

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

    //Luo oviliput ennakkomyynnin päätyttyä -.-.-.-.-.-
    @PostMapping("/{tapahtumaId}/oviliput")

    public ResponseEntity<List<LippuDTO>> postOviliput(@Valid @PathVariable Long tapahtumaId) {

        
        // Haetaan tapahtumaobjekti, jotta voidaan tarkistaa onko ennakkomyynti loppunut
        Optional<Tapahtuma> tapahtumaOpt = tapahtumaRepository.findById(tapahtumaId);
        if (tapahtumaOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tapahtumaa ei löytynyt");
        }

        Tapahtuma tapahtuma = tapahtumaOpt.get();
        LocalDateTime myynninLoppu = tapahtuma.getAloitusaika().minusHours(1);
            
        // Tarkistetaan, että ennakkomyynti on loppunut
        if (LocalDateTime.now().isBefore(myynninLoppu)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ennakkomyynti ei ole vielä loppunut");
        }
        // Tarkistetaan, että lippuja on jäljellä
        int liputJaljella = lippuService.liputJaljella(tapahtumaId);
            if (liputJaljella <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tapahtuma on loppuunmyyty");
            }


        //Luodaan uusi myyntitapahtuma ovilippujen tulostusta varten
        Optional<Tyontekija> tyontekijaOpt = tyontekijaRepository.findByEmail("admin@oprojekti1.com");
        
        //Virheenhallintaa mikäli admin-käyttäjää ei löydy
        if (tyontekijaOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin-käyttäjää ei löytynyt");
        }

        Tyontekija tyontekija = tyontekijaOpt.get();
        Myynti myynti = new Myynti(LocalDateTime.now(), null, tyontekija );

        //Luodaan lista ovilipuista
        List<LippuDTO> oviliput = new ArrayList<>();
        for (int i = 0; i < liputJaljella; i++) { //Luodaan niin monta uutta ovilippua, kuin tapahtumaan on lippuja jäljellä)

            //Luodaan jokaiselle ovilipulle uniikki tarkistuskoodi
            String tarkistuskoodi = LippuService.generoiUniikkiTarkistuskoodi(tapahtumaId, lippuRepository);

            //Haetaan Ovilippu -asiakastyypin tiedot
            Asiakastyyppi asiakastyyppi = asiakastyyppiRepository.findByAsiakastyyppi("Ovilippu");

            if (asiakastyyppi == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ovilippuja ei ole määritelty");
            }

            //Haetaan kyseisen tapahtuman tapahtumalippu-entity, jonka asiakastyyppi on ovilippu
            Tapahtumalippu ovilippu = tapahtumaLippuRepository.findByTapahtumaAndAsiakastyyppi(tapahtuma, asiakastyyppi);
            
            if (ovilippu == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kyseiselle tapahtumalle ei ole määritelty ovilippujen hintaa");
            }

         
            //Luodaan yksittäinen lippu haetuilla tiedoilla
            Lippu lippu = new Lippu(ovilippu, myynti, tarkistuskoodi);
            lippuRepository.save(lippu);


                // Muunnetaan Lippu LippuDTO:ksi
                LippuDTO lippuDTO = new LippuDTO(
                    lippu.getLippuId(),
                    lippu.getTarkistuskoodi(),
                    lippu.getMyynti().getMyyntiId(),
                    lippu.getTapahtumalippu().getTapahtumalippuId(),
                    tapahtuma.getTapahtumaNimi(),
                    tapahtuma.getAloitusaika(),
                    ovilippu.getHinta(),
                    asiakastyyppi.getAsiakastyyppi()
                );
                oviliput.add(lippuDTO);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(oviliput);
       
    }


}