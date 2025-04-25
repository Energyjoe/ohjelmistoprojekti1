package kevat25.ohjelmistoprojekti1.web;

import org.springframework.web.bind.annotation.RestController;

import kevat25.ohjelmistoprojekti1.domain.MyyntiRepository;
import kevat25.ohjelmistoprojekti1.domain.Tapahtuma;
import kevat25.ohjelmistoprojekti1.domain.TapahtumaRepository;
import kevat25.ohjelmistoprojekti1.domain.Tapahtumalippu;
import kevat25.ohjelmistoprojekti1.domain.Lippu;
import kevat25.ohjelmistoprojekti1.domain.LippuRepository;
import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.LippuPostDTO;
import kevat25.ohjelmistoprojekti1.domain.TapahtumalippuRepository;
import kevat25.ohjelmistoprojekti1.service.LippuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/liput")
public class lippuController {

    private final SecurityFilterChain SecurityFilterChain;
    @Autowired
    private MyyntiRepository myyntiRepository;
    @Autowired
    private TapahtumalippuRepository tapahtumalippuRepository;
    @Autowired
    private LippuRepository lippuRepository;
    @Autowired
    private TapahtumaRepository tapahtumaRepository;
    @Autowired
    private LippuService lippuservice;

    lippuController(SecurityFilterChain SecurityFilterChain) {
        this.SecurityFilterChain = SecurityFilterChain;
    }

    // Hakee yhden lipun tiedot lippuId:n perusteella.
    @CrossOrigin
    @GetMapping("/{lippuId}")
    @ResponseBody
    public ResponseEntity<Lippu> getLippu(@PathVariable Long lippuId) {
        Optional<Lippu> lippu = lippuRepository.findById(lippuId);
        if (lippu.isPresent()) {
            return ResponseEntity.ok(lippu.get());
        } else {
            // Jos lippua ei löydy, palautetaan 404 Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lippua ei löytynyt");
        }
    }

    // Hakee kaikki liput
    @CrossOrigin
    @GetMapping("/")
    @ResponseBody
    public List<Lippu> getLiput() {
        List<Lippu> liput = (List<Lippu>) lippuRepository.findAll();
        if (liput.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lippuja ei löytynyt");
        }
        return liput;
    }

    // Luo yksittäisen lipun, joka liittyy tiettyyn myyntiin ja tapahtumalippuun
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Lippu> postLippu(@RequestBody LippuPostDTO request) {
        Long myyntiId = request.getMyyntiId();
        Long tapahtumalippuId = request.getTapahtumalippuId();

        Optional<Myynti> myynti = myyntiRepository.findById(myyntiId);
        Optional<Tapahtumalippu> tapahtumalippu = tapahtumalippuRepository.findById(tapahtumalippuId);

        if (tapahtumalippu.isPresent() && myynti.isPresent()) {
            Long tapahtumaId = tapahtumalippu.get().getTapahtuma().getTapahtumaId();
            
            //Haetaan tapahtumaobjekti, jotta voidaan tarkistaa onko ennakkomyynti loppunut
            Optional<Tapahtuma> tapahtumaOpt = tapahtumaRepository.findById(tapahtumaId);

            if (tapahtumaOpt.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tapahtumaa ei löytynyt");
            }

            Tapahtuma tapahtuma = tapahtumaOpt.get();

            //Lasketaan ennakkomyynnin loppumisaika
            LocalDateTime myynninLoppu = tapahtuma.getAloitusaika().minusHours(1);
            
            //Tarkistetaan, että ennakkomyynti ei ole loppunut
            if (LocalDateTime.now().isAfter(myynninLoppu)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ennakkomyynti on loppunut");
            }

            //Tarkistetaan, että lippuja on jäljellä
            int liputJaljella = lippuservice.liputJaljella(tapahtumaId);
            if (liputJaljella<=0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tapahtuma on loppuunmyyty");
            }

            String tarkistuskoodi = LippuService.generoiUniikkiTarkistuskoodi(tapahtumaId, lippuRepository);

            Lippu lippu = new Lippu(tapahtumalippu.get(), myynti.get(), tarkistuskoodi);
            lippuRepository.save(lippu);
            return ResponseEntity.status(HttpStatus.CREATED).body(lippu);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tapahtumaa tai myyntiä ei löytynyt");
    }

    // Poistaa yksittäisen lipun
    @DeleteMapping("/{lippuId}")
    @ResponseBody
    public ResponseEntity<String> deleteLippu(@PathVariable Long lippuId) {
        Optional<Lippu> lippu = lippuRepository.findById(lippuId);
        if (lippu.isPresent()) {
            lippuRepository.delete(lippu.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Lippu poistettu");
        } else {
            // Jos lippua ei löydy, palautetaan 404 Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lippua ei löytynyt");
        }
    }

    // Vaihtaa lipun tarkistusarvon, jos arvo on false -> true, ja päinvastoin
    @CrossOrigin
    @PatchMapping("/{tarkistuskoodi}")
    @ResponseBody
    public ResponseEntity<String> changeState(@PathVariable String tarkistuskoodi) {
        Optional<Lippu> lippu = lippuRepository.findByTarkistuskoodi(tarkistuskoodi);
        if (lippu.isPresent()) {
            boolean tarkistettu = lippu.get().getTarkistettu();
            lippu.get().setTarkistettu(!tarkistettu);
            lippuRepository.save(lippu.get());
            return ResponseEntity.status(HttpStatus.OK).body("Lipun tarkistustila vaihdettu");
        } else {
            // Jos lippua ei löydy, palautetaan 404 Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lippua ei löytynyt");
        }
    }

    // Metodi käsittelee tietokantavirheet
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDatabaseException(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Tietokantayhteys epäonnistui. Yritä uudelleen myöhemmin.");
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<String> handleCannotCreateTransactionException(CannotCreateTransactionException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Tietokantayhteys epäonnistui. Yritä uudelleen myöhemmin.");
    }
}
